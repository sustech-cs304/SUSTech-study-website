package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 根据类别查找帖子
    List<Post> findByCategory(String category);

    // 根据标题模糊查询
    List<Post> findByTitleContaining(String title);

    // 使用JPQL查询特定类别的帖子，并按发布时间降序排序
    @Query("SELECT p FROM Post p WHERE p.category = :category ORDER BY p.publishTime DESC")
    List<Post> findByCategoryOrderByPublishTimeDesc(@Param("category") String category);

    // 使用原生SQL查询特定作者的帖子数量
    @Query(value = "SELECT COUNT(*) FROM posts WHERE author = :author", nativeQuery = true)
    int countPostsByAuthor(@Param("author") String author);
}
