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
import static org.junit.jupiter.api.Assertions.assertEquals; // 确保导入了这个类

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
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    public void testGetPostDetails() throws Exception {
        Post post = new Post();
        post.setId(1L);
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
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }



    @Test
    public void testCreateComment() throws Exception {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Test Comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Test Comment"));
    }

    @Test
    public void testGetCommentsByPostId() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        Mockito.when(commentRepository.findByPostIdOrderByPublishTimeDesc(1L)).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].content").value("Test Comment"));
    }

    @Test
    public void testLikeComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setLikes(0);

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/comments/1/like"))
                .andExpect(status().isOk());

        Mockito.verify(commentRepository, Mockito.times(1)).save(comment);
        assertEquals(1, comment.getLikes());
    }

    @Test
    public void testDislikeComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setDislikes(0);

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/comments/1/dislike"))
                .andExpect(status().isOk());

        Mockito.verify(commentRepository, Mockito.times(1)).save(comment);
        assertEquals(1, comment.getDislikes());
    }

    @Test
    public void testSearchPosts() throws Exception {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Mockito.when(postRepository.findByTitleContainingAndCategoryOrderByPublishTime(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Collections.singletonList(post));

        mockMvc.perform(get("/posts/search")
                        .param("title", "Test")
                        .param("category", "all")
                        .param("sortBy", "newest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }



    @Test
    public void testCreateCommentPostNotFound() throws Exception {
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Test Comment\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLikeCommentNotFound() throws Exception {
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/comments/1/like"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDislikeCommentNotFound() throws Exception {
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/comments/1/dislike"))
                .andExpect(status().isNotFound());
    }
}