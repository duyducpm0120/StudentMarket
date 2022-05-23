package com.example.studentmarket.Models;

import java.util.ArrayList;

public class UserProfile {
    public String userId;
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


    public String getUserId() {
        if (userId != null)
            return userId;
        return "";
    }

    public String getUserAddress() {
        if (userAddress != null)
            return userAddress;
        return "";
    }

    public String getUserEmail() {
        if (userEmail != null)
            return userEmail;
        return "";
    }

    public String getUserFullName() {
        if (userFullName != null)
            return userFullName;
        return "";
    }

    public String getUserPhone() {
        if (userPhone != null)
            return userPhone;
        return "";
    }

    public String getUserPic() {
        if (userPic != null)
            return userPic;
        return "";
    }

    public String getRole() {
        if (role != null)
            return role;
        return "";
    }

    public ArrayList<String> getAuthorities() {
        if (authorities != null)
            return authorities;
        return new ArrayList<String>();
    }

    public boolean isActive() {
        if (isActive)
            return isActive;
        return false;
    }

    public boolean isNotLocked() {
        if (isNotLocked)
            return isNotLocked;
        return false;
    }

    public String getAccountName() {
        if (accountName != null)
            return accountName;
        return "";
    }

    public String getUserUniversity() {
        if (userUniversity != null)
            return userUniversity;
        return "";
    }
}
