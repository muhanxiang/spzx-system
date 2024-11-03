package com.mhx.spzx.order.mapper;

import com.mhx.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);
}
