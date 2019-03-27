package com.spg.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ConfigDao {

    ConfigDao findOneCanUse();
}
