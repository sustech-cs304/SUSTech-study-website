package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.*;
import com.example.mvcdemo2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/new_post")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(posts); // 返回状态码200和帖子数据
    }

    @GetMapping("/posts/{id}")
    public String getPostDetails(@PathVariable Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "post_details"; // 返回 post_details.html 对应的视图名称
        } else {
            // 可以选择重定向到一个错误页面或者返回一个错误信息
            return "error_page"; // 或者是 return "redirect:/some_path";
        }
    }



    @GetMapping("/posts/id={id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 在您的 PostController 中
    @GetMapping("/posts/search")
    public ResponseEntity<List<Post>> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "all") String category,
            @RequestParam(defaultValue = "newest") String sortBy) {

        List<Post> posts = postRepository.findByTitleContainingAndCategoryOrderByPublishTime(
                title, category, sortBy);

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            comment.setPost(post.get());
            Comment savedComment = commentRepository.save(comment);
            return ResponseEntity.ok(savedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByPublishTimeDesc(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setLikes(updatedComment.getLikes() + 1);
            commentRepository.save(updatedComment);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/comments/{commentId}/dislike")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setDislikes(updatedComment.getDislikes() + 1);
            commentRepository.save(updatedComment);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
