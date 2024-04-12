package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.repository.trade_repository;
import org.springframework.stereotype.Controller;

@Controller
public class trade_controller {
    private final trade_repository tr;

    public trade_controller(trade_repository tr){
        this.tr = tr;
    }
}
