package com.spg.web.controller;


import com.spg.commom.*;
import com.spg.service.WeixinService;
import com.spg.util.RandomUtils;
import com.spg.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一句话描述该类作用:【】
 *
 * @author: trevor
 * @create: 2019-03-14 0:56
 **/
@Api(value = "微信登陆相关" ,description = "微信登陆相关")
@RestController
public class WeixinLoginController{

    @Resource
    private WeixinService weixinService;

    @Resource
    private ConcurrentHashMap<String ,Object> concurrentHashMap;

    @ApiOperation("得到用户临时凭证uuid")
    @RequestMapping(value = "/front/weixin/login/forward", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> weixinForward()  {
        //用户临时凭证
        String uuid = RandomUtils.getRandomChars(40);
        concurrentHashMap.put(uuid ,System.currentTimeMillis());
        return ResponseHelper.createInstance(uuid ,MessageCodeEnum.CREATE_SUCCESS);
    }

    @ApiOperation("根据code码请求用户信息")
    @RequestMapping(value = "/front/weixin/login/get", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> checkAuth(@RequestParam("uuid") String uuid ,@RequestParam("code") String code) throws IOException {
        if (concurrentHashMap.get(uuid) == null) {
            return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.ERROR_NUM_MAX);
        }
        JsonEntity<String> jsonEntity = weixinService.weixinAuth(code);
        //授权成功
        if(jsonEntity.getCode() > 0){
            concurrentHashMap.remove(uuid);
            return jsonEntity;
        }else {
            return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.AUTH_FAILED);
        }
    }
}
