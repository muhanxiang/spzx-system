package com.mhx.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.product.CategoryBrandDto;
import com.mhx.spzx.model.entity.product.Brand;
import com.mhx.spzx.model.entity.product.CategoryBrand;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
