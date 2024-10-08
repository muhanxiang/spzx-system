package com.mhx.spzx.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.model.dto.h5.UserLoginDto;
import com.mhx.spzx.model.dto.h5.UserRegisterDto;
import com.mhx.spzx.model.entity.user.UserInfo;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.h5.UserInfoVo;
import com.mhx.spzx.user.mapper.UserInfoMapper;
import com.mhx.spzx.user.service.UserInfoService;
import com.mhx.spzx.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //验证验证码正确性
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        String redisCode = redisTemplate.opsForValue().get(username);
        if(redisCode == null || !redisCode.equals(code)){
            throw new BaseException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //验证用户名非重复
        UserInfo userInfo=userInfoMapper.selectByUserName(username);
        if(userInfo!=null){
            throw new BaseException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //封装数据添加到数据库
        userInfo=new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        //删除验证码
        redisTemplate.delete(username);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        UserInfo userInfo = userInfoMapper.selectByUserName(username);
        if(userInfo==null){
            throw new BaseException(ResultCodeEnum.LOGIN_ERROR);
        }
        String databasePassword = userInfo.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!databasePassword.equals(md5Password)){
            throw new BaseException(ResultCodeEnum.LOGIN_ERROR);
        }
        if(userInfo.getStatus()==0){
            throw new BaseException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:spzx:" + token,
                JSON.toJSONString(userInfo),
                30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
//        String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
//        if(!StringUtils.hasText(userJson)){
//            throw new BaseException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo= JSON.parseObject(userJson,UserInfo.class);

        //直接从threadLocal获取
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo=new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
