package com.mhx.spzx.product.controller;

import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value="/api/product/category")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CategoryControlloer {
    @Autowired
    private CategoryService categoryService;

    //查询所有分类 按照树形方式封装
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
