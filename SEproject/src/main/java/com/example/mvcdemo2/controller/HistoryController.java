package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.mapper.HistoryMapper;
import com.example.mvcdemo2.model.History;
import com.example.mvcdemo2.model.User;
import com.example.mvcdemo2.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HistoryController {
    String usrName = "jerry";
    @Autowired
    private HistoryService historyService;
    @GetMapping("/his")
    public List<History> findAllHistory(){
        List<History> allHistory = historyService.findAllHistory();
        return allHistory;
    }
    @GetMapping("/hisname")
    public List<History> findHistoryByUsername() {
        List<History> hisByName = historyService.findHisByName(usrName);
        return hisByName;
    }
    @GetMapping("/api/quiz-history")
    public List<History> findHistoryByUsernameAndID(@RequestParam("id") int id) {
        return historyService.findHisByNameAndID(usrName, id);
    }
}
