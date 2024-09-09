package com.mhx.spzx.manager.mapper;

import com.mhx.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    OrderStatistics selectStatisticsByDate(String createDate);
}
