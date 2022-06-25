package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.GET_NOTIFICATION;
import static com.example.studentmarket.Constants.EndpointConstant.MARK_NOTIFICATION_AS_READ;
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
import com.example.studentmarket.Models.UserProfileModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class NotifyService {
    private Context context;

    public NotifyService(Context context) {
        this.context = context;
    }

    public void getListNotification(VolleyCallback callback) throws JSONException {
        String url = GET_NOTIFICATION;

        JSONObject requestBody = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestBody, new Response.Listener<JSONObject>() {

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
    public void markNotificationAsRead(String id,VolleyCallback callback) throws JSONException {
        String url = MARK_NOTIFICATION_AS_READ+"/"+id;

        JSONObject requestBody = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
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
