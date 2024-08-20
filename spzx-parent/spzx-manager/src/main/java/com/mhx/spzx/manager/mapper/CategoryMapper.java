package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectCategoryByParentId(Long id);

    int selectCountByParentId(Long id);
}
