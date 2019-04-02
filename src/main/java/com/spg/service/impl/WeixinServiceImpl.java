package com.spg.service.impl;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.commom.WebKeys;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.service.WeixinService;
import com.spg.util.RandomUtils;
import com.spg.util.TokenUtil;
import com.spg.util.WeixinAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author trevor
 * @date 2019/3/4 11:38
 */
@Service
@Slf4j
public class WeixinServiceImpl implements WeixinService {

    @Resource
    private UserService userService;

    @Override
    public JsonEntity<Map<String, Object>> weixinAuth(String code) throws IOException {
        //获取access_token
        Map<String, String> accessTokenMap = WeixinAuthUtils.getWeixinToken(code);
        //拉取用户信息
        Map<String, String> userInfoMap = WeixinAuthUtils.getUserInfo(accessTokenMap.get(WebKeys.ACCESS_TOKEN)
                ,accessTokenMap.get(WebKeys.OPEN_ID));
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
            //生成10位hash
            String hash = RandomUtils.getRandomChars(10);
            Map<String, Object> claims = TokenUtil.getMap(hash ,openid);
            //判断用户是否存在
            User user = userService.findByOpenid(openid);
            if (user == null) {
                //新增
                userService.insertOne(generateUser(hash ,userInfoMap));
            } else {
                //更新hash
                userService.updateHash(hash ,openid);
            }
            return ResponseHelper.createInstance(claims ,MessageCodeEnum.AUTH_SUCCESS);
        }
    }

    /**
     * 生成一个user
     * @return
     */
    private User generateUser(String hash ,Map<String, String> userInfoMap){
        User user = new User();
        user.setOpenid(userInfoMap.get("openid"));
        user.setAppName(userInfoMap.get("nickname"));
        //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
        user.setAppPictureUrl(userInfoMap.get("headimgurl"));
        user.setHash(hash);
        return user;
    }
}
