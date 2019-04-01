package com.spg.commom;

import lombok.Data;

/**
 * @author trevor
 * @date 04/01/19 11:16
 */
@Data
public class ReturnChatMessage {

    /**
     * 消息类型，1为加入新的聊天用户，2为聊天内容，3为图片
     */
    private Integer messageType;

    /**
     * 是否是自己的聊天内容，1为是，0为否
     */
    private Integer isMyself;

    /**
     * 消息
     */
    private String message;

    /**
     * 用户名字
     */
    private String appName;

    /**
     *  用户头像
     */
    private String pictureUrl;

    public ReturnChatMessage(Integer isMyself ,ChatMessage chatMessage){
        this.isMyself = isMyself;
        this.appName = chatMessage.getAppName();
        this.message = chatMessage.getMessage();
        this.messageType = chatMessage.getMessageType();
        this.pictureUrl = chatMessage.getPictureUrl();
    }
}
