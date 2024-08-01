package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    @Delete("delete from sys_user_role where user_id=#{userId}")
    void deleteByUserId(Long userId);

    @Insert("insert into sys_user_role (role_id, user_id, create_time, update_time, is_deleted) VALUES " +
            "(#{roleId},#{userId},now(),now(),0)")
    void doAssign(Long userId, Long roleId);

    List<Long> findByUserId(Long userId);
}
