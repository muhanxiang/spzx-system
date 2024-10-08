package com.mhx.spzx.user.mapper;

import com.mhx.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo selectByUserName(String username);

    void save(UserInfo userInfo);
}
