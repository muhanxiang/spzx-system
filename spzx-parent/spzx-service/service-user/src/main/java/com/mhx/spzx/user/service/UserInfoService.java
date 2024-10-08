package com.mhx.spzx.user.service;

import com.mhx.spzx.model.dto.h5.UserLoginDto;
import com.mhx.spzx.model.dto.h5.UserRegisterDto;
import com.mhx.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
