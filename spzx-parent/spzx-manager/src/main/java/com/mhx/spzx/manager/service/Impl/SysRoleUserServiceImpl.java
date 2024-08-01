package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.manager.mapper.SysRoleUserMapper;
import com.mhx.spzx.manager.service.SysRoleService;
import com.mhx.spzx.manager.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
}
