package com.wanglu.movcat.configuration;

import com.wanglu.movcat.interceptor.WebLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerInterceptor getWebLoginInterceptor(){
        return new WebLoginInterceptor();
    }

    //登录拦截器
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        //registry.addInterceptor(getWebLoginInterceptor()).addPathPatterns("/giftArticle/**").excludePathPatterns("api/path/login");
        registry.addInterceptor(getWebLoginInterceptor()).addPathPatterns("/user/**");
        super.addInterceptors(registry);
    }
}