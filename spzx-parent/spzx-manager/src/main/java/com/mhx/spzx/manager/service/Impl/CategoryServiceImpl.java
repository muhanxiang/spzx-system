package com.mhx.spzx.manager.service.Impl;

import com.mhx.spzx.manager.mapper.CategoryMapper;
import com.mhx.spzx.manager.service.CategoryService;
import com.mhx.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    @Transactional
    public List<Category> findCategoryList(Long id) {
        List<Category> categoryList=categoryMapper.selectCategoryByParentId(id);
        if(!CollectionUtils.isEmpty(categoryList)){
            for (Category category : categoryList) {
                int cnt=categoryMapper.selectCountByParentId(category.getId());
                category.setHasChildren(cnt > 0);
            }
        }
        return categoryList;
    }
}
