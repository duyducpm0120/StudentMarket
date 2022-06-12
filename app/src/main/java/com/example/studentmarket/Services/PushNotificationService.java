package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.PUSHNOTI_REGISTER_DEVICE;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PushNotificationService {
    private Context context;

    public PushNotificationService(Context context) {
        this.context = context;
    }

    public void registerDevice(String token) {
        String url = PUSHNOTI_REGISTER_DEVICE;
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("fcmToken", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("response", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //parseVolleyError(error); //handle wrong username or password
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
