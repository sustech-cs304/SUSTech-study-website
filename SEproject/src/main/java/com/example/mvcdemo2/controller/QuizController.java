package com.begin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {

    @GetMapping("/selection")
    public String quizSelection() {
        return "quizSelection"; // 返回quizSelection.html页面
    }
}
