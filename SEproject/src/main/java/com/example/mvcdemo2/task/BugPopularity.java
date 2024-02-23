package com.example.mvcdemo2.task;

import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BugPopularity {
    @Autowired
    private StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);

    @GetMapping("/Syntax")
    public String showSyntax(Model model){
        LOGGER.info("Enter Syntax page");
        return "Syntax";
    }

    @GetMapping("/Fatal")
    public String showFatal(Model model){
        LOGGER.info("Enter Fatal page");
        return "Fatal";
    }

    @GetMapping("/Exceptions")
    public String showException(Model model){
        LOGGER.info("Enter Exceptions page");
        return "Exceptions";
    }

    @GetMapping("/between")
    public String showBetween(){
        return "between";
    }

}
