package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.GET_USER_PROFILE_PREFIX_URL;

import android.content.Context;

import com.example.studentmarket.Models.UserProfile;

public class ProfileService {
    private Context context;
    public ProfileService(Context context) {
        this.context = context;
    }

    public UserProfile getUserProfile (String userId) {
        String url = GET_USER_PROFILE_PREFIX_URL + "{" + userId.toString() + "}";
        return new UserProfile("12","aaa","asd","asd","aaa");

    }
}
