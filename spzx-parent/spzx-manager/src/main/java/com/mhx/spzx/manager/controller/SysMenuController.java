package com.mhx.spzx.manager.controller;

import com.mhx.spzx.manager.service.SysMenuService;
import com.mhx.spzx.model.entity.system.SysMenu;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list=sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") Long id){
        sysMenuService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
