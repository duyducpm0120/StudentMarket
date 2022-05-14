package com.example.studentmarket.Models;

public class UserProfile {
    private String userId;
    private String userFullName;
    private String userEmail;
    private String userPhone;
    private String userUniversity;
    private String user_pic;

    public String getUser_pic() {
        return user_pic;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public UserProfile(
            String userId,
            String userFullName,
            String userEmail,
            String userPhone,
            String userUniversity
    ) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userUniversity = userUniversity;
    }
}
