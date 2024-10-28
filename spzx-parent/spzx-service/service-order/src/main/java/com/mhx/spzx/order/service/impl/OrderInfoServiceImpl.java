package com.mhx.spzx.order.service.impl;

import com.mhx.spzx.feign.cart.CartFeignClient;
import com.mhx.spzx.model.dto.h5.OrderInfoDto;
import com.mhx.spzx.model.entity.h5.CartInfo;
import com.mhx.spzx.model.entity.order.OrderItem;
import com.mhx.spzx.model.vo.h5.TradeVo;
import com.mhx.spzx.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Override
    public TradeVo getTrade() {
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItems.add(orderItem);
            total=total.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        }
        TradeVo tradeVo=new TradeVo();
        tradeVo.setOrderItemList(orderItems);
        tradeVo.setTotalAmount(total);
        return tradeVo;
    }

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {

        return null;
    }
}
