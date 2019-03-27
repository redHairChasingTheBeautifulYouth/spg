package com.spg.service.impl;

import com.spg.dao.ConfigDao;
import com.spg.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigDao configDao;

    @Override
    public ConfigDao findOneCanUse() {
        return configDao.findOneCanUse();
    }
}
