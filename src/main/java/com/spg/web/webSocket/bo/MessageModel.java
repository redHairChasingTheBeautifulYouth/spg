package com.spg.web.webSocket.bo;

import lombok.Data;

/**
 * @author trevor
 * @date 03/30/19 16:03
 */
@Data
public class MessageModel {

    private Integer code;

    private String desc;

    private String data;

    public MessageModel(MessageModelEnum messageModelEnum){
        this.code = messageModelEnum.getCode();
        this.desc = messageModelEnum.getDesc();
    }

    public MessageModel(MessageModelEnum messageModelEnum ,String data){
        this.code = messageModelEnum.getCode();
        this.desc = messageModelEnum.getDesc();
        this.data = data;
    }
}
