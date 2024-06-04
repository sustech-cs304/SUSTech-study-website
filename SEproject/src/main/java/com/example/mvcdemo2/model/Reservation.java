package com.example.mvcdemo2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String time_slot;
    private String location;
    private Integer participants;
    private String firstName; // 第一位预约者的姓名
    private String firstStudentId; // 第一位预约者的学号
    private String roomType; // 房间类型
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstStudentId() {
        return firstStudentId;
    }

    public void setFirstStudentId(String firstStudentId) {
        this.firstStudentId = firstStudentId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time_slot='" + time_slot + '\'' +
                ", location='" + location + '\'' +
                ", participants=" + participants +
                ", firstName='" + firstName + '\'' +
                ", firstStudentId='" + firstStudentId + '\'' +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}
