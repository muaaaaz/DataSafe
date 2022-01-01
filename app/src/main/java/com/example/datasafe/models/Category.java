package com.example.datasafe.models;

public class Category {
    int id;
    int uid;
    String name;

    public Category(int id, int uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
    }

    public Category(int uid, String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
