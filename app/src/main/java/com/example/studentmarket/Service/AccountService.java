package com.example.studentmarket.Service;

import static com.example.studentmarket.Service.EndpointConstant.*;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class AccountService {
    public void SignUp(String email, String userName, String fullname, String phone, String password, Context context) {
        String url = SIGNUP_URL;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userAddress", "AAAAA");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            requestBody.put("userEmail", "email@gmail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            requestBody.put("accountName", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            requestBody.put("userFullName", fullname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            requestBody.put("userPhone", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            requestBody.put("password", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("SignUp response",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void Login(String accountName, String password) {
    }

    public void ChangePassword(String id, String oldPassword, String newPassword) {
    }

    public void ForgotPassword(String email) {
    }

    public void ValidateEmail(String email) {
    }

}
