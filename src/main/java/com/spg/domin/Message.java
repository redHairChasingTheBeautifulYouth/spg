package com.spg.domin;

import lombok.Data;

@Data
public class Message {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 发消息的时间
     */
    private Long time;

    /**
     * 1为文字消息，2为图片，3为表情
     */
    private Integer messageType;

    /**
     * 消息
     */
    private String message;
}
