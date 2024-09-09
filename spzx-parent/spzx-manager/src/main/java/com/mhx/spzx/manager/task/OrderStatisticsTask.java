package com.mhx.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.mhx.spzx.manager.mapper.OrderInfoMapper;
import com.mhx.spzx.manager.mapper.OrderStatisticsMapper;
import com.mhx.spzx.model.entity.order.OrderInfo;
import com.mhx.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    //每天凌晨2点查询前一天统计数据
    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void orderTotalAmountStatistics() {
        String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        OrderStatistics orderStatistics=
                orderInfoMapper.selectStatisticsByDate(createDate);
        if(orderStatistics!=null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}