package com.mhx.spzx.manager.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mhx.spzx.model.dto.system.LoginDto;
import com.mhx.spzx.model.entity.system.SysUser;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.system.LoginVo;
import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.manager.mapper.SysUserMapper;
import com.mhx.spzx.manager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        String codeKey = loginDto.getCodeKey();
        String captcha = loginDto.getCaptcha();
        String codeValue = redisTemplate.opsForValue().get("user:validate"+ codeKey);
        if(StrUtil.isEmpty(codeValue)||!StrUtil.equalsIgnoreCase(codeValue,captcha)){
            throw new BaseException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete("user:validate" + codeKey);
        String userName = loginDto.getUserName();
        SysUser sysUser=sysUserMapper.selectUserInfoByUserName(userName);
        if(sysUser==null){
            throw new BaseException(ResultCodeEnum.LOGIN_ERROR);
        }
        String database_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!input_password.equals(database_password)){
            throw new BaseException(ResultCodeEnum.LOGIN_ERROR);
        }
        String token= UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser),
                7, TimeUnit.DAYS);
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }
}
