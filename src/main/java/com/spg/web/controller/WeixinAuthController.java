package com.spg.web.controller;

import com.spg.commom.AuthEnum;
import com.spg.commom.JsonEntity;
import com.spg.commom.TempUser;
import com.spg.commom.WebKeys;
import com.spg.service.WeixinService;
import com.spg.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: trevor
 * @Date: 2019\3\28 0028 00:21
 * @Description:
 */
@Api(value = "微信回调" ,description = "微信回调")
@RestController
@Slf4j
public class WeixinAuthController {

    @Resource
    private WeixinService weixinService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private ConcurrentHashMap<String ,Long> timestampMap;

    @ApiOperation("微信授权回调地址")
    @RequestMapping(value = "/public/api/weixin/auth", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void weixinAuth() throws IOException {
        String code = request.getParameter(WebKeys.CODE);
        String uuid = request.getParameter(WebKeys.UUID);
        TempUser tempUser = (TempUser) request.getServletContext().getAttribute(uuid);
        //授权来源不正确
        if (tempUser == null) {
            return;
        }
        JsonEntity<Map<String, Object>> jsonEntity = weixinService.weixinAuth(code);
        if(jsonEntity.getCode() < 0){
            log.error(jsonEntity.getMessage());
        }else {
            //生成token
            Map<String, Object> map = jsonEntity.getData();
            String openid = (String) map.get(WebKeys.OPEN_ID);
            String hash = (String) map.get("hash");
            String timestamp = (String) map.get("timestamp");
            String token = TokenUtil.generateToken(map);
            tempUser = new TempUser(AuthEnum.IS_AUTH.getCode() ,token);
            request.getServletContext().setAttribute(uuid,tempUser);
            request.getServletContext().setAttribute(openid+hash+timestamp ,System.currentTimeMillis());
        }
    }

}
