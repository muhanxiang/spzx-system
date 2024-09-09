package com.mhx.spzx.manager.service;

import com.mhx.spzx.model.dto.order.OrderStatisticsDto;
import com.mhx.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
