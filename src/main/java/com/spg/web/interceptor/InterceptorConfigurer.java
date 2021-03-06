package com.spg.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Auther: trevor
 * @Date: 2019\3\28 0028 01:03
 * @Description:
 */
@Component
public class InterceptorConfigurer implements WebMvcConfigurer{

    @Resource
    private CORSInterceptor corsInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor)
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                //排除swagger
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/swagger-ui.html/**")
                //排除测试登陆
                .excludePathPatterns("/api/testLogin/login")
                //排除错误页面
                .excludePathPatterns("/error.html")
                //排除超级管理员登陆
                .excludePathPatterns("/admin/login")
                //排除微信转发到微信登录页面/检查微信授权地址
                .excludePathPatterns("/front/weixin/login/**")
                //排除微信回调地址
                .excludePathPatterns("/public/api/weixin/auth");

    }

}
