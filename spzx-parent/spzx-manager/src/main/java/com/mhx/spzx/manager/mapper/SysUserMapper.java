package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.dto.system.SysUserDto;
import com.mhx.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    @Update("update sys_user set is_deleted=1 where id=#{userId}")
    void delete(Long userId);
}
