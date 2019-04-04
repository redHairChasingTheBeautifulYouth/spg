package com.spg.commom;

import lombok.Data;

/**
 * @Auther: trevor
 * @Date: 2019\4\5 0005 02:07
 * @Description:
 */
@Data
public class LoginToken {

    /**
     * token令牌 过期时间默认15day
     */
    private String token;

    /**
     * 刷新token 过期时间可以设置为jwt的两倍，甚至更长，用于动态刷新token
     */
    private String refreshToken;

    /**
     * token过期时间戳
     */
    private Long tokenPeriodTime;
}
