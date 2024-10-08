package com.mhx.spzx.product.mapper;

import com.mhx.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectOneCategory();

    List<Category> findAll();
}
