package com.example.studentmarket.Helper.RetrofitHelper;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface RetrofitCallback {
    void onSuccess(Object response) throws JSONException;
    void onError(Object error);
}
