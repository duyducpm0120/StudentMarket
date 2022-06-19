package com.example.studentmarket.Helper.RetrofitHelper;

import com.example.studentmarket.Models.ProductModel;
import com.example.studentmarket.Models.request.ProductBodyRequest;


import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

//    @GET("marvel")
//    Call<List<results>> getsuperHeroes();
    @POST("listings/createListing/")
    @Multipart
    Call<ProductModel> postProduct(@Header("Authorization") String authorization,
                                   @Part MultipartBody.Part img,
                                   @Part("body") ProductBodyRequest body);
    @GET("/listings/myListings")
    Call<List<ProductModel>> getMyProduct(@Header("Authorization") String authorization);
}
