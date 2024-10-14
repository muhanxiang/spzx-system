package com.mhx.spzx.product.controller;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.h5.ProductSkuDto;
import com.mhx.spzx.model.entity.product.Product;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.h5.ProductItemVo;
import com.mhx.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/product")
@Tag(name = "商品列表管理")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit,
                       ProductSkuDto productSkuDto){
        PageInfo<ProductSku> pageInfo=
                productService.findByPage(page,limit,productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@PathVariable("skuId") Long skuId){
        ProductItemVo productItemVo=productService.item(skuId);
        return  Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        return productService.getBySkuId(skuId);
    }
}
