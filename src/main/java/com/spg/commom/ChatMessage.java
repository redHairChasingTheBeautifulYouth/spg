package com.spg.commom;

import com.spg.domin.User;
import lombok.Data;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 17:26
 * @Description:
 */
@Data
public class ChatMessage {

    private Long userId;

    private String appName;

    private String pictureUrl;

    private Long time;

    /**
     * 信息内容
     */
    private String message;

    /**
     * 1为加入新的聊天用户，2为聊天内容，3为图片
     */
    private Integer messageType;

    public ChatMessage (User user , ReceiveChatMessage receiveChatMessage){
        this.userId = user.getId();
        this.appName = user.getAppName();
        this.pictureUrl = user.getAppPictureUrl();
        this.time = System.currentTimeMillis();
        this.message = receiveChatMessage.getMessage();
        this.messageType = receiveChatMessage.getMessageType();
    }
}
