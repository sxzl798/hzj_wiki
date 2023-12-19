package com.hzj.wiki.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Resource
    private RedisTemplate redisTemplate;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        LOG.info("---------LogInterceptor 开始---------");
        LOG.info("请求地址：{} {}",request.getRequestURI().toString(), request.getMethod());
        LOG.info("远程地址：{}",request.getRemoteAddr());

        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime",startTime);
        //OPTIONS请求不做校验
        //前后端分离的架构，前端会发一个OPTIONS请求做预检，对预检请求
        if (request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }

        String path = request.getRequestURL().toString();
        LOG.info("接口登录拦截：，path:{}",path);

        //获取header的token参数
        String token = request.getHeader("token");
        LOG.info("登录校验开始，token：{}",token);
        if (token == null||token.isEmpty()){
            LOG.info("token为空，请求拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        Object object = redisTemplate.opsForValue().get(token);
        if (object == null){
            LOG.warn("token无效，请求拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }else {
            LOG.info("已登录：{}",object);
            return true;
        }

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        long startTime = (long)request.getAttribute("requestStartTime");
        LOG.info("--------LogInterceptor 结束 耗时：{} ms--------",System.currentTimeMillis()-startTime);
    }
}
