package com.spg.domin;

import lombok.Data;

/**
 * @author trevor
 * @date 03/27/19 12:57
 */
@Data
public class SuperAdmin {

    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 错误次数，
     */
    private Integer errorNum;

}
