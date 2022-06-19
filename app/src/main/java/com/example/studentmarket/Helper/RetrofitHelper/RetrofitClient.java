package com.example.studentmarket.Helper.RetrofitHelper;


import com.example.studentmarket.Constants.EndpointConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private RetrofitInterface myApi;
    String BASE_URL = EndpointConstant.ENDPOINT_PREFIX;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(RetrofitInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public RetrofitInterface getMyApi() {
        return myApi;
    }
}
