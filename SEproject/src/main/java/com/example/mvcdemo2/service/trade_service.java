package com.example.mvcdemo2.service;

import com.example.mvcdemo2.repository.trade_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class trade_service{
    private final trade_repository trade_repository;

    @Autowired
    public trade_service(trade_repository trade_repository){
        this.trade_repository = trade_repository;
    }
}
