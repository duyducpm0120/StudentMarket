package com.example.studentmarket.Services;

import static com.example.studentmarket.Constants.EndpointConstant.GET_LIST_CATEGORY;
import static com.example.studentmarket.Constants.EndpointConstant.GET_LIST_PRODUCT;
import static com.example.studentmarket.Constants.EndpointConstant.LOGIN_URL;
import static com.example.studentmarket.Constants.EndpointConstant.POST_PRODUCT;
import static com.example.studentmarket.Constants.EndpointConstant.SEARCH_PRODUCT;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.Helper.ServiceQueue.ServiceQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductService {

    private Context context;

    public ProductService(Context context) {
        this.context = context;
    }

    public void PostProduct(String token) {
        String url = POST_PRODUCT;

        JSONObject requestBody = new JSONObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("login response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        PopupHelper popup = new PopupHelper(context, "Thông báo", "Đăng nhập thất bại, vui lòng thử lại");
                        Toast.makeText(context, "Login err", Toast.LENGTH_LONG).show();

                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetListProduct(int PageSize, int PageIndex, int[] listCategoyIds, String token) throws JSONException {
        String url = GET_LIST_PRODUCT;

        JSONObject requestBody = new JSONObject();

        requestBody.put("pageSize", PageSize);

        requestBody.put("pageIndex", PageIndex);

        requestBody.put("listingCategoriesIds", listCategoyIds);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("login response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        PopupHelper popup = new PopupHelper(context, "Thông báo", "Đăng nhập thất bại, vui lòng thử lại");
                        Toast.makeText(context, "Login err", Toast.LENGTH_LONG).show();

                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void SearchProduct(String search, String token) throws JSONException {
        String url = SEARCH_PRODUCT;

        JSONObject requestBody = new JSONObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("login response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        PopupHelper popup = new PopupHelper(context, "Thông báo", "Đăng nhập thất bại, vui lòng thử lại");
                        Toast.makeText(context, "Login err", Toast.LENGTH_LONG).show();

                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetListCategory(String search, String token) throws JSONException {
        String url = GET_LIST_CATEGORY;

        JSONObject requestBody = new JSONObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("login response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("response err", error.toString());
                        PopupHelper popup = new PopupHelper(context, "Thông báo", "Đăng nhập thất bại, vui lòng thử lại");
                        Toast.makeText(context, "Login err", Toast.LENGTH_LONG).show();

                    }

                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        ;


        // Access the RequestQueue through your singleton class.
        ServiceQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
