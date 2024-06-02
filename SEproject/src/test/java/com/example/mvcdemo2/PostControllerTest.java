package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.PostController;
import com.example.mvcdemo2.model.Comment;
import com.example.mvcdemo2.model.Post;
import com.example.mvcdemo2.repository.CommentRepository;
import com.example.mvcdemo2.repository.PostRepository;
import com.example.mvcdemo2.controller.HistoryController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private HistoryController historyController;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testGetAllPosts() throws Exception {
        Post post = new Post();
        post.setId(1L);  // Ensure the ID is set
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))  // Ensure JSON path matches
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    public void testGetPostDetails() throws Exception {
        Post post = new Post();
        post.setId(1L);  // Ensure the ID is set
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post_details"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", post));
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post();
        post.setId(1L);  // Ensure the ID is set
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))  // Ensure JSON path matches
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testCreateComment() throws Exception {
        Post post = new Post();
        post.setId(1L);  // Ensure the ID is set
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Comment comment = new Comment();
        comment.setId(1L);  // Ensure the ID is set
        comment.setContent("Test Comment");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Test Comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))  // Ensure JSON path matches
                .andExpect(jsonPath("$.content").value("Test Comment"));
    }

    @Test
    public void testGetCommentsByPostId() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);  // Ensure the ID is set
        comment.setContent("Test Comment");

        Mockito.when(commentRepository.findByPostIdOrderByPublishTimeDesc(1L)).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))  // Ensure JSON path matches
                .andExpect(jsonPath("$[0].content").value("Test Comment"));
    }
}
