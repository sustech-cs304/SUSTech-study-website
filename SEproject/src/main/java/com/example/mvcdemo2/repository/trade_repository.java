package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface trade_repository extends JpaRepository<goods, Integer> {
    @Query("SELECT g FROM goods g WHERE g.name ILIKE %:searchQuery%")
    List<goods> findByNameContaining(@Param("searchQuery") String searchQuery);

    @Query("SELECT g FROM goods g WHERE g.seller = :searchQuery")
    List<goods> findBySeller(@Param("searchQuery") String searchQuery);
}