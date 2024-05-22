package com.example.mvcdemo2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String data;
    private Integer setID;

    public History(Integer id,String name, String data, Integer setID) {
        this.username = name;
        this.id = id;
        this.data = data;
        this.setID = setID;
    }

    public History() {

    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", data='" + data + '\'' +
                ", setID=" + setID +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSetID(Integer setID) {
        this.setID = setID;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getData() {
        return data;
    }

    public Integer getSetID() {
        return setID;
    }
}
