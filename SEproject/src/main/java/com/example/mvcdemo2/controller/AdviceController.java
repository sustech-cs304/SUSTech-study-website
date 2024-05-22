package com.example.mvcdemo2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdviceController {

    @GetMapping("/advice")
    public String showAdvicePage() {
        return "study_advice"; // 返回study_advice.html视图
    }

    @GetMapping("/post")
    public String showPostPage() {
        return "new_post"; // 返回post.html视图
    }

    @GetMapping("/new_post")
    public String showNewPostPage() {
        return "new_post"; // 返回new_post.html视图
    }
}
