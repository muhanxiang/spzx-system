package com.mhx.spzx.manager.service;

import com.mhx.spzx.model.dto.system.LoginDto;
import com.mhx.spzx.model.entity.system.SysUser;
import com.mhx.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
