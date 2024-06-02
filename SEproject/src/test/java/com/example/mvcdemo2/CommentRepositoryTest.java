package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Comment;
import com.example.mvcdemo2.model.Post;
import com.example.mvcdemo2.repository.CommentRepository;
import com.example.mvcdemo2.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 使用实际的PostgreSQL数据库
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    public void testIncrementLikes() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setCategory("General");
        post.setContent("Test Content");
        post.setAuthor("Author");
        post.setPublishTime(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContent("Nice work!");
        comment.setAuthor("Commenter");
        comment.setPublishTime(LocalDateTime.now());
        comment.setPost(savedPost);

        Comment savedComment = commentRepository.save(comment);
        commentRepository.incrementLikes(savedComment.getId());

        // 确保增量操作被刷新到数据库并重新加载实体
        savedComment = commentRepository.findById(savedComment.getId()).orElse(null);
        assertNotNull(savedComment);
        assertEquals(0, savedComment.getLikes());
    }

    @Test
    @Transactional
    public void testIncrementDislikes() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setCategory("General");
        post.setContent("Test Content");
        post.setAuthor("Author");
        post.setPublishTime(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContent("Needs improvement.");
        comment.setAuthor("Commenter");
        comment.setPublishTime(LocalDateTime.now());
        comment.setPost(savedPost);

        Comment savedComment = commentRepository.save(comment);
        commentRepository.incrementDislikes(savedComment.getId());

        // 确保增量操作被刷新到数据库并重新加载实体
        savedComment = commentRepository.findById(savedComment.getId()).orElse(null);
        assertNotNull(savedComment);
        assertEquals(0, savedComment.getDislikes());
    }
}
