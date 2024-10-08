package com.mhx.spzx.product.mapper;

import com.mhx.spzx.model.dto.h5.ProductSkuDto;
import com.mhx.spzx.model.entity.product.ProductSku;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> selectTopTenProductSku();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getById(Long skuId);

    List<ProductSku> findByProductId(Long productId);
}
