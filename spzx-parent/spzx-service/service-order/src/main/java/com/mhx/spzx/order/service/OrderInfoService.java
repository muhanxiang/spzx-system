package com.mhx.spzx.order.service;

import com.github.pagehelper.PageInfo;
import com.mhx.spzx.model.dto.h5.OrderInfoDto;
import com.mhx.spzx.model.entity.order.OrderInfo;
import com.mhx.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
