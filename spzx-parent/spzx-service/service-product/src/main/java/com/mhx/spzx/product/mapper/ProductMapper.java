package com.mhx.spzx.product.mapper;

import com.mhx.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Product getById(Long productId);
}
