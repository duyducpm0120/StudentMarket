package com.example.studentmarket.Store;

import com.example.studentmarket.Models.UserProfileModel;

public class UserProfileHolder {
    private UserProfileModel userData;

    public UserProfileModel getData() {
        return userData;
    }

    public void setData(UserProfileModel data) {
        this.userData = data;
    }

    private static final UserProfileHolder holder = new UserProfileHolder();

    public static UserProfileHolder getInstance() {
        return holder;
    }
}
