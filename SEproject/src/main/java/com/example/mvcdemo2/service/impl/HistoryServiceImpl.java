package com.example.mvcdemo2.service.impl;
import com.example.mvcdemo2.mapper.HistoryMapper;
import com.example.mvcdemo2.model.History;
import com.example.mvcdemo2.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Override
    public List<History> findAllHistory(){
        List<History> allHistory = historyMapper.findAllHistory();
        return allHistory;
    }
    @Override
    public List<History> findHisByName(String name){
        List<History> hisByName = historyMapper.findHisByName(name);
        return hisByName;
    }
    @Override
    public List<History> findHisByNameAndID(String name, int id){
        List<History> hisByNameAndID = historyMapper.findHisByNameAndID(name, id);
        return hisByNameAndID;
    }
}
