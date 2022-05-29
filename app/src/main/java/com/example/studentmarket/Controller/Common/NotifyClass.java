package com.example.studentmarket.Controller.Common;

public class NotifyClass {
    private String id;
    private String body;
    private String user_name;
    private String image;
    private String timeStamp;
    private String title;
    private String user_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public NotifyClass(String id, String user_name, String body, String image, String timeStamp, String title, String user_id) {
        this.id = id;
        this.user_name=user_name;
        this.body = body;
        this.image = image;
        this.timeStamp = timeStamp;
        this.title = title;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
