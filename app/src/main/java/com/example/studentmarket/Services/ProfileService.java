package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.GET_USER_PROFILE_PREFIX_URL;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studentmarket.Helper.ServiceQueue.ServiceQueue;
import com.example.studentmarket.Helper.VolleyErrorHelper;
import com.example.studentmarket.Models.LoginResponse;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.StorageKeyConstant;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ProfileService {
    private Context context;
    public ProfileService(Context context) {
        this.context = context;
    }

    public UserProfile getUserProfile (String userId) {
        String url = GET_USER_PROFILE_PREFIX_URL + "{" + userId.toString() + "}";

        JSONObject requestBody = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());

                        LoginResponse loginResponse = new Gson().fromJson(String.valueOf(response), LoginResponse.class);
                        Log.d("Login response token", loginResponse.getToken());
                        SharedStorage storage = new SharedStorage(context);
                        storage.saveValue(new StorageKeyConstant().getTokenIdKey(),loginResponse.getToken());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        VolleyErrorHelper helper = new VolleyErrorHelper(context);
                        helper.parseVolleyError(error,"Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại sau");
                    }

                });

        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);

        return new UserProfile("12","aaa","asd","asd","aaa");

    }
}
