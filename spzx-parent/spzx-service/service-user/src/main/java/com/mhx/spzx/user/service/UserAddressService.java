package com.mhx.spzx.user.service;

import com.mhx.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long id);
}
