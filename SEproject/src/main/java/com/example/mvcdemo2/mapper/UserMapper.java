package com.example.mvcdemo2.mapper;

import com.example.mvcdemo2.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllUser();
}
