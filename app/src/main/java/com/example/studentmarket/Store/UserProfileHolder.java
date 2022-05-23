package com.example.studentmarket.Store;

import com.example.studentmarket.Models.UserProfile;

public class UserProfileHolder {
    private UserProfile userData;
    public UserProfile getData() {return userData;}
    public void setData(UserProfile data) {this.userData = data;}

    private static final UserProfileHolder holder = new UserProfileHolder();
    public static UserProfileHolder getInstance() {return holder;}
}
