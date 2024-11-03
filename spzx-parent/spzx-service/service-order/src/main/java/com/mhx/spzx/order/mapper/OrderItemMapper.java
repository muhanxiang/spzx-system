package com.mhx.spzx.order.mapper;

import com.mhx.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);
}
