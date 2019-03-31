package com.spg.web.webSocket.config;

import com.spg.commom.WebKeys;
import com.spg.web.webSocket.bo.Transcript;
import lombok.Data;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: trevor
 * @Date: 2019\3\29 0029 23:19
 * @Description:
 */
@Data
public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {

    private ConcurrentHashMap<String , Set<Transcript>> userMap = new ConcurrentHashMap<>(2<<6);


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
