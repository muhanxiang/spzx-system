package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectCategoryByParentId(Long id);

    int selectCountByParentId(Long id);

    @Select(" select id,name,image_url,parent_id,status,order_num,create_time,update_time,is_deleted" +
            " from category where is_deleted =0 order by id desc")
    List<Category> findAll();

    void batchInsert(List<CategoryExcelVo> cachedDataList);
}
