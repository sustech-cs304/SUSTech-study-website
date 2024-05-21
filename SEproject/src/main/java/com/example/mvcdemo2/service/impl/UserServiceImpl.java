package com.example.mvcdemo2.service.impl;

import com.example.mvcdemo2.mapper.UserMapper;
import com.example.mvcdemo2.model.User;
import com.example.mvcdemo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User>findAllUser(){
        List<User> allUser = userMapper.findAllUser();
        return allUser;
    }
}
