package com.mhx.spzx.order.service.impl;

import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.feign.cart.CartFeignClient;
import com.mhx.spzx.feign.product.ProductFeignClient;
import com.mhx.spzx.feign.user.UserFeignClient;
import com.mhx.spzx.model.dto.h5.OrderInfoDto;
import com.mhx.spzx.model.entity.h5.CartInfo;
import com.mhx.spzx.model.entity.order.OrderInfo;
import com.mhx.spzx.model.entity.order.OrderItem;
import com.mhx.spzx.model.entity.order.OrderLog;
import com.mhx.spzx.model.entity.product.ProductSku;
import com.mhx.spzx.model.entity.user.UserAddress;
import com.mhx.spzx.model.entity.user.UserInfo;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.h5.TradeVo;
import com.mhx.spzx.order.mapper.OrderInfoMapper;
import com.mhx.spzx.order.mapper.OrderItemMapper;
import com.mhx.spzx.order.mapper.OrderLogMapper;
import com.mhx.spzx.order.service.OrderInfoService;
import com.mhx.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderLogMapper orderLogMapper;
    @Override
    public TradeVo getTrade() {
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItems.add(orderItem);
            total=total.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        }
        TradeVo tradeVo=new TradeVo();
        tradeVo.setOrderItemList(orderItems);
        tradeVo.setTotalAmount(total);
        return tradeVo;
    }

    @Override
    @Transactional
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        // 获取所有订单项的集合
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        // 判断是否为空
        if(CollectionUtils.isEmpty(orderItemList)){
            throw new BaseException(ResultCodeEnum.DATA_ERROR);
        }
        //校验商品库存是否充足
        //远程调用获取SKU信息检验库存
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if(productSku==null){
                throw new BaseException(ResultCodeEnum.DATA_ERROR);
            }
            if(productSku.getStockNum() < orderItem.getSkuNum()){
                throw new BaseException(ResultCodeEnum.STOCK_LESS);
            }
        }
        //添加数据到order_info
        //远程调用获取用户收货地址信息
        OrderInfo orderInfo=new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();

        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //封装收货地址信息
        Long userAddressId = orderInfoDto.getUserAddressId();
        UserAddress userAddress=userFeignClient.getUserAddress(userAddressId);
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);
        //添加数据到order_item
        for (OrderItem orderItem : orderItemList) {
            //设置订单id
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }
        //添加数据到order_log表
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);
        //把生成订单的商品从购物车删除
        cartFeignClient.deleteChecked();
        //返回订单id
        return orderInfo.getId();
    }
}
