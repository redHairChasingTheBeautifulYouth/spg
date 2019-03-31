package com.spg.service;

import com.spg.domin.User;

public interface UserService {

    User findByOpenid(String openid);
}
