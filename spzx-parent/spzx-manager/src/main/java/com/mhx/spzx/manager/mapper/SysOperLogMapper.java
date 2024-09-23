package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper {

    void insert(SysOperLog sysOperLog);
}
