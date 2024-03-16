package com.example.mvcdemo2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AIController {

    @GetMapping("/ai_chat")
    public String aiChat() {
        return "ai_chat"; // ai_chat.html in the /templates directory
    }
}
