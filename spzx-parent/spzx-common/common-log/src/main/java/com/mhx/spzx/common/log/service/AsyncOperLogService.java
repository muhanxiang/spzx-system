package com.mhx.spzx.common.log.service;

import com.mhx.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
