package com.spg.web.interceptor;

import com.spg.commom.WebKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: trevor
 * @Date: 2019\3\28 0028 01:22
 * @Description:
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取得客户端浏览器的类型
        String browserType = request.getHeader("user-agent").toLowerCase();
        if (browserType.indexOf(WebKeys.WEIXIN_BROWSER) < 0) {
            response.sendRedirect("/error.html");
        }
        //从什么页面进来
        String reUrl = request.getRequestURI();
        //获取token
        String token = request.getHeader(WebKeys.TOKEN);
        if (token == null) {
            response.sendRedirect("/weixinLogin.html");
        }
        return super.preHandle(request, response, handler);
    }
}
