package com.mhx.spzx.product.controller;

import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.h5.IndexVo;
import com.mhx.spzx.product.service.CategoryService;
import com.mhx.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.formula.functions.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result index(){
        // 所有一级分类
        List<Category> categoryList=categoryService.selectOneCategory();
        // 销量前十条商品记录
        List<ProductSku> productSkuList=productService.selectTopTenProductSku();
        IndexVo indexVo=new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}
