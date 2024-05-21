package com.example.mvcdemo2.service;

import com.example.mvcdemo2.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {
    List<User> findAllUser();
}
