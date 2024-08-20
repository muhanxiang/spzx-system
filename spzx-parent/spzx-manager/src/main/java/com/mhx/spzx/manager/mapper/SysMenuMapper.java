package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAll();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    @Select("select count(*) from sys_menu where parent_id=#{id} and is_deleted=0")
    int selectCountById(Long id);

    @Update("update sys_menu set is_deleted=1 where id=#{id}")
    void delete(Long id);

    List<SysMenu> findMenusByUserId(Long userId);

    SysMenu selectParentMenu(Long parentId);
}
