package com.example.studentmarket.Helper.ServiceHeaderHelper;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.example.studentmarket.Constants.StorageKeyConstant;
import com.example.studentmarket.Store.SharedStorage;

import java.util.HashMap;
import java.util.Map;

public class ServiceHeaderHelper {
    private Context context;

    public ServiceHeaderHelper(Context context) {
        this.context = context;
    }

    public Map<String, String> getHeadersWithToken() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + new SharedStorage(context).getValue(TOKEN_ID_KEY));
        return headers;
    }
}
