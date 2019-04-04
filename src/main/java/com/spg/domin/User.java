package com.spg.domin;

import lombok.Data;

/**
 * 一句话描述该类作用:【玩家信息】
 *
 * @author: trevor
 * @create: 2019-03-03 23:14
 **/
@Data
public class User {

    /**
     * id
     */
    private Long id;

    /**
     * 唯一的openid
     */
    private String openid;

    /**
     * hash值
     */
    private String hash;

    /**
     * 用户昵称
     */
    private String appName;

    /**
     * 用户头像地址
     */
    private String appPictureUrl;


}
