package com.example.mvcdemo2.repository;

import com.example.mvcdemo2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdOrderByPublishTimeDesc(Long postId);


    // 更新赞的数量
    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.likes = c.likes + 1 WHERE c.id = :commentId")
    void incrementLikes(Long commentId);

    // 更新踩的数量
    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.dislikes = c.dislikes + 1 WHERE c.id = :commentId")
    void incrementDislikes(Long commentId);
}
