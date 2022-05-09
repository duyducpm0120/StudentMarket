package com.example.studentmarket.Controller.Common;

public class product {
    private int id;
    private String address;
    private String body;
    private String image;
    private String timeStamp;
    private String title;
    private int status;
    private int poster;
    private String price;
    private boolean heart;

    public product(int id, String address, String body, String image, String timeStamp, String title, int status,
            int poster, String price, boolean heart) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.image = image;
        this.timeStamp = timeStamp;
        this.title = title;
        this.status = status;
        this.poster = poster;
        this.price = price;
        this.heart = heart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }
}
