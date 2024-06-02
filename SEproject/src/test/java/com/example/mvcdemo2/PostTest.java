package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Comment;
import com.example.mvcdemo2.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post("Test Title", "Test Category", "Test Content", "Test Author", LocalDateTime.now());
    }

    @Test
    public void testPostCreation() {
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Category", post.getCategory());
        assertEquals("Test Content", post.getContent());
        assertEquals("Test Author", post.getAuthor());
        assertNotNull(post.getPublishTime());
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment("Test Comment", "Test Author", LocalDateTime.now(), post);
        post.addComment(comment);

        assertEquals(1, post.getComments().size());
        assertEquals("Test Comment", post.getComments().get(0).getContent());
        assertEquals(post, post.getComments().get(0).getPost());
    }

    @Test
    public void testRemoveComment() {
        Comment comment = new Comment("Test Comment", "Test Author", LocalDateTime.now(), post);
        post.addComment(comment);
        post.removeComment(comment);

        assertEquals(0, post.getComments().size());
        assertNull(comment.getPost());
    }

    @Test
    public void testSettersAndGetters() {
        post.setTitle("New Title");
        assertEquals("New Title", post.getTitle());

        post.setCategory("New Category");
        assertEquals("New Category", post.getCategory());

        post.setContent("New Content");
        assertEquals("New Content", post.getContent());

        post.setAuthor("New Author");
        assertEquals("New Author", post.getAuthor());

        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        post.setPublishTime(newTime);
        assertEquals(newTime, post.getPublishTime());
    }

    @Test
    public void testToString() {
        String toString = post.toString();
        assertTrue(toString.contains("id=" + post.getId()));
        assertTrue(toString.contains("title='" + post.getTitle() + "'"));
        assertTrue(toString.contains("category='" + post.getCategory() + "'"));
        assertTrue(toString.contains("content='" + post.getContent() + "'"));
        assertTrue(toString.contains("publishTime=" + post.getPublishTime()));
    }
}
