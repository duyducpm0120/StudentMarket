package com.example.studentmarket.Controller.Common;

public class type {
    private String id;
    private String name;
    private int image;
    private boolean show;

    public type(String id,String name, int image,boolean show) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
