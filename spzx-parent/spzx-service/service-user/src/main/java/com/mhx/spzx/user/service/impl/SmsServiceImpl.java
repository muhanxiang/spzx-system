package com.mhx.spzx.user.service.impl;

import com.mhx.spzx.user.service.SmsService;
import com.mhx.spzx.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void sendCode(String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(StringUtils.hasText(code)){
            return;
        }
        //生成验证码
        code = RandomStringUtils.randomNumeric(4);
        //发送验证码
        sendMessage(phone,code);
        //把验证码放到redis中 设置过期时间
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
    }

    private void sendMessage(String phone, String code) {
        String host = "https://wwsms.market.alicloudapi.com";
        String path = "/send_sms";
        String method = "POST";
        String appcode = "20d5a08e433c487db4da2ea4b08f5998";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("template_id", "CST_11253");   //注意，模板CST_11253 仅作调试使用，下发短信不稳定，请联系客服报备自己的专属签名模板，以保障业务稳定使用
        bodys.put("phone_number", phone);

//可以提交工单联系客服，或者钉钉联系，钉钉号：1ko_t720ssqc54
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
