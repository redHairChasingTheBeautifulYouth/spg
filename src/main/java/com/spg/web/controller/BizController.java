package com.spg.web.controller;

import com.spg.commom.JsonEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 17:03
 * @Description:
 */
@RestController("检查用户是否是第一个进入的，若是则成为房主，若不是则检查是否是房主的好友")
public class BizController {

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "检查用户是否是第一个进入的，若是则成为房主，若不是则检查是否是房主的好友")
    @RequestMapping(value = "/check/user/{roomId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonEntity<String> checkUserRoom(Long roomId){

    }
}
