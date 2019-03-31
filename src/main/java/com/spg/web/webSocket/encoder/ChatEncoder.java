package com.spg.web.webSocket.encoder;

import com.alibaba.fastjson.JSON;
import com.spg.web.webSocket.bo.MessageModel;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author trevor
 * @date 03/30/19 16:01
 */
public class ChatEncoder implements Encoder.Text<MessageModel>{


    @Override
    public void init(EndpointConfig config){

    }

    @Override
    public void destroy(){

    }

    @Override
    public String encode(MessageModel messageModel){
        return JSON.toJSONString(messageModel);
    }
}
