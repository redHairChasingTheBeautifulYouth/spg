package com.spg.web.webSocket.decoder;

import com.alibaba.fastjson.JSON;
import com.spg.commom.ReceiveChatMessage;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author trevor
 * @date 03/30/19 16:01
 */
public class ChatDecoder implements Decoder.Text<ReceiveChatMessage> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy(){

    }

    @Override
    public ReceiveChatMessage decode(String str) {
        return JSON.parseObject(str ,ReceiveChatMessage.class);
    }

    @Override
    public boolean willDecode(String s){
        return true;
    }
}
