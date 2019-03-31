package com.spg.service.impl;

import com.spg.dao.UserDao;
import com.spg.domin.User;
import com.spg.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User  findUserByOpenidContainOpenidAndHash(String openid) {
        return userDao.findUserByOpenidContainOpenidAndHash(openid);
    }

}
