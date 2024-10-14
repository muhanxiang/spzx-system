package com.mhx.spzx.product.service;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.h5.ProductSkuDto;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.model.vo.h5.ProductItemVo;

import java.util.List;

public interface ProductService {
    List<ProductSku> selectTopTenProductSku();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);
}
