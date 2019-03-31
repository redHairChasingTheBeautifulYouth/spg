package com.spg.dao;

import com.spg.domin.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User findUserByOpenidContainOpenidAndHash(String openid);
}
