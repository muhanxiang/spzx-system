package com.mhx.spzx.order.mapper;

import com.mhx.spzx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {
    void save(OrderLog orderLog);
}
