package com.mhx.spzx.product.service.impl;

import com.mhx.spzx.model.entity.product.Brand;
import com.mhx.spzx.product.mapper.BrandMapper;
import com.mhx.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
