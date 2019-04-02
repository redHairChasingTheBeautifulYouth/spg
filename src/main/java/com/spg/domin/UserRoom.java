package com.spg.domin;

import lombok.Data;

@Data
public class UserRoom {

    private Long id;

    private Long userId;

    private Long roomId;

    /**
     * 1为房主，2为管理员，3为普通成员，4为正在申请的 ，5为拒绝的
     */
    private Integer roleType;

}
