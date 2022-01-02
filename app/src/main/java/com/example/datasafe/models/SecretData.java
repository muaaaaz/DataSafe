package com.example.datasafe.models;


import java.io.Serializable;

public class SecretData implements Serializable {
    int id;     // secret data id
    int uid;    // user id
    int cid;    // category id
    String title;   // title to display
    String data;    // secret data

    public SecretData(int id, int uid, int cid, String title, String data) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
        this.title = title;
        this.data = data;
    }

    public SecretData(int uid, int cid, String title, String data) {
        this.uid = uid;
        this.cid = cid;
        this.title = title;
        this.data = data;
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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
