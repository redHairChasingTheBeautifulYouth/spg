package com.spg.service.impl;

import com.google.common.collect.Maps;
import com.spg.commom.*;
import com.spg.domin.User;
import com.spg.service.ConfigService;
import com.spg.service.UserService;
import com.spg.service.WeixinService;
import com.spg.util.*;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author trevor
 * @date 2019/3/4 11:38
 */
@Service
@Slf4j
public class WeixinServiceImpl implements WeixinService {

    @Resource
    private UserService userService;

    @Value("${weixinimgs.path}")
    private String weixinimgsPath;


    @Override
    public JsonEntity<String> weixinAuth(String code) throws IOException {
        //获取access_token
        Map<String, String> accessTokenMap = WeixinAuthUtils.getWeixinToken(code);
        //拉取用户信息
        Map<String, String> userInfoMap = WeixinAuthUtils.getUserInfo(accessTokenMap.get(WebKeys.ACCESS_TOKEN)
                , accessTokenMap.get(WebKeys.OPEN_ID));
        //有可能access token以被使用
        if (userInfoMap.get(WebKeys.ERRCODE) != null) {
            log.error("拉取用户信息 失败啦,快来围观:-----------------" + userInfoMap.get(WebKeys.ERRMSG));
            //刷新access_token
            Map<String, String> accessTokenByRefreshTokenMap = WeixinAuthUtils.getWeixinTokenByRefreshToken(accessTokenMap.get(WebKeys.REFRESH_TOKEN));
            // 再次拉取用户信息
            userInfoMap = WeixinAuthUtils.getUserInfo(accessTokenByRefreshTokenMap.get(WebKeys.ACCESS_TOKEN),
                    accessTokenByRefreshTokenMap.get(WebKeys.OPEN_ID));
        }
        String openid = userInfoMap.get(WebKeys.OPEN_ID);
        if (openid == null) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.AUTH_FAILED);
        } else {
            //判断用户是否存在
            User user = userService.findByOpenid(openid);
            Map<String,Object> claims = Maps.newHashMap();
            if (user == null) {
                //保存用户头像
                String imgUrl = userInfoMap.get("headimgurl");
                //新增
                String hash = RandomUtils.getRandomChars(10);
                user = new User();
                user.setOpenid(openid);
                user.setHash(hash);
                user.setAppName(userInfoMap.get("nickname"));
                user.setAppPictureUrl(userInfoMap.get("headimgurl"));
                userService.insertOne(user);

                claims.put("openid" ,user.getOpenid());
                claims.put("hash" ,user.getHash());
                claims.put("timestamp" ,System.currentTimeMillis());
            } else {
                //更新头像，昵称，hash
                user.setAppName(userInfoMap.get("nickname"));
                String hash = RandomUtils.getRandomChars(10);
                user.setHash(hash);
                user.setAppPictureUrl(userInfoMap.get("headimgurl"));
                userService.updateUser(user);

                claims.put("openid" ,user.getOpenid());
                claims.put("hash" ,user.getHash());
                claims.put("timestamp" ,System.currentTimeMillis());
            }

            String token = TokenUtil.generateToken(claims);
            return ResponseHelper.createInstance(token, MessageCodeEnum.AUTH_SUCCESS);
        }
    }

}
