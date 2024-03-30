package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 添加新的查询方法以支持多参数搜索和排序
    @Query("SELECT p FROM Post p WHERE " +
            "(:title IS NULL OR p.title LIKE %:title%) AND " +
            "(:category = 'all' OR p.category = :category) " +
            "ORDER BY CASE WHEN :sortBy = 'newest' THEN p.publishTime END DESC, " +
            "CASE WHEN :sortBy = 'oldest' THEN p.publishTime END ASC")
    List<Post> findByTitleContainingAndCategoryOrderByPublishTime(
            @Param("title") String title,
            @Param("category") String category,
            @Param("sortBy") String sortBy);


    // 使用JPQL查询特定类别的帖子，并按发布时间降序排序
    @Query("SELECT p FROM Post p WHERE p.category = :category ORDER BY p.publishTime DESC")
    List<Post> findByCategoryOrderByPublishTimeDesc(@Param("category") String category);

    // 使用原生SQL查询特定作者的帖子数量
    @Query(value = "SELECT COUNT(*) FROM posts WHERE author = :author", nativeQuery = true)
    int countPostsByAuthor(@Param("author") String author);


}
