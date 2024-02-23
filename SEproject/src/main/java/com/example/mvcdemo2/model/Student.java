package com.example.mvcdemo2.model;

import jakarta.persistence.*;

@Entity
@Table
public class Student {
    @Id
    private int question_id;
    private int user_id;
    private int view_count;
    private int answer_count;
    private String tags;
    private String title;
    private String url;
    @Column(length = 50000)
    private String content;


    public Student(int question_id, int user_id, int view_count, int answer_count,
                   String tags,String content,String title, String url) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.view_count = view_count;
        this.answer_count = answer_count;
        this.tags = tags;
        this.content=content;
        this.title=title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
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
