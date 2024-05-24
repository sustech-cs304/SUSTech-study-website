package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface HistoryRepository extends JpaRepository<History,Integer> {
    @Query("SELECT his FROM History his")
    List<History> findAllHistory();

    @Query(value = "SELECT * FROM history WHERE username = :username", nativeQuery = true)
    List<History> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM history WHERE username = :name and setid = :id", nativeQuery = true)
    List<History> findHisByNameAndID(@Param("name") String name, @Param("id") Integer id);

    // 保存答案
    @Modifying
    @Query(value = "INSERT INTO history (username, data, setid) VALUES (:name, :data, :id)", nativeQuery = true)
    void saveAnswers(@Param("name") String name, @Param("data") String data, @Param("id") Integer id);
}
