package com.example.studentmarket.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProfileModel implements Serializable {
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
    public UniversityModel university;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setUserUniversity(UniversityModel userUniversity) {
        this.university = userUniversity;
    }

    public int getUserId() {
        return userId;
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

    public UniversityModel getUserUniversity() {
        return university;
    }
}
