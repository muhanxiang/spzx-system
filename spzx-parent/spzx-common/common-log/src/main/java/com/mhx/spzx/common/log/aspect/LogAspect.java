package com.mhx.spzx.common.log.aspect;

import com.mhx.spzx.common.log.annotation.Log;
import com.mhx.spzx.common.log.service.AsyncOperLogService;
import com.mhx.spzx.common.log.utils.LogUtil;
import com.mhx.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog))")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){
        //业务代码调用之前 先封装一部分数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        Object proceed =null;
        try {
            proceed = joinPoint.proceed();
            //业务代码调用之后 封装数据
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            //当出现异常 封装数据
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException(e);
        }
        //调用方法 把日志信息添加到数据库
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }

}
