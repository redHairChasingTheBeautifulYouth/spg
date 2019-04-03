package com.spg.web.controller;


import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.util.RandomUtils;
import com.spg.util.SessionUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 一句话描述该类作用:【】
 *
 * @author: trevor
 * @create: 2019-03-14 0:56
 **/
@Api(value = "测试用暂时登录" ,description = "测试用暂时登录")
@RestController
@Slf4j
public class TestLoginController {

    @Resource
    private UserService userService;

    @ApiOperation("只需点一下就可以登录了，转到/api/login/user获取用户信息")
    @RequestMapping(value = "/api/testLogin/login", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> weixinAuth(HttpServletRequest request, HttpServletResponse response){
        Long time = System.currentTimeMillis();
        String openid = time + "";
        String hash = RandomUtils.getRandomChars(10);
        Map<String, Object> claims = TokenUtil.getMap(hash ,openid);
        String token = TokenUtil.generateToken(claims);
        User user = new User();
        user.setOpenid(openid);
        user.setHash(hash);
        user.setAppName("登录测试名字");
        user.setAppPictureUrl("https://raw.githubusercontent.com/redHairChasingTheBeautifulYouth/Java-Guide/master/imgs/20181101-1.jpg");
        userService.insertOne(user);
        log.info("测试登录成功 ，hash值---------" + hash);
        SessionUtil.getSession().setAttribute("token" ,token);
        return ResponseHelper.createInstance(token ,MessageCodeEnum.AUTH_SUCCESS);
    }
}
