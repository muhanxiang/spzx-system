package com.mhx.spzx.product.mapper;

import com.mhx.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
