package com.spg.web.controller;

import com.spg.commom.*;
import com.spg.domin.SuperAdmin;
import com.spg.service.SuperAdminServcie;
import com.spg.util.SessionUtil;
import com.spg.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author trevor
 * @date 03/27/19 12:33
 */
@Api(value = "超级管理员相关" ,description = "超级管理员相关")
@RestController
@Validated
public class SuperAdminController {

    @Resource
    private SuperAdminServcie superAdminServcie;

    @ApiOperation(value = "超级管理员登陆")
    @RequestMapping(value = "/admin/login", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> login(@RequestBody @Validated SuperAdminLogin superAdminLogin){
        JsonEntity<SuperAdmin> login = superAdminServcie.login(superAdminLogin);
        if (login.getCode() > 0) {
            Map<String, Object> map = TokenUtil.getMap("liubin", "liubingxfqrewqrtq");
            return ResponseHelper.createInstance(TokenUtil.generateToken(map) ,MessageCodeEnum.LOGIN_SUCCESS);
        }else {
            return new JsonEntity<>(login.getCode() ,login.getMessage());
        }
    }


    @ApiOperation(value = "超级管理员创建房间")
    @RequestMapping(value = "/admin/create/room", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> createRoom(){
        return superAdminServcie.createRoom();
    }

}
