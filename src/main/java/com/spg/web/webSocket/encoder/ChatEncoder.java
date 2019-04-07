package com.spg.web.webSocket.encoder;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.spg.commom.ReturnChatMessage;
import com.spg.util.TokenUtil;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

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


//    public static void main(String[] s){
//        String openid = "1554471678601";
//        String hash = "e5r9jlwfi1lmqimmxhk9";
//        Map<String,Object> claims = Maps.newHashMap();
//        claims.put("openid" ,openid);
//        claims.put("hash" ,hash);
//        claims.put("timestamp" ,System.currentTimeMillis());
//        System.out.println(TokenUtil.generateToken(claims));
//    }
}
