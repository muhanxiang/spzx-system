package com.mhx.spzx.common.exception;

import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;
    public BaseException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum=resultCodeEnum;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
}
