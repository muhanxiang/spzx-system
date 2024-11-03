package com.mhx.spzx.user.service.impl;

import com.mhx.spzx.model.entity.user.UserAddress;
import com.mhx.spzx.user.mapper.UserAddressMapper;
import com.mhx.spzx.user.service.UserAddressService;
import com.mhx.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper addressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return addressMapper.findUserAddressList(userId);
    }

    @Override
    public UserAddress getUserAddress(Long id) {
        return addressMapper.getUserAddress(id);
    }
}
