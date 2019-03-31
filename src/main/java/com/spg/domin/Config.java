package com.spg.domin;

import lombok.Data;

@Data
public class Config {

    private Long id;

    /**
     * 域名
     */
    private String domainName;
}
