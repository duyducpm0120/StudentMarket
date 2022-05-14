package com.example.studentmarket.Models;

import java.util.ArrayList;

public class UserProfile {
    public int userId;
    public String userAddress;
    public String userEmail;
    public String userFullName;
    public String userPhone;
    public String userPic;
    public String role;
    public ArrayList<String> authorities;
    public boolean isActive;
    public boolean isNotLocked;
    public String accountName;
    public String userUniversity;

    public int getUserId() {
        return userId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserPic() {
        return userPic;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getUserUniversity() {
        return userUniversity;
    }
}
