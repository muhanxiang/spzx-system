package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.manager.mapper.SysMenuMapper;
import com.mhx.spzx.manager.mapper.SysRoleMenuMapper;
import com.mhx.spzx.manager.service.SysMenuService;
import com.mhx.spzx.manager.utils.MenuHelper;
import com.mhx.spzx.model.entity.system.SysMenu;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.system.SysMenuVo;
import com.mhx.spzx.utils.AuthContextUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList=sysMenuMapper.findAll();
        if(CollectionUtils.isEmpty(sysMenuList))
            return null;
        return MenuHelper.buildTree(sysMenuList);
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        //当新添加了一个子菜单 把父菜单的isHalf半开状态改为1
        updateSysRoleMenu(sysMenu);

    }

    private void updateSysRoleMenu(SysMenu sysMenu) {
        Long parentId = sysMenu.getParentId();
        if(parentId!=0){
            SysMenu parentMenu=sysMenuMapper.selectParentMenu(parentId);
            if(parentMenu!=null){
                sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
                updateSysRoleMenu(parentMenu);
            }
        }
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        //查询是否包含子菜单
        int cnt=sysMenuMapper.selectCountById(id);
        if(cnt>0){
            throw new BaseException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.delete(id);
    }

    @Override
    public List<SysMenuVo> findMenusByUserId() {
        Long userId = AuthContextUtil.get().getId();
        return buildMenus(MenuHelper.buildTree(sysMenuMapper.findMenusByUserId(userId)));
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
