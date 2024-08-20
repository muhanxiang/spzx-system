package com.mhx.spzx.manager.controller;

import com.mhx.spzx.manager.service.SysRoleMenuService;
import com.mhx.spzx.model.dto.system.AssginMenuDto;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService ;

    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
       Map<String,Object> map=sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
       return Result.build(map,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}