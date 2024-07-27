package com.mhx.spzx.manager.service;

import com.mhx.spxz.model.dto.system.LoginDto;
import com.mhx.spxz.model.entity.system.SysUser;
import com.mhx.spxz.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
