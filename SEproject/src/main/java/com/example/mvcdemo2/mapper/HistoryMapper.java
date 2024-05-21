package com.example.mvcdemo2.mapper;

import com.example.mvcdemo2.model.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<History> findAllHistory();
    List<History> findHisByName(String name);
    List<History> findHisByNameAndID(@Param("name") String name, @Param("id") int id);
}
