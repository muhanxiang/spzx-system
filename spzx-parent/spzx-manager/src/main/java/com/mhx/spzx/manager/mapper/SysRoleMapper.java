package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.dto.system.SysRoleDto;
import com.mhx.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void save(SysRole sysRole);

    void update(SysRole sysRole);

    @Update("update sys_role set is_deleted=1 where id=#{roleId}")
    void delete(Long roleId);
}
