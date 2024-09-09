package com.mhx.spzx.manager.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.mhx.spzx.manager.mapper.OrderStatisticsMapper;
import com.mhx.spzx.manager.service.OrderInfoService;
import com.mhx.spzx.model.dto.order.OrderStatisticsDto;
import com.mhx.spzx.model.entity.order.OrderStatistics;
import com.mhx.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        List<OrderStatistics> orderStatisticsList=orderStatisticsMapper.selectList(orderStatisticsDto);
        //日期列表
        List<String> dateList = orderStatisticsList.stream().map(
                orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .toList();
        //统计金额列表
        List<BigDecimal> amountList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                        .toList();
        // 创建OrderStatisticsVo对象封装响应结果数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo() ;
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        // 返回数据
        return orderStatisticsVo;
    }
}
