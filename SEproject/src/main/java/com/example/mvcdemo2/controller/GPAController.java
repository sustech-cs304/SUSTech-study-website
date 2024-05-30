package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.GPA;
import com.example.mvcdemo2.repository.GPARepository;
import com.example.mvcdemo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gpa")
public class GPAController {
    @Autowired
    private GPARepository gpaRepository;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<String> saveGPA(@RequestBody GPARequest gpaRequest) {
        try {
            gpaRepository.saveGPA(gpaRequest.getUsername(), gpaRequest.getD3x());
            return new ResponseEntity<>("GPA saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving GPA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<GPA> getGPAByUsername(@PathVariable String username) {
        GPA gpa = gpaRepository.findByUsername(username);
        if (gpa != null) {
            return new ResponseEntity<>(gpa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping("/gpahistory")
    public String showgpahistory(Model model){
        //model.addAttribute("students", studentService.getStudents());
        return "gpahistory";
    }
}
class GPARequest {
    private String username;
    private String d3x;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getD3x() {
        return d3x;
    }

    public void setD3x(String d3x) {
        this.d3x = d3x;
    }
}
