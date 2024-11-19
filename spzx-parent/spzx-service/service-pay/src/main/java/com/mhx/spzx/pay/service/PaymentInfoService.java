package com.mhx.spzx.pay.service;

import com.mhx.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoService {
    //保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);
}
