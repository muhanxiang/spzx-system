package com.mhx.spzx.manager.controller;

import com.mhx.spzx.manager.service.CategoryService;
import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable("id") Long id){
        List<Category> list=categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
