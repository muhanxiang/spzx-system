package com.mhx.spzx.manager.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.spzx.manager.mapper.SysRoleUserMapper;
import com.mhx.spzx.model.dto.system.AssginRoleDto;
import com.mhx.spzx.model.dto.system.LoginDto;
import com.mhx.spzx.model.dto.system.SysUserDto;
import com.mhx.spzx.model.entity.system.SysUser;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.system.LoginVo;
import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.manager.mapper.SysUserMapper;
import com.mhx.spzx.manager.service.SysUserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

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
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list=sysUserMapper.findByPage(sysUserDto);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void saveSysUser(SysUser sysUser) {
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(dbSysUser!=null){
            throw new BaseException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    @Override
    @Transactional
    public void doAssign(AssginRoleDto assginRoleDto) {
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }
}
