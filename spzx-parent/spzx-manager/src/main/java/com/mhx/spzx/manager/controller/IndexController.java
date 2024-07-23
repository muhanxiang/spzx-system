package com.mhx.spzx.manager.controller;

import com.mhx.spxz.model.dto.system.LoginDto;
import com.mhx.spxz.model.vo.common.Result;
import com.mhx.spxz.model.vo.common.ResultCodeEnum;
import com.mhx.spxz.model.vo.system.LoginVo;
import com.mhx.spxz.model.vo.system.ValidateCodeVo;
import com.mhx.spzx.manager.service.SysUserService;
import com.mhx.spzx.manager.service.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    //用户登录
    @Operation(summary = "登录的方法")
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo=sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    //生成图片验证码
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo=validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
}
