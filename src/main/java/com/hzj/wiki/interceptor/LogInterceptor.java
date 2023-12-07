package com.hzj.wiki.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        LOG.info("---------LogInterceptor 开始---------");
        LOG.info("请求地址：{} {}",request.getRequestURI().toString(), request.getMethod());
        LOG.info("远程地址：{}",request.getRemoteAddr());

        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime",startTime);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        long startTime = (long)request.getAttribute("requestStartTime");
        LOG.info("--------LogInterceptor 结束 耗时：{} ms--------",System.currentTimeMillis()-startTime);
    }
}
