package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.CHANGE_PASSWORD_URL;
import static com.example.studentmarket.Constants.EndpointConstant.FORGOT_PASSWORD_PREFIX_URL;
import static com.example.studentmarket.Constants.EndpointConstant.LOGIN_URL;
import static com.example.studentmarket.Constants.EndpointConstant.SIGNUP_URL;

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
import com.example.studentmarket.Models.response.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AccountService {
    private Context context;

    public AccountService(Context context) {
        this.context = context;
    }

    public void SignUp(String email, String userName, String fullname, String phone, String password) throws Exception {
        String url = SIGNUP_URL;

        JSONObject requestBody = new JSONObject();

        requestBody.put("userAddress", "AAAAA");

        requestBody.put("userEmail", email);

        requestBody.put("accountName", userName);

        requestBody.put("userFullName", fullname);

        requestBody.put("userPhone", phone);

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

    public void Login(String accountName, String password, VolleyCallback callback) throws JSONException {
        String url = LOGIN_URL;

        LoginResponse res;

        JSONObject requestBody = new JSONObject();

        requestBody.put("accountName", accountName);

        requestBody.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
//                         Log.d("login response", response.toString());
//                         setUsername(accountName);
//                         setToken(response.toString());
//                         PopupHelper popup = new PopupHelper(context,"Thông báo", "Đăng nhập thành công");
//                         popup.Show();
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                         // TODO: Handle error
//                         Log.d("response err", error.toString());
//                         PopupHelper popup = new PopupHelper(context, "Thông báo","Đăng nhập thất bại, vui lòng thử lại");
//                         Toast.makeText(context,"Login err", Toast.LENGTH_LONG).show();
//                         popup.Show();

                        callback.onError(error);
                    }

                });

        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void ChangePassword(int userId, String oldPassword, String newPassword, VolleyCallback callback)  {
        String url = CHANGE_PASSWORD_URL;

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("id", userId);
            requestBody.put("oldPassword", oldPassword);
            requestBody.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("response", response.toString());
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
                        //parseVolleyError(error); //handle wrong username or password
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

    public void ForgotPassword(String email) throws JSONException {
        String url = FORGOT_PASSWORD_PREFIX_URL + "{" + email + "}";

        JSONObject requestBody = new JSONObject();

        requestBody.put("email", email);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("fotgot password response", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //parseVolleyError(error); //handle wrong username or password
                    }
                });

        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void ValidateEmail(String email) {
    }

}
