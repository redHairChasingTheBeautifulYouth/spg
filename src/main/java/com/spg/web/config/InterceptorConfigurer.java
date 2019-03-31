package com.spg.web.config;

import com.spg.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: trevor
 * @Date: 2019\3\28 0028 01:03
 * @Description:
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/weixin/auth**")
                //排除不需要验证登录用户操作权限的请求
                .excludePathPatterns("/weixinLogin.html")
                .excludePathPatterns("/superAdminLogin.html")
                .excludePathPatterns("/get/token")
                .excludePathPatterns("/images/**");
    }

}
