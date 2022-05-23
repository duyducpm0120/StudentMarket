package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.GET_MY_PROFILE;
import static com.example.studentmarket.Constants.EndpointConstant.GET_USER_PROFILE_PREFIX_URL;
import static com.example.studentmarket.Constants.EndpointConstant.UPDATE_USER_PROFILE;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studentmarket.Helper.ServiceHeaderHelper.ServiceHeaderHelper;
import com.example.studentmarket.Helper.ServiceQueue.ServiceQueue;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Helper.VolleyErrorHelper;
import com.example.studentmarket.Models.UserProfile;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ProfileService {
    private Context context;

    public ProfileService(Context context) {
        this.context = context;
    }

    public void getUserProfile(String userId, VolleyCallback callback) {
        String url = GET_USER_PROFILE_PREFIX_URL + "{" + userId.toString() + "}";

        JSONObject requestBody = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());

                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new ServiceHeaderHelper(context).getHeadersWithToken();
            }
        };
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getMyProfile(VolleyCallback callback) {
        String url = GET_MY_PROFILE;

        JSONObject requestBody = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new ServiceHeaderHelper(context).getHeadersWithToken();
            }
        };
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void updateUserProfile(UserProfile userProfile, VolleyCallback callback) throws JSONException {
        String url = UPDATE_USER_PROFILE;

        JSONObject requestBody = new JSONObject();

        requestBody.put("userFullName", userProfile.getUserFullName());
        requestBody.put("university", userProfile.getUserUniversity());
        requestBody.put("userPhone", userProfile.getUserPhone());
        requestBody.put("userEmail", userProfile.getUserEmail());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());

                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new ServiceHeaderHelper(context).getHeadersWithToken();
            }
        };
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
