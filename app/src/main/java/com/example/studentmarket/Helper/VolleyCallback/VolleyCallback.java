package com.example.studentmarket.Helper.VolleyCallback;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject response) throws JSONException;
    void onError(VolleyError error);
}
