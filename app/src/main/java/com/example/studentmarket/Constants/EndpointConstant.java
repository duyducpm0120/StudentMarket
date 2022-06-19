package com.example.studentmarket.Constants;

public class EndpointConstant {
    public static final String ENDPOINT_PREFIX = "http://10.0.2.2:8080/";
    public static final String SIGNUP_URL = ENDPOINT_PREFIX + "user/signUp";
    public static final String LOGIN_URL = ENDPOINT_PREFIX + "user/login";
    public static final String RATING_USER_PREFIX_URL = ENDPOINT_PREFIX + "ratings/rate/";
    public static final String CHANGE_PASSWORD_URL = ENDPOINT_PREFIX + "/user/updatePassword";
    public static final String FORGOT_PASSWORD_PREFIX_URL = ENDPOINT_PREFIX + "/user/resetPassword/";
    public static final String GET_USER_PROFILE_PREFIX_URL = ENDPOINT_PREFIX + "users/findById/";
    public static final String GET_LIST_PRODUCT = ENDPOINT_PREFIX + "listings/getListings";
    public static final String SEARCH_PRODUCT = ENDPOINT_PREFIX + "listings/search";
    public static final String POST_PRODUCT = ENDPOINT_PREFIX + "post/";
    public static final String GET_LIST_CATEGORY = ENDPOINT_PREFIX + "listingCategories/getAll";
    public static final String GET_MY_PROFILE = ENDPOINT_PREFIX + "user/getMyProfile";
    public static final String GET_LIST_FAVORITE = ENDPOINT_PREFIX + "listings/mySavedListings";
    public static final String SAVE_PRODUCT_FAVORITE = ENDPOINT_PREFIX + "listings/save";
    public static final String CAN_SAVE_PRODUCT_FAVORITE = ENDPOINT_PREFIX + "listings/canSave";
    public static final String UNSAVE_PRODUCT_FAVORITE = ENDPOINT_PREFIX + "listings/unsave";
    public static final String GET_DETAIL_PRODUCT = ENDPOINT_PREFIX + "listings/getDetail";
    public static final String UPDATE_USER_PROFILE = ENDPOINT_PREFIX + "user/update";
    public static final String UPDATE_USER_AVATAR = ENDPOINT_PREFIX + "user/updateProfilePic";
    public static final String GET_MY_LIST_PRODUCT = ENDPOINT_PREFIX + "listings/myListings";
    public static final String PUSHNOTI_REGISTER_DEVICE = ENDPOINT_PREFIX + "pushNotifications/registerDevice";
    public static final String SEND_NEW_MESSAGE_NOTIFICATION = ENDPOINT_PREFIX + "pushNotifications/sendNewMessageNotification";
    public static final String GET_DETAIL_POSTER = ENDPOINT_PREFIX + "listings/getPosterProfile";
}
