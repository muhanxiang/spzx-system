package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.manager.mapper.SysRoleMenuMapper;
import com.mhx.spzx.manager.service.SysMenuService;
import com.mhx.spzx.manager.service.SysRoleMenuService;
import com.mhx.spzx.model.dto.system.AssginMenuDto;
import com.mhx.spzx.model.entity.system.SysMenu;
import com.mhx.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        List<Long> roleMenuIds=sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sysMenuList",sysMenuList);
        map.put("roleMenuIds",roleMenuIds);
        return map;
    }

    @Override
    @Transactional
    public void doAssign(AssginMenuDto assginMenuDto) {
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(menuInfo!=null&& !menuInfo.isEmpty()){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
