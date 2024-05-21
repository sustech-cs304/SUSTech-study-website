package com.example.mvcdemo2.service;

import com.example.mvcdemo2.model.History;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryService {
    List<History> findAllHistory();
    List<History> findHisByName(String name);
    List<History> findHisByNameAndID(String name, int id);
}
