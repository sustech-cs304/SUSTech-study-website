package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Comment;
import com.example.mvcdemo2.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    private Comment comment;
    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post("Test Title", "Test Category", "Test Content", "Test Author", LocalDateTime.now());
        comment = new Comment("Test Comment", "Test Author", LocalDateTime.now(), post);
    }

    @Test
    public void testCommentCreation() {
        assertEquals("Test Comment", comment.getContent());
        assertEquals("Test Author", comment.getAuthor());
        assertNotNull(comment.getPublishTime());
        assertEquals(post, comment.getPost());
        assertEquals(0, comment.getLikes());
        assertEquals(0, comment.getDislikes());
    }

    @Test
    public void testSettersAndGetters() {
        comment.setContent("New Comment");
        assertEquals("New Comment", comment.getContent());

        comment.setAuthor("New Author");
        assertEquals("New Author", comment.getAuthor());

        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        comment.setPublishTime(newTime);
        assertEquals(newTime, comment.getPublishTime());

        Post newPost = new Post("New Title", "New Category", "New Content", "New Author", LocalDateTime.now());
        comment.setPost(newPost);
        assertEquals(newPost, comment.getPost());

        comment.setLikes(5);
        assertEquals(5, comment.getLikes());

        comment.setDislikes(3);
        assertEquals(3, comment.getDislikes());
    }

    @Test
    public void testToString() {
        String toString = comment.toString();
        assertTrue(toString.contains("id=" + comment.getId()));
        assertTrue(toString.contains("content='" + comment.getContent() + "'"));
        assertTrue(toString.contains("author='" + comment.getAuthor() + "'"));
        assertTrue(toString.contains("publishTime=" + comment.getPublishTime()));
        assertTrue(toString.contains("post=" + comment.getPost()));
        assertTrue(toString.contains("likes=" + comment.getLikes()));
        assertTrue(toString.contains("dislikes=" + comment.getDislikes()));
    }
}
