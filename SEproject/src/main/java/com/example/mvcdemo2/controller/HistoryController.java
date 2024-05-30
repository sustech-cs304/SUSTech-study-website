package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.History;

import com.example.mvcdemo2.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HistoryController {

    String usrName = "";
    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping("get_user_name")
    public String GetUserName(){
        return usrName;
    }
    @GetMapping("/his")
    public List<History> findAllHistory(){
        List<History> allHistory = historyRepository.findAllHistory();
        return allHistory;
    }
    @GetMapping("/hisname")
    public List<History> findHistoryByUsername() {
        List<History> hisByName = historyRepository.findByUsername(usrName);
        return hisByName;
    }

    @GetMapping("/api/quiz-history")
    public ModelAndView findHistoryByUsernameAndID(@RequestParam("id") int id) {
        List<History> historyList = historyRepository.findHisByNameAndID(usrName, id);
        ModelAndView mav = new ModelAndView("history"); // 视图名称，对应模板文件名
        mav.addObject("historyList", historyList);
        return mav;
    }

    @PostMapping("/save-answers")
    public ResponseEntity<?> saveAnswers(
            @RequestParam("answers") String answers,
            @RequestParam("quizId") int quizId) {

        // 处理答案字符串并保存到数据库
        historyRepository.saveAnswers(usrName, answers, quizId);

        // 假设成功保存后返回一个URL用于重定向
        Map<String, String> response = new HashMap<>();
        response.put("url", "/selection");
        return ResponseEntity.ok(response);
    }
}