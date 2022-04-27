package com.example.studentmarket.Services;

import static com.example.studentmarket.Services.EndpointConstant.LOGIN_URL;
import static com.example.studentmarket.Services.EndpointConstant.RATING_USER_PREFIX_URL;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UserActionService {
    public static void rateUser(String userId, Context context) {
        String url = RATING_USER_PREFIX_URL + "{" + userId + "}";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

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

    public static void parseVolleyError(@NonNull VolleyError error) {
        try {
//            String responseBody = new String(error.networkResponse.data, "utf-8");
//            JSONObject data = new JSONObject(responseBody);
//            JSONArray errors = data.getJSONArray("errors");
//            JSONObject jsonMessage = errors.getJSONObject(0);
//            String message = jsonMessage.getString("message");
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String message = data.optString("message");
            Log.d("err", message);
        } catch (JSONException e) {
            Log.d("err", e.toString());
        } catch (UnsupportedEncodingException errorr) {
            Log.d("err", errorr.toString());
        }
    }
}
