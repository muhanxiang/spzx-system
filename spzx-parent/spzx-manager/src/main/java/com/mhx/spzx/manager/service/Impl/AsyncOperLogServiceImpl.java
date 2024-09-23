package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.common.log.service.AsyncOperLogService;
import com.mhx.spzx.manager.mapper.SysOperLogMapper;
import com.mhx.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
