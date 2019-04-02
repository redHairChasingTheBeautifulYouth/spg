package com.spg.web.controller;

import com.spg.commom.*;
import com.spg.domin.User;
import com.spg.service.BizService;
import com.spg.util.TokenUtil;
import io.swagger.annotations.Api;
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
import java.util.List;
import java.util.Map;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 17:03
 * @Description:
 */
@Api(value = "业务相关" ,description = "业务相关")
@RestController
public class BizController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private BizService bizService;

    @ApiOperation(value = "得到一个新的token")
    @RequestMapping(value = "/get/token", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> getToken(){
        Map<String,Object> claims =  TokenUtil.getClaimsFromToken(request.getHeader("token"));
        claims.put("timestamp" ,System.currentTimeMillis());
        return ResponseHelper.createInstance(TokenUtil.generateToken(claims) , MessageCodeEnum.CREATE_SUCCESS);
    }

    @ApiOperation(value = "查询得到本房间的登陆用户信息,role为1房主，2管理员，3普通成员")
    @RequestMapping(value = "/login/user/{roomId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<LoginUser> getLoginUser(@PathVariable("roomId") Long roomId){
        return bizService.getLoginUser(getOpenid() ,roomId);
    }

    @ApiOperation(value = "用于转发到聊天室")
    @RequestMapping(value = "/forward/{roomId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void forwardChatRoom(@PathVariable("roomId") Long roomId) throws ServletException, IOException {
        request.getRequestDispatcher("/chat.html?roomId=" + roomId).forward(request,response);
    }

    @ApiOperation(value = "进入房间，检查用户是否是第一个进入的，若是则成为房主，若不是则检查是否是房主的好友")
    @RequestMapping(value = "/enter/{roomId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> enterRoom(@PathVariable("roomId") Long roomId){
        return bizService.enterRoom(roomId ,getOpenid());
    }

    @ApiOperation(value = "申请成为房主的好友")
    @RequestMapping(value = "/apply/{roomId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> applyRnterRoom(@PathVariable("roomId") Long roomId){
        return bizService.applyRnterRoom(roomId ,getOpenid());
    }

    @ApiOperation(value = "查询聊天记录")
    @RequestMapping(value = "/chat/record/{roomId}/{pageSize}/{pageNo}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<List<ReturnChatMessage>> chatRecord(@PathVariable("roomId") Long roomId , @PathVariable("pageSize") Integer pageSize , @PathVariable("pageNo") Integer pageNo){
        return bizService.chatRecord(getOpenid() ,roomId ,pageSize ,pageNo);
    }

    @ApiOperation(value = "查询群成员,普通成员查询已经通过申请的，管理员和房主查询已申请通过的、正在申请的、拒绝的。status为1房主，2管理员，3普通成员，4正在申请的，5拒绝的")
    @RequestMapping(value = "/chat/room/{roomId}/member", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<List<RoomMember>> qyeryMember(@PathVariable("roomId") Long roomId){
        return bizService.queryMember(getOpenid() ,roomId);
    }

    @ApiOperation(value = "通过好友申请、拒绝好友申请、将普通好友变管理员、取消管理员身份变成普通好友、将普通好友踢出，operation码分别是1，2，3，4，5")
    @RequestMapping(value = "/chat/room/{roomId}/member/{byOperationUserId}/{operation}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> operationChatRoom(@PathVariable("roomId") Long roomId,
                                                @PathVariable("byOperationUserId") Long byOperationUserId,
                                                @PathVariable("operation") Integer operation){
        return bizService.operationChatRoom(getOpenid() ,byOperationUserId ,roomId ,operation);
    }

    private String getOpenid(){
        String token = request.getHeader("token");
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        return openid;
    }



}
