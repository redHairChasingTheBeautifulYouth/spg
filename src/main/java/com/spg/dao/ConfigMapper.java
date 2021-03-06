package com.spg.dao;

import com.spg.domin.Config;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMapper {

    List<Config> findDomainNamesCanUse();
}
