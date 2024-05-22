package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.demos.web.User;
import com.example.mvcdemo2.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u")
    List<Users> findAllUsers();
}
