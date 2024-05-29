package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.GPA;
import com.example.mvcdemo2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GPARepository extends JpaRepository<GPA, Integer> {
}
