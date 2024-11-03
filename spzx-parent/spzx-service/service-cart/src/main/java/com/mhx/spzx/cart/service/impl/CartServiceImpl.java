package com.mhx.spzx.cart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mhx.spzx.cart.service.CartService;
import com.mhx.spzx.feign.product.ProductFeignClient;
import com.mhx.spzx.model.entity.h5.CartInfo;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        //加入商品到redis 要判断是否本身已经存在 使用了hash类型
        Object cartInfoObj =
                redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo=null;
        //最终加入购物车 需要获取商品的sku信息 这里用到了OpenFeign远程调用
        if(cartInfoObj!=null){
            cartInfo= JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }
        else{
            cartInfo=new CartInfo();
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        redisTemplate.opsForHash().put(
                cartKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));
    }

    @Override
    public List<CartInfo> getCartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(valueList)){
            List<CartInfo> cartInfoList = valueList.stream().map(
                            cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                            .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                            .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.opsForHash().delete(cartKey,String.valueOf(skuId));
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        if(redisTemplate.opsForHash().hasKey(cartKey,String.valueOf(skuId))){
            Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
            CartInfo cartInfo=JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));
        }
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(valueList)){
            List<CartInfo> cartInfoList = valueList.stream().map(
                            cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            for (CartInfo cartInfo : cartInfoList) {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey,
                        String.valueOf(cartInfo.getSkuId()),
                        JSON.toJSONString(cartInfo));
            }
        }
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjs = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(cartInfoObjs)){
            List<CartInfo> cartInfoList = cartInfoObjs.stream().map(obj ->
                            JSON.parseObject(obj.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked()==1)
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjs = redisTemplate.opsForHash().values(cartKey);
        cartInfoObjs.stream().map(obj->JSON.parseObject(obj.toString(), CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked()==1)
                .forEach(
                        cartInfo -> redisTemplate.opsForHash().delete(
                                cartKey,String.valueOf(cartInfo.getSkuId())));
    }
}
