package com.mhx.spxz.model.entity.product;

import com.mhx.spxz.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}