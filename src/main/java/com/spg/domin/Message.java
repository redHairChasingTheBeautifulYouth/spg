package com.spg.domin;

import com.spg.commom.ChatMessage;
import lombok.Data;

@Data
public class Message {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    private String appName;

    private String pictureUrl;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 发消息的时间
     */
    private Long time;

    /**
     * 1为加入新的聊天用户，2为聊天内容，3为图片
     */
    private Integer messageType;

    /**
     * 消息
     */
    private String message;

    public Message (ChatMessage chatMessage ,String roomId){
        this.userId = chatMessage.getUserId();
        this.roomId = Long.valueOf(roomId);
        this.time = chatMessage.getTime();
        this.messageType = chatMessage.getMessageType();
        this.message = chatMessage.getMessage();
        this.appName = chatMessage.getAppName();
        this.pictureUrl = chatMessage.getPictureUrl();
    }
}
