package com.example.studentmarket.Services;

public class EndpointConstant {
    public static final String ENDPOINT_PREFIX = "http://10.0.2.2:8080/";
    public static final String SIGNUP_URL = ENDPOINT_PREFIX + "user/signUp";
    public static final String LOGIN_URL = ENDPOINT_PREFIX + "user/login";
    public static final String RATING_USER_PREFIX_URL = ENDPOINT_PREFIX + "ratings/rate/";
    public static final String CHANGE_PASSWORD_URL = ENDPOINT_PREFIX + "/user/updatePassword";
    public static final String FORGOT_PASSWORD_PREFIX_URL = ENDPOINT_PREFIX + "/user/resetPassword/";
}
