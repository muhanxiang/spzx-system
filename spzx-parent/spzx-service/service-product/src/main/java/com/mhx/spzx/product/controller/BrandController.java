package com.mhx.spzx.product.controller;

import com.mhx.spzx.model.entity.product.Brand;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "品牌管理")
@RestController
@RequestMapping(value="/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"})
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "获取所有品牌")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> list=brandService.findAll();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}