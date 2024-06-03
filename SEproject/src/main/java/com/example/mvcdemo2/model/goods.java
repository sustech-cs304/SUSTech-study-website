package com.example.mvcdemo2.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int good_id;
    private String name;
    private int price;
    @ElementCollection
    private List<String> tags;
    private String seller;
    private byte[] image;
    private String description;

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    private int view;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private String contact;

    public goods(String name, int price, List<String> tags, String seller, byte[] image, String description) {
        this.name = name;
        this.price = price;
        this.tags = tags;
        this.seller = seller;
        this.image = image;
        this.description = description;
    }

    public goods() {

    }

    public int getGood_id() {
        return good_id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getSeller() {
        return seller;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}