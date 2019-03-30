package com.spg.web.webSocket.bo;

import lombok.Data;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: trevor
 * @Date: 2019\3\29 0029 23:19
 * @Description:
 */
@Data
public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {
    private Transcript transcript;

    public ChatServerConfigurator(){
        this.transcript = new Transcript(20);
    }

    /**
     * 拦截打开握手阶段的HTTP请求和响应
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec , HandshakeRequest request , HandshakeResponse response){

    }
}
