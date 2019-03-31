package com.spg.web.controller;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.commom.WebKeys;
import com.spg.service.BizService;
import com.spg.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 17:03
 * @Description:
 */
@RestController("检查用户是否是第一个进入的，若是则成为房主，若不是则检查是否是房主的好友")
public class BizController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private BizService bizService;

    @ApiOperation(value = "得到一个新的token")
    @RequestMapping(value = "/get/token", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> getToken(){
        Map<String,Object> claims =  TokenUtil.getClaimsFromToken(request.getParameter("token"));
        claims.put("timestamp" ,System.currentTimeMillis());
        return ResponseHelper.createInstance(TokenUtil.generateToken(claims) , MessageCodeEnum.CREATE_SUCCESS);
    }

    @ApiOperation(value = "用于转发到聊天室")
    @RequestMapping(value = "/{roomId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void forwardChatRoom(@PathVariable("roomId") Long roomId) throws ServletException, IOException {
        request.getRequestDispatcher("/chat.html?roomId=" + roomId).forward(request,response);
    }

    @ApiOperation(value = "进入房间，检查用户是否是第一个进入的，若是则成为房主，若不是则检查是否是房主的好友")
    @RequestMapping(value = "/enter/{roomId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> enterRoom(@PathVariable("roomId") Long roomId){
        return bizService.enterRoom(roomId ,getOpenid());
    }

    @ApiOperation(value = "申请成为房主的好友")
    @RequestMapping(value = "/apply/{roomId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> applyRnterRoom(@PathVariable("roomId") Long roomId){
        return bizService.applyRnterRoom(roomId ,getOpenid());
    }

    private String getOpenid(){
        String token = request.getHeader("token");
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        return openid;
    }



}
