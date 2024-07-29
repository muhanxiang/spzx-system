package com.mhx.spzx.common.exception;

import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    //全局异常处理
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理类
    @ExceptionHandler(BaseException.class)
    public Result error(BaseException baseException){
        return Result.build(null, baseException.getResultCodeEnum());
    }

}
