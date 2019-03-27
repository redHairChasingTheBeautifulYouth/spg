package com.spg.domin;

import lombok.Data;

@Data
public class Message {

    private Long id;

    private Long userId;

    private Long roomId;

    /**
     * 1为文字消息，2为图片，3为表情
     */
    private Integer messageType;

    private String message;
}
