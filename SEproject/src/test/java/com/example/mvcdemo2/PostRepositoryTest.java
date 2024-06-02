package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Post;
import com.example.mvcdemo2.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 使用实际的PostgreSQL数据库
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testFindByTitleContainingAndCategoryOrderByPublishTime() {
        Post post = new Post();
        post.setTitle("Spring Data JPA");
        post.setCategory("Technology");
        post.setContent("Content about Spring Data JPA");
        post.setAuthor("Jane Doe");
        post.setPublishTime(LocalDateTime.now());

        postRepository.save(post);

        List<Post> foundPosts = postRepository.findByTitleContainingAndCategoryOrderByPublishTime(
                "Spring", "Technology", "newest");

        assertFalse(foundPosts.isEmpty());
        assertEquals("Spring Data JPA", foundPosts.get(0).getTitle());
    }

    @Test
    public void testFindByCategoryOrderByPublishTimeDesc() {
        Post post = new Post();
        post.setTitle("Spring Boot Guide");
        post.setCategory("Technology");
        post.setContent("Content about Spring Boot");
        post.setAuthor("John Doe");
        post.setPublishTime(LocalDateTime.now());

        postRepository.save(post);

        List<Post> foundPosts = postRepository.findByCategoryOrderByPublishTimeDesc("Technology");

        assertFalse(foundPosts.isEmpty());
        assertEquals("Spring Boot Guide", foundPosts.get(0).getTitle());
    }

    @Test
    public void testCountPostsByAuthor() {
        Post post = new Post();
        post.setTitle("Java Basics");
        post.setCategory("Education");
        post.setContent("Content about Java Basics");
        post.setAuthor("Alice Smith");
        post.setPublishTime(LocalDateTime.now());

        postRepository.save(post);

        int count = postRepository.countPostsByAuthor("Alice Smith");

        assertEquals(1, count);
    }

    @Test
    public void testFindByAuthor() {
        Post post = new Post();
        post.setTitle("Hibernate Tutorial");
        post.setCategory("Technology");
        post.setContent("Content about Hibernate");
        post.setAuthor("Bob Johnson");
        post.setPublishTime(LocalDateTime.now());

        postRepository.save(post);

        List<Post> foundPosts = postRepository.findByAuthor("Bob Johnson");

        assertFalse(foundPosts.isEmpty());
        assertEquals("Hibernate Tutorial", foundPosts.get(0).getTitle());
    }
}
