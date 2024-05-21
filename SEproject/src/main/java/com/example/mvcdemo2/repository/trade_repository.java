package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface trade_repository extends JpaRepository<goods, Integer> {
}
