package com.spg.web.webSocket.bo;

import com.spg.commom.ChatMessage;
import com.spg.commom.ChatUser;
import lombok.Data;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: trevor
 * @Date: 2019\3\30 0030 00:13
 * @Description:
 */
@Data
public class Transcript {

    private CopyOnWriteArrayList<ChatUser> messages = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<ChatMessage> userNames = new CopyOnWriteArrayList<>();

}
