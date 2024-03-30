package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.Post;
import com.example.mvcdemo2.repository.PostRepository;
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




//    @GetMapping("/posts/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
//        Optional<Post> post = postRepository.findById(id);
//        if (post.isPresent()) {
//            return ResponseEntity.ok(post.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
