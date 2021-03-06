package com.spg.service.impl;

import com.spg.dao.UserMapper;
import com.spg.domin.User;
import com.spg.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User  findByOpenid(String openid) {
        return userMapper.findByOpenid(openid);
    }

    @Override
    public void insertOne(User user) {
        this.userMapper.insertOne(user);
    }

    @Override
    public void updateUser(User user) {
        this.userMapper.updateUser(user);
    }

}
