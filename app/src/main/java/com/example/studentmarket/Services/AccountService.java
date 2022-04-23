package com.example.studentmarket.Services;

import static com.example.studentmarket.Services.EndpointConstant.*;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AccountService {
    public void SignUp(String email, String userName, String fullname, String phone, String password, Context context) throws JSONException {
        String url = SIGNUP_URL;

        JSONObject requestBody = new JSONObject();

        requestBody.put("userAddress", "AAAAA");

        requestBody.put("userEmail", email);

        requestBody.put("accountName", userName);

        requestBody.put("userFullName", fullname);

        requestBody.put("userPhone", email);

        requestBody.put("password", password);
        Log.i(requestBody.toString(), "Request body");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("SignUp response", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("SignUp response err", error.toString());

                    }
                });

        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void Login(String accountName, String password,  Context context) throws JSONException {
        String url = LOGIN_URL;

        JSONObject requestBody = new JSONObject();

        requestBody.put("accountName", accountName);

        requestBody.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("login response", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        parseVolleyError(error); //handle wrong username or password
                    }
                });

        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void ChangePassword(String id, String oldPassword, String newPassword) {
    }

    public void ForgotPassword(String email) {
    }

    public void ValidateEmail(String email) {
    }

    public void parseVolleyError(VolleyError error) {
        try {
//            String responseBody = new String(error.networkResponse.data, "utf-8");
//            JSONObject data = new JSONObject(responseBody);
//            JSONArray errors = data.getJSONArray("errors");
//            JSONObject jsonMessage = errors.getJSONObject(0);
//            String message = jsonMessage.getString("message");
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String message = data.optString("message");
            Log.d("err",message);
        } catch (JSONException e) {
            Log.d("err",e.toString());
        } catch (UnsupportedEncodingException errorr) {
            Log.d("err",errorr.toString());
        }
    }

}
