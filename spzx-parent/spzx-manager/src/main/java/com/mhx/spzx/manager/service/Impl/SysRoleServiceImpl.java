package com.mhx.spzx.manager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.system.SysRoleDto;
import com.mhx.spzx.model.entity.system.SysRole;
import com.mhx.spzx.manager.mapper.SysRoleMapper;
import com.mhx.spzx.manager.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;


    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto) {
        PageHelper.startPage(current,limit);
        List<SysRole> list= sysRoleMapper.findByPage(sysRoleDto);
        return new PageInfo<>(list);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }
}
