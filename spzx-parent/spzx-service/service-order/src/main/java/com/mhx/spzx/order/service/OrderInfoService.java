package com.mhx.spzx.order.service;

import com.mhx.spzx.model.dto.h5.OrderInfoDto;
import com.mhx.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);
}
