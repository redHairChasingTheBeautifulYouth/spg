package com.spg.web.controller;


import com.spg.commom.*;
import com.spg.util.RandomUtils;
import com.spg.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @ApiOperation("微信登录并转发到微信登录页面")
    @RequestMapping(value = "/front/weixin/login", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void login() throws ServletException, IOException {
        //用户临时凭证
        String uuid = RandomUtils.getRandomChars(40);
        //使用全局变量，微信授权成功后改变值
        TempUser tempUser = new TempUser(AuthEnum.NOT_AUTH.getCode() ,"" ,"");
        request.getServletContext().setAttribute(uuid ,tempUser);
        request.getRequestDispatcher("/weixinlogin.html?uuid=" + uuid + "&reUrl=" + request.getParameter("reUrl"))
                .forward(request,response);
    }

    @ApiOperation("检查微信是否授权")
    @RequestMapping(value = "/front/weixin/login/check", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> checkAuth()  {
        String uuid = request.getParameter(WebKeys.UUID);
        //判断微信是否已经授权
        TempUser tempUser = (TempUser) request.getServletContext().getAttribute(uuid);
        if(AuthEnum.IS_AUTH.getCode().equals(tempUser.getIsAuth())){
            SessionUtil.getSession().setAttribute(WebKeys.TOKEN ,tempUser.getToken());
            return ResponseHelper.createInstance(tempUser.getToken() ,MessageCodeEnum.AUTH_SUCCESS);
        }else {
            return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.AUTH_FAILED);
        }
    }
}
