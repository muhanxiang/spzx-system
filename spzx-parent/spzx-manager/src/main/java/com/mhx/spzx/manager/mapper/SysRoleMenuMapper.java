package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {

    List<Long> findSysRoleMenuByRoleId(Long roleId);

    @Delete("delete from sys_role_menu where role_id=#{roleId}")
    void deleteByRoleId(Long roleId);


    void doAssign(AssginMenuDto assginMenuDto);

    void updateSysRoleMenuIsHalf(Long id);
}
