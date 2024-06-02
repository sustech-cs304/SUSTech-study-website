package com.example.mvcdemo2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String author;
    private LocalDateTime publishTime;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore // 添加这个注解
    private Post post;

    private int likes; // 新增属性
    private int dislikes; // 新增属性

    public Comment() {}

    public Comment(String content, String author, LocalDateTime publishTime, Post post) {
        this.content = content;
        this.author = author;
        this.publishTime = publishTime;
        this.post = post;
        this.likes = 0;
        this.dislikes = 0;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    // 新增的 getter 和 setter 方法
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publishTime=" + publishTime +
                ", post=" + post +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
