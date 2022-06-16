package com.example.studentmarket.Helper.RetrofitHelper;

import com.example.studentmarket.Constants.EndpointConstant;
import com.example.studentmarket.Models.PostProductResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    String BASE_URL = EndpointConstant.ENDPOINT_PREFIX;
//    @GET("marvel")
//    Call<List<results>> getsuperHeroes();
    @POST("listings/createListing")
    @Multipart
    Call<PostProductResponse> postProduct(@Header("Authorization") String authorization,
                                          @Part MultipartBody.Part img,
                                          @Part MultipartBody.Part body);
}
