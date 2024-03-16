package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.service.StudentService;
import com.example.mvcdemo2.task.TopicPopularity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
@Controller
@ComponentScan(basePackages = {"com.example.mvcdemo2.service"}) // Add your service's package here
public class StudentController {

    private final StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/list")
    public String getStudents(Model model){
        model.addAttribute("students", studentService.getStudents());
        return "index";
    }

    @RequestMapping("/main")
    public String showMain(Model model){
        //model.addAttribute("students", studentService.getStudents());
        LOGGER.info("Enter main page");
        return "gpa";
    }

    @RequestMapping("/gpa")
    public String showGPA(Model model){
        //model.addAttribute("students", studentService.getStudents());
        LOGGER.info("Enter gpa page");
        return "gpa";
    }
    @GetMapping("/")
    public String home() {
        return "main"; // Assuming 'main' is the name of your home page template
    }
}
