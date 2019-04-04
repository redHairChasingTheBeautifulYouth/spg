package com.spg.dao;

import com.spg.domin.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findByOpenid(@Param("openid") String openid);

    void insertOne(@Param("user") User user);

    void updateUser(@Param("user") User user);
}
