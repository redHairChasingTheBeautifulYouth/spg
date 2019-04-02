package com.spg.commom;

import lombok.Data;

/**
 * @author trevor
 * @date 04/02/19 10:18
 */
@Data
public class RoomMember {

    private Long id;

    private String appName;

    private String pictureUrl;

    /**
     * 1房主，2管理员，3普通成员，4正在申请的，5拒绝的
     */
    private Integer status;
}
