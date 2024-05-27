package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.Users;

import com.example.mvcdemo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public List<Users> getAllUser(){
        List<Users> allUser = userRepository.findAllUsers();
        return allUser;
    }
}