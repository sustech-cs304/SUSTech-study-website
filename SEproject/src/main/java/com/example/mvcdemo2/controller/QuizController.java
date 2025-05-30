package com.example.mvcdemo2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    @GetMapping("/main")
    public String showMain(){
        return "main";
    }

    @GetMapping("/quiz")
    public String showQuiz(@RequestParam(name = "id", required = false) String quizId, Model model) {
        System.out.println("Received quizId: " + quizId);
        if (quizId != null) {
            model.addAttribute("quizId", quizId);
        }
        return "quiz";
    }

    @GetMapping("/selection")
    public String quizSelection() {
        return "quizSelection"; // 返回quizSelection.html页面
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // 返回quizSelection.html页面
    }

//    @PostMapping("/quiz-selection")
//    public RedirectView selectQuiz(@RequestParam("quizId") String quizId) {
//        return new RedirectView("/quiz?id=" + quizId);
//    }

}
