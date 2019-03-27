package com.spg.commom;

/**
 * 一句话描述该类作用:【】
 *
 * @author: trevor
 * @create: 2019-03-08 0:24
 **/

public enum MessageCodeEnum {

    /*****************************************               http返回消息                    *********************/

    /**
     * 账号或密码错误
     */
    SUPER_ADMIN_LOGIN_ERROR(-1 ,"账号或密码错误"),

    /**
     * 操你妈，想黑爸爸呢
     */
    ERROR_NUM_MAX(-2 ,"操你妈，想黑爸爸呢"),

    /**
     * 无可用域名了
     */
    NO_DOMAIN_NAMES(-3 ,"无可用域名了"),

    /**
     * 草，报异常了
     */
    SYSTEM_ERROR(-6 ,"鸡巴，报错了"),

    /**
     * 授权失败
     */
    AUTH_FAILED(-4 ,"授权失败"),

    /**
     * 草，参数错误
     */
    PARAM_ERROR(-50 ,"草，参数错误"),

    /**
     * 登陆成功
     */
    LOGIN_SUCCESS(1 ,"登陆成功"),

    /**
     * 创建成功
     */
    CREATE_SUCCESS(2 ,"创建成功");



    /*****************************************               websocket返回消息                    *********************/


    private Integer code;

    private String message;

    MessageCodeEnum(Integer code , String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}