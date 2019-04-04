package com.spg.web.webSocket.bo;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 18:10
 * @Description:
 */
public enum  MessageModelEnum {

    /**
     * token 过期
     */
    TOKEN_TIME_ERROR("-400" ,"token 过期"),

    /**
     * 还不是房主的好友
     */
    NO_AUTH("-2" ,"还不是房主的好友"),

    /**
     * token 错误
     */
    TOKEN_ERROR("-401" ,"token 错误");

    private String code;

    private String desc;

    MessageModelEnum(String code , String message){
        this.code = code;
        this.desc = message;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
