package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.User;
import com.example.mvcdemo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public List<User> getAllUser(){
        List<User> allUser = userService.findAllUser();
        return allUser;
    }
}
