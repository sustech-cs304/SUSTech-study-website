package com.example.mvcdemo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface trade_repository extends JpaRepository<goods, Integer> {
}
