package com.mhx.spzx.manager.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.spzx.manager.mapper.ProductDetailsMapper;
import com.mhx.spzx.manager.mapper.ProductMapper;
import com.mhx.spzx.manager.mapper.ProductSkuMapper;
import com.mhx.spzx.manager.service.ProductService;
import com.mhx.spzx.model.dto.product.ProductDto;
import com.mhx.spzx.model.entity.product.Product;
import com.mhx.spzx.model.entity.product.ProductDetails;
import com.mhx.spzx.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page,limit);
        List<Product> list=productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Product product) {
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0,size=productSkuList.size(); i<size; i++) {
            // 获取ProductSku对象
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode
            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper.save(productSku);                    // 保存数据
        }
        ProductDetails productDetails=new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getById(Long id) {
        Product product=productMapper.findById(id);
        List<ProductSku> productSkuList=productSkuMapper.findByProductId(id);
        ProductDetails productDetails=productDetailsMapper.findByProductId(id);
        product.setProductSkuList(productSkuList);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku productSku : productSkuList) {
            productSkuMapper.updateById(productSku);
        }
        ProductDetails productDetails= productDetailsMapper.findByProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.updateById(productDetails);
    }

    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);                   // 根据id删除商品基本数据
        productSkuMapper.deleteByProductId(id);         // 根据商品id删除商品的sku数据
        productDetailsMapper.deleteByProductId(id);
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
