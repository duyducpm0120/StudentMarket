package com.example.studentmarket.Controller.Message;

public class FirebaseConversation {
    private String id;
    private String user1;
    private String user2;
    private String userName1;
    private String userName2;
    private String img1;
    private String img2;

    public FirebaseConversation(){

    }

    public FirebaseConversation(String id, String user1, String user2,String userName1,String userName2, String img1, String img2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.img1 = img1;
        this.img2 = img2;
        this.userName1 = userName1;
        this.userName2 = userName2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getUserName1() {
        return userName1;
    }
    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }
    public String getUserName2() {
        return userName2;
    }
    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }
}
