package com.mhx.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mhx.spzx.model.entity.system.SysUser;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果请求方式是options 预检请求 直接放行
        String method = request.getMethod();
        if(method.equals("OPTIONS")){
            return true;
        }
        //获取token检验
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        String userInfoString = redisTemplate.opsForValue().get("user:login" + token);
        if(StrUtil.isEmpty(userInfoString)){
            responseNoLoginInfo(response);
            return false;
        }
        AuthContextUtil.set(JSON.parseObject(userInfoString, SysUser.class));
        //更新redis数据过期时间
        redisTemplate.expire("user:login" + token,30, TimeUnit.MINUTES);
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除ThreadLocal
        AuthContextUtil.remove();
    }
}
