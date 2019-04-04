package com.spg.service;

import com.spg.domin.User;

public interface UserService {

    User findByOpenid(String openid);

    void insertOne(User user);

    void updateUser(User user);



}
