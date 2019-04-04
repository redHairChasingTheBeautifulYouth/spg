package com.spg.web.interceptor;

import com.spg.commom.WebKeys;
import com.spg.service.UserService;
import com.spg.util.SessionUtil;
import com.spg.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: trevor
 * @Date: 2019\3\28 0028 01:22
 * @Description:
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取得客户端浏览器的类型
        String browserType = request.getHeader("user-agent").toLowerCase();
        //校验浏览器
//        if (StringUtils.isEmpty(browserType) || !browserType.contains(WebKeys.WEIXIN_BROWSER)) {
//            log.info("浏览器类型不匹配，重定向到error页面");
//            response.sendRedirect("/error.html");
//            return false;
//        }
        //从什么页面进来
        String reUrl = request.getRequestURI();
        //获取token

        String token = request.getHeader(WebKeys.TOKEN);
        if (token == null) {
            response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
            return false;
        }
        //session token
        String sessionToken = (String) SessionUtil.getSession().getAttribute("token");
        if (sessionToken == null) {
            response.sendRedirect("/front/weixin/login/refresh");
            return false;
        }
        if (!Objects.equals(token ,sessionToken)) {
            response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
            return false;
        }
        try {
            //解析token
            Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
            String openid = (String) claims.get(WebKeys.OPEN_ID);
            String hash = (String) claims.get("hash");
            String timestamp = (String) claims.get("timestamp");

            //三者必须存在,少一样说明token被篡改
            if (openid == null || hash == null || timestamp == null) {
                response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
                return false;
            }
            //合法才通过
            if(!(userService.checkOpenidAndHash(openid,hash))){
                response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
                return false;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            //token非法
            response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
            return false;
        }
    }


}
