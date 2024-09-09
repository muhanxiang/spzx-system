package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    public List<ProductUnit> findAll();
}
