package com.spg.web.webSocket;

import com.spg.web.webSocket.bo.ChatServerConfigurator;
import com.spg.web.webSocket.bo.Transcript;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @Auther: trevor
 * @Date: 2019\3\29 0029 22:24
 * @Description:
 */
@ServerEndpoint(
        value = "/chat/{chatRoomId}}",
        configurator = ChatServerConfigurator.class
)
public class ChatServer {
    private static String USERNAME_KEY = "username";
    private static String USERNAMES_KEY = "usernames";

    private Session session;

    private ServerEndpointConfig endpointConfig;

    private Transcript transcript;

    @OnOpen
    public void startChatChannel(EndpointConfig endpointConfig ,Session session) {
        this.endpointConfig = (ServerEndpointConfig) endpointConfig;
        ChatServerConfigurator csc = (ChatServerConfigurator) ((ServerEndpointConfig) endpointConfig).getConfigurator();
        this.transcript = csc.getTranscript();
        this.session = session;
    }





}
