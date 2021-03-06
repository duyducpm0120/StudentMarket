package com.example.studentmarket.Controller.Message;

public class Messenger {
    private String id;
    private String name;
    private String posterId;
    private String imageUrl;
    private String msg;
    private String time;
    private String rawTime;

    public Messenger(String id,String posterId,String name, String imageUrl, String msg,String time,String rawTime) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.msg = msg;
        this.time = time;
        this.id = id;
        this.posterId = posterId;
        this.rawTime = rawTime;
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

    public String getPosterId() {
        return posterId;
    }
    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }
    public String getRawTime() {
        return rawTime;
    }
    public void setRawTime(String rawTime) {
        this.rawTime = rawTime;
    }

}
