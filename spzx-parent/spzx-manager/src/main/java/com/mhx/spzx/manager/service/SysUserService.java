package com.mhx.spzx.manager.service;

import com.mhx.spxz.model.dto.system.LoginDto;
import com.mhx.spxz.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
