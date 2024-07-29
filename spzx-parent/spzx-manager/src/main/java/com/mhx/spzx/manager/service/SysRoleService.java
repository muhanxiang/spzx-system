package com.mhx.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.system.SysRoleDto;
import com.mhx.spzx.model.entity.system.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);
}
