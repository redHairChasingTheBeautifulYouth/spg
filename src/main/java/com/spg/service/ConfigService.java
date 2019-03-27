package com.spg.service;


import com.spg.domin.Config;

import java.util.List;

public interface ConfigService {

    /**
     * 查询可用域名
     * @return
     */
    List<Config> findDomainNamesCanUse();
}
