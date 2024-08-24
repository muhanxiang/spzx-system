package com.mhx.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.entity.product.Brand;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);
}
