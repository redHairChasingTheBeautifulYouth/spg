package com.spg.web.webSocket.decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spg.web.webSocket.bo.MessageModel;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author trevor
 * @date 03/30/19 16:01
 */
public class ChatDecoder implements Decoder.Text<MessageModel> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy(){

    }

    @Override
    public MessageModel decode(String str) {
        return JSON.parseObject(str ,MessageModel.class);
    }

    @Override
    public boolean willDecode(String s){
        return true;
    }
}
