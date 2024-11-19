package com.mhx.spzx.pay.service.impl;

import com.mhx.spzx.feign.order.OrderFeignClient;
import com.mhx.spzx.model.entity.order.OrderInfo;
import com.mhx.spzx.model.entity.order.OrderItem;
import com.mhx.spzx.model.entity.pay.PaymentInfo;
import com.mhx.spzx.pay.mapper.PaymentInfoMapper;
import com.mhx.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1 根据订单编号查询支付记录
        PaymentInfo paymentInfo=paymentInfoMapper.getByOrderNo(orderNo);
        //2 判断是否存在
        if(paymentInfo==null){
            //远程调用获取订单信息
            OrderInfo orderInfo=orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //把结果封装
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }
}
