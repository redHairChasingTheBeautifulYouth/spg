package com.spg.service;

import com.spg.domin.User;

public interface UserService {

    User findByOpenid(String openid);

    Boolean checkOpenidAndHash(String openid,String hash);

    void insertOne(User user);

    void updateHash(String hash ,String openid);



}
