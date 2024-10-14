package com.mhx.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.h5.ProductSkuDto;
import com.mhx.spzx.model.entity.product.Product;
import com.mhx.spzx.model.entity.product.ProductDetails;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.model.vo.h5.ProductItemVo;
import com.mhx.spzx.product.mapper.ProductDetailsMapper;
import com.mhx.spzx.product.mapper.ProductMapper;
import com.mhx.spzx.product.mapper.ProductSkuMapper;
import com.mhx.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductSku> selectTopTenProductSku() {
        return productSkuMapper.selectTopTenProductSku();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> list=productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(list);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        //先创建一个vo对象
        ProductItemVo productItemVo=new ProductItemVo();
        //获取sku信息
        ProductSku productSku=productSkuMapper.getById(skuId);
        //根据sku信息 获取productId 并获取商品信息
        Long productId=productSku.getProductId();
        Product product=productMapper.getById(productId);
        //获取商品详情信息
        ProductDetails productDetails=productDetailsMapper.getByProductId(productId);
        //封装一个map 商品规格和skuId的对应关系
        Map<String,Object> skuSpecValueMap=new HashMap<>();
        //根据商品id获取所有sku列表
        List<ProductSku> productSkuList=productSkuMapper.findByProductId(productId);
        for (ProductSku sku : productSkuList) {
            skuSpecValueMap.put(sku.getSkuSpec(),sku.getId());
        }
        //封装所有数据
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.getById(skuId);
    }
}
