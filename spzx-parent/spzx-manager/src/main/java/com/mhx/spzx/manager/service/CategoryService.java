package com.mhx.spzx.manager.service;

import com.mhx.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);
}
