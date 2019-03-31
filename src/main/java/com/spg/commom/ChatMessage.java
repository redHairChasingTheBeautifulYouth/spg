package com.spg.commom;

import lombok.Data;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 17:26
 * @Description:
 */
@Data
public class ChatMessage {

    private Long userId;

    private Long time;

    private String message;

    private String messageType;
}
