package com.example.mvcdemo2.repository;
import com.example.mvcdemo2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query(value = "SELECT * FROM admin WHERE username = :username", nativeQuery = true)
    Admin findAdmin(@Param("username") String username);
}
