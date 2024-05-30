package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.Users;

import com.example.mvcdemo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryController historyController;

    @GetMapping("/user")
    public List<Users> getAllUser(){
        List<Users> allUser = userRepository.findAllUsers();
        return allUser;
    }

    @GetMapping("/user/find")
    public ResponseEntity<Users> findByUsername(@RequestParam String username) {
        Users user = userRepository.findByUsername(username);
        historyController.setUsrName(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("user/create")
    public ResponseEntity<?> create(
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        if (userRepository.findByUsername(username) != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Username already exists");
            return ResponseEntity.badRequest().body(response);
        }

        userRepository.saveUser(username,password);
        Map<String, String> response = new HashMap<>();
        response.put("url", "/selection");
        historyController.setUsrName(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String oldPassword = payload.get("oldPassword");
        String newPassword = payload.get("newPassword");

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        if (!user.getPassword().equals(oldPassword)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Old password is incorrect");
            return ResponseEntity.badRequest().body(response);
        }

        userRepository.updatePassword(username, newPassword);
        return ResponseEntity.ok().build();
    }

}