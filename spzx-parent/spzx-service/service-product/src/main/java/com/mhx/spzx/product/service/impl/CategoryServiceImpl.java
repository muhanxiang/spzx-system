package com.mhx.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.product.mapper.CategoryMapper;
import com.mhx.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public List<Category> selectOneCategory() {
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");
        if(StringUtils.hasText(categoryOneJson)){
            return JSON.parseArray(categoryOneJson, Category.class);
        }
        List<Category> categoryList = categoryMapper.selectOneCategory();
        redisTemplate.opsForValue().set("category:one",JSON.toJSONString(categoryList),
                7, TimeUnit.DAYS);
        return categoryList;
    }

    @Override
    @Cacheable(value = "category",key = "'all'")
    public List<Category> findCategoryTree() {
        List<Category> allCategoryList=categoryMapper.findAll();
        List<Category> oneCategoryList = allCategoryList.stream().
                filter(item -> item.getParentId() == 0).
                collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = allCategoryList.stream().
                        filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).
                        collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);

                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = allCategoryList.stream().
                                filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).
                                collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}
