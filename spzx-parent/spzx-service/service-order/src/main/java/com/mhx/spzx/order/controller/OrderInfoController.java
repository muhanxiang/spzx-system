package com.mhx.spzx.order.controller;

import com.mhx.spzx.model.dto.h5.OrderInfoDto;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.h5.TradeVo;
import com.mhx.spzx.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping(value="/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "确认下单")
    @GetMapping("auth/trade")
    public Result trade(){
        TradeVo tradeVo=orderInfoService.getTrade();
        return Result.build(tradeVo,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Long orderId=orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }

}