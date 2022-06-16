package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.CAN_SAVE_PRODUCT_FAVORITE;
import static com.example.studentmarket.Constants.EndpointConstant.GET_LIST_CATEGORY;
import static com.example.studentmarket.Constants.EndpointConstant.GET_LIST_FAVORITE;
import static com.example.studentmarket.Constants.EndpointConstant.GET_LIST_PRODUCT;
import static com.example.studentmarket.Constants.EndpointConstant.GET_MY_LIST_PRODUCT;
import static com.example.studentmarket.Constants.EndpointConstant.SAVE_PRODUCT_FAVORITE;
import static com.example.studentmarket.Constants.EndpointConstant.SEARCH_PRODUCT;
import static com.example.studentmarket.Constants.EndpointConstant.UNSAVE_PRODUCT_FAVORITE;
import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studentmarket.Helper.RetrofitHelper.RetrofitCallback;
import com.example.studentmarket.Helper.RetrofitHelper.RetrofitClient;
import com.example.studentmarket.Helper.ServiceQueue.ServiceQueue;
import com.example.studentmarket.Helper.Utils;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.FileRequestBody;
import com.example.studentmarket.Models.PostProductResponse;
import com.example.studentmarket.Store.SharedStorage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductService {

    private Context context;

    public ProductService(Context context) {
        this.context = context;
    }

    public void PostProduct(String title, int price, String body, Uri imageUri, Integer[] categories, RetrofitCallback callback) {
//
//        "title": "girl's underwears for boys",
//                "price": 100000,
//                "body": "gamer girl's underwear, worn, unwashed",
//                "pic": {
//            "formdata": {}
//        },
//        "address": "UIT",
//                "categories": []


        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("listingTitle", title);
            jsonBody.put("listingBody", body);
            jsonBody.put("listingPrice", String.valueOf(price));
            jsonBody.put("listingCategories", categories.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        File file = new File(imageUri.getPath());
        String contentType = new Utils().getContentType(imageUri, context);
        FileRequestBody fileRequestBody = null;
        try {
             fileRequestBody = new FileRequestBody(context.getContentResolver().openInputStream(imageUri), contentType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MultipartBody.Part img = MultipartBody.Part.createFormData("img", file.getName(), fileRequestBody);
        MultipartBody.Part requestBody = MultipartBody.Part.createFormData("body", String.valueOf(jsonBody));
        Call<PostProductResponse> call = RetrofitClient.getInstance().getMyApi().postProduct("Bearer " + new SharedStorage(context).getValue(TOKEN_ID_KEY), img, requestBody);
        call.enqueue(new Callback<PostProductResponse>() {
            @Override
            public void onResponse(Call<PostProductResponse> call, retrofit2.Response<PostProductResponse> response) {

                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PostProductResponse> call, Throwable t) {
                Log.d("retrofit fail message", t.getMessage());
                callback.onError(call);
            }
        });
    }

    public void GetListProduct(int PageSize, int PageIndex, ArrayList<Integer> listCategoyIds, VolleyCallback callback) throws JSONException {
        String url = GET_LIST_PRODUCT;

        JSONArray arrjs = new JSONArray(listCategoyIds);

        JSONObject requestBody = new JSONObject();

        requestBody.put("pageSize", PageSize);

        requestBody.put("pageIndex", PageIndex);

        requestBody.put("listingCategoriesIds", arrjs);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

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
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        callback.onError(error);
                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void SearchProduct(String search,VolleyCallback callback) throws JSONException {
        String url = SEARCH_PRODUCT;

        JSONObject requestBody = new JSONObject();
        requestBody.put("pageSize", 11);

        requestBody.put("pageIndex", 1);

        requestBody.put("searchString", search);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("search result", response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("search err", error.toString());
                        callback.onError(error);
                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void GetListCategory(VolleyCallback
                                 callback) throws JSONException {
        String url = GET_LIST_CATEGORY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        callback.onError(error);
                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void GetListFavorite(VolleyCallback
                                 callback) throws JSONException {
        String url = GET_LIST_FAVORITE;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response get favorite err", error.toString());
                        callback.onError(error);
                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void SaveFavorite(String id,VolleyCallback
                                        callback) throws JSONException {
        String url = SAVE_PRODUCT_FAVORITE+"/"+id;
        JSONObject requestBody = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response save favorite err", error.toString());
                        callback.onError(error);
                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };;
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void UnsaveFavorite(String id,VolleyCallback
            callback) throws JSONException {
        String url = UNSAVE_PRODUCT_FAVORITE + "/" + id;
        JSONObject requestBody = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response unsave favorite err", error.toString());
                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };
        ;
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void CanSaveFavorite(String id, VolleyCallback
            callback) throws JSONException {
        String url = CAN_SAVE_PRODUCT_FAVORITE + "/" + id;
        JSONObject requestBody = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response unsave favorite err", error.toString());
                        callback.onError(error);
                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };
        ;
        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetMyProductList(VolleyCallback callback) {
        String url = GET_MY_LIST_PRODUCT;

        JSONObject requestBody = new JSONObject();

        Log.d("rq", requestBody.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

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
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetDetailProduct(String id, VolleyCallback
            callback) throws JSONException {
        String url = GET_DETAIL_PRODUCT + "/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response get favorite err", error.toString());
                        callback.onError(error);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + new SharedStorage(context).getValue(TOKEN_ID_KEY));
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
