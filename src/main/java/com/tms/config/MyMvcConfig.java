package com.tms.config;

import com.tms.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor requestInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自己的拦截器,并设置拦截的请求路径
        //addPathPatterns为拦截此请求路径的请求
        //excludePathPatterns为不拦截此路径的请求
        registry.addInterceptor(requestInterceptor).addPathPatterns("/user/*").excludePathPatterns("/user/login")
                .excludePathPatterns("/story/signOrRegister");
    }
}