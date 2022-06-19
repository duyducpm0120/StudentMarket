package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.example.studentmarket.Constants.PostProductReasonEnum;
import com.example.studentmarket.Controller.Common.ProductAdapter;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.ProductModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProfilePost extends Fragment {
    private GridView homeListProductGridView;
    private ArrayList<ProductModel> arrayProduct;
    private ProductAdapter productAdapter;
    private Button postNewProductButton;
    private ArrayList<ProductModel> listingList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        listingList = new ArrayList<>();
        getMyProduct();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_post, container, false);
        homeListProductGridView = (GridView) view.findViewById(R.id.profile_post_list_product);
        productAdapter = new ProductAdapter(getContext(), R.layout.product, listingList);
        homeListProductGridView.setAdapter(productAdapter);
        postNewProductButton = view.findViewById(R.id.confirm_button);
        postNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), PostProduct.class);
                myIntent.putExtra("reason", PostProductReasonEnum.POST_NEW_PRODUCT);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }

    private void getMyProduct() {
        ProductService productService = new ProductService(getContext());
        productService.GetMyProductList(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                JSONArray listings = response.getJSONArray("listings");
                Gson gson = new Gson();
                for (int i = 0; i < listings.length(); i++) {
                    listingList.add(gson.fromJson(String.valueOf(listings.getJSONObject(i)), ProductModel.class));
                }
                for (int i = 0; i < listings.length(); i++) {
                    listingList.add(gson.fromJson(String.valueOf(listings.getJSONObject(i)), ProductModel.class));
                }
                for (int i = 0; i < listings.length(); i++) {
                    listingList.add(gson.fromJson(String.valueOf(listings.getJSONObject(i)), ProductModel.class));
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("get producList err", error.getMessage());
            }
        });
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.profile_fragmentContainerView, this);
        tr.commit();
    }


}