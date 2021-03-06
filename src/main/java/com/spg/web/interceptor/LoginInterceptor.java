package com.spg.web.interceptor;

import com.spg.commom.WebKeys;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.util.SessionUtil;
import com.spg.util.ThreadLocalUtil;
import com.spg.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
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

    private final static String redirectUrl = "www.knave.top/wechat/";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(WebKeys.TOKEN);
        if (token == null) {
            response.sendRedirect(redirectUrl);
            return false;
        }
        try {
            //解析token
            Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
            String openid = (String) claims.get("openid");
            String hash = (String) claims.get("hash");
            Long timestamp = (Long) claims.get("timestamp");

            //三者必须存在,少一样说明token被篡改
            if (openid == null || hash == null || timestamp == null) {
                response.sendRedirect(redirectUrl);
                return false;
            }
            //合法才通过
            User user = userService.findByOpenid(openid);
            if (user != null && Objects.equals(hash ,user.getHash())) {
                ThreadLocalUtil.getInstance().bind(userService.findByOpenid(openid));
                return Boolean.TRUE;
            }else {
                response.sendRedirect(redirectUrl);
                return false;
            }
        } catch (Exception e) {
            //token非法
            log.error(e.toString());
            response.sendRedirect(redirectUrl);
            return false;
        }
    }


}
