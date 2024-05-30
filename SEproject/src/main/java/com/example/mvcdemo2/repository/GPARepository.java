package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.GPA;
import com.example.mvcdemo2.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GPARepository extends JpaRepository<GPA, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE GPA SET d3x = :d3x WHERE username = :username", nativeQuery = true)
    void saveGPA(@Param("username") String username, @Param("d3x") String d3x);


    GPA findByUsername(String username);
}
