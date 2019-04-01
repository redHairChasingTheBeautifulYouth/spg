package com.spg.web.webSocket.encoder;

import com.alibaba.fastjson.JSON;
import com.spg.commom.ReturnChatMessage;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author trevor
 * @date 03/30/19 16:01
 */
public class ChatEncoder implements Encoder.Text<ReturnChatMessage>{


    @Override
    public void init(EndpointConfig config){

    }

    @Override
    public void destroy(){

    }

    @Override
    public String encode(ReturnChatMessage returnChatMessage){
        return JSON.toJSONString(returnChatMessage);
    }
}
