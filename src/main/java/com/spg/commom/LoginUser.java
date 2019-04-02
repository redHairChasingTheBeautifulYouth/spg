package com.spg.commom;

import lombok.Data;

/**
 * @author trevor
 * @date 04/02/19 17:04
 */
@Data
public class LoginUser {
    private Long id;

    private String appName;

    private String pictureUrl;

    /**
     * 用户在本房间的角色，1房主，2管理员，3普通成员
     */
    private Integer role;
}
