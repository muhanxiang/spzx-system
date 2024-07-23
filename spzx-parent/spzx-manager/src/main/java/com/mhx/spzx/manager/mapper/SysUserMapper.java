package com.mhx.spzx.manager.mapper;

import com.mhx.spxz.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);
}
