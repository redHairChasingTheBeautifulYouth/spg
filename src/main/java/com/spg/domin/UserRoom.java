package com.spg.domin;

import lombok.Data;

@Data
public class UserRoom {

    private Long id;

    private Long userId;

    private Long roomId;

    private Integer roleType;

}
