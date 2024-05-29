package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.demos.web.User;
import com.example.mvcdemo2.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u")
    List<Users> findAllUsers();

    @Query("SELECT u FROM Users u WHERE u.name = :username")
    Users findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (username, password) VALUES (:username, :password)", nativeQuery = true)
    void saveUser(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password = :password WHERE username = :username", nativeQuery = true)
    void updatePassword(@Param("username") String username, @Param("password") String password);

}
