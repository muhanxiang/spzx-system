package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.dto.product.ProductDto;
import com.mhx.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);

    void save(Product product);

    Product findById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}
