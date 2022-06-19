package com.example.studentmarket.Controller.Message;

public class Messenger {
    private String id;
    private String name;
    private String imageUrl;
    private String msg;
    private String time;

    public Messenger(String id,String name, String imageUrl, String msg,String time) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.msg = msg;
        this.time = time;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }
    public void seId() {
        this.id = id;
    }
}
