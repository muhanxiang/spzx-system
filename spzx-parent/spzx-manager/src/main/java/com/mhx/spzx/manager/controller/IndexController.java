package com.mhx.spzx.manager.controller;

import com.mhx.spzx.model.dto.system.LoginDto;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.system.LoginVo;
import com.mhx.spzx.model.vo.system.ValidateCodeVo;
import com.mhx.spzx.manager.service.SysUserService;
import com.mhx.spzx.manager.service.ValidateCodeService;
import com.mhx.spzx.utils.AuthContextUtil;
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

    @GetMapping("/getUserInfo")
    //@RequestHeader相当于是从HttpServletRequest里的请求头里取数据，简化操作
    public Result getUserInfo(@RequestHeader(name="token") String token){
//        //从请求头获取token
//        //String token = request.getHeader("token");
//        //根据token查询用户信息
//        SysUser sysUser= sysUserService.getUserInfo(token);
//        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
        return Result.build(AuthContextUtil.get(),ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(name="token") String token){
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
