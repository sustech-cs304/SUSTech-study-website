package com.example.mvcdemo2.model;

import jakarta.persistence.*;

@Entity
@Table
public class Student {
    @Id

    private int user_id;
    private int question_id;
    private int password;
    private int answer_count;
    private String tags;
    private String username;
    private String url;
    @Column(length = 50000)
    private String content;


    public Student(int question_id, int user_id, int password, int answer_count,
                   String tags,String content,String username, String url) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.password = password;
        this.answer_count = answer_count;
        this.tags = tags;
        this.content=content;
        this.username=username;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Student() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

//    @Override
//    public String toString() {
//        return "Student{" +
//            "id=" + id +
//            ", name='" + name + '\'' +
//            ", email='" + email + '\'' +
//            '}';
//    }
}
