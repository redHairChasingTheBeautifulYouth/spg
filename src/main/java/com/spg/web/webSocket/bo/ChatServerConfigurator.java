package com.spg.web.webSocket.bo;

import javax.websocket.server.ServerEndpointConfig;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: trevor
 * @Date: 2019\3\29 0029 23:19
 * @Description:
 */
public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {
    private ConcurrentHashMap<String ,String> userMap = new ConcurrentHashMap<>(2<<6);
}
