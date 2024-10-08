package com.mhx.spzx.product.service;

import com.mhx.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}
