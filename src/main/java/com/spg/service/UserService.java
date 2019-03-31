package com.spg.service;

import com.spg.domin.User;

public interface UserService {

    User findUserByOpenidContainOpenidAndHash(String openid);
}
