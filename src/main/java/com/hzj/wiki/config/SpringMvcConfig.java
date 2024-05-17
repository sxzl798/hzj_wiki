package com.hzj.wiki.config;

import com.hzj.wiki.interceptor.LogInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    LogInterceptor logInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/test/**",
                        "/user/login",
                        "/category/all",
                        "/ebook/list",
                        "/doc/all/**",
                        "/doc/find-content/**",
                        "/ebook-snapshot/**"
                );
    }
}
