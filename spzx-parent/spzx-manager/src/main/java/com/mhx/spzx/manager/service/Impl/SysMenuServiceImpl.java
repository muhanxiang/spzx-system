package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.manager.mapper.SysMenuMapper;
import com.mhx.spzx.manager.service.SysMenuService;
import com.mhx.spzx.manager.utils.MenuHelper;
import com.mhx.spzx.model.entity.system.SysMenu;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList=sysMenuMapper.findAll();
        if(CollectionUtils.isEmpty(sysMenuList))
            return null;
        return MenuHelper.buildTree(sysMenuList);
    }
}
