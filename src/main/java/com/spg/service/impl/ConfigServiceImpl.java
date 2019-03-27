package com.spg.service.impl;

import com.spg.dao.ConfigDao;
import com.spg.domin.Config;
import com.spg.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigDao configDao;

    @Override
    public List<Config> findDomainNamesCanUse() {
        return configDao.findDomainNamesCanUse();
    }
}
