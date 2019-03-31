package com.spg.web.interceptor;

import com.spg.commom.WebKeys;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.util.SessionUtil;
import com.spg.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
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
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取得客户端浏览器的类型
        String browserType = request.getHeader("user-agent").toLowerCase();
        if (StringUtils.isEmpty(browserType)) {
            response.sendRedirect("/error.html");
        }
        if (!browserType.contains(WebKeys.WEIXIN_BROWSER)) {
            response.sendRedirect("/error.html");
        }
        //从什么页面进来
        String reUrl = request.getRequestURI();
        //获取token
        String token = request.getHeader(WebKeys.TOKEN);
        if (token == null) {
            response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
        }
        try {
            //解析token
            Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
            String openid = (String) claims.get(WebKeys.OPEN_ID);
            String hash = (String) claims.get("hash");
            String timestamp = String.valueOf(claims.get("timestamp"));
            //三者必须存在,少一样说明token被篡改
            if (openid == null || hash == null || timestamp == null) {
                response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
            }
            //三者合法才通过
            if(!(checkOpenidAndHash(openid,hash) && checkTimeStamp(timestamp))){
                response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            //token非法
            response.sendRedirect("/front/weixin/login?reUrl=" + reUrl);
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 检查token是否过期
     * 开发时:指定1分钟,可以更好的看到效果
     * @param timestamp
     *
     *
     * @return
     */
    private boolean checkTimeStamp(String timestamp) {
        // 有效期: 30分钟,单位: ms
        long expires_in = 30 * 1000 * 20;
        long timestamp_long = Long.parseLong(timestamp);
        //两者相差的时间,单位(ms)
        long time = System.currentTimeMillis() - timestamp_long;
        if(time > expires_in){
            //过期
            return false;
        }else {
            return true;
        }
    }

    /**
     * 判断opendid,hash是否合法
     * true合法
     * false不合法
     * @param openid
     * @return
     */
    private Boolean checkOpenidAndHash(String openid,String hash){
        User user = userService.findUserByOpenidContainOpenidAndHash(openid);
        if(user.getOpenid() != null){
            //对比
            if(openid.equals(user.getOpenid()) && hash.equals(user.getHash())){
                return true;
            }
        }
        return false;
    }
}
