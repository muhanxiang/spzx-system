package com.mhx.spzx.manager.utils;

import com.mhx.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        List<SysMenu> trees=new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getParentId()==0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu menu : sysMenuList) {
            if(sysMenu.getId().longValue()==menu.getParentId().longValue()){
                sysMenu.getChildren().add(findChildren(menu,sysMenuList));
            }
        }
        return sysMenu;
    }


}
