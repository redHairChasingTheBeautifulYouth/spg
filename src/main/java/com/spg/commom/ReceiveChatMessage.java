package com.spg.commom;

import lombok.Data;

/**
 * @author trevor
 * @date 04/01/19 11:12
 */
@Data
public class ReceiveChatMessage {

    /**
     * 消息类型，1为加入新的聊天用户，2为聊天内容，3为图片
     */
    private Integer messageType;

    /**
     * 消息
     */
    private String message;
}
