package com.spg.web.webSocket.config;

import com.spg.web.webSocket.ChatServer;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 15:42
 * @Description:
 */
public class HttpSessionInvalidationListener implements HttpSessionBindingListener {

    private ChatServer chatServer;

    public HttpSessionInvalidationListener(ChatServer chatServer){
        this.chatServer = chatServer;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        chatServer.notifySessionUnboundMe(event.getSession());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {

    }
}
