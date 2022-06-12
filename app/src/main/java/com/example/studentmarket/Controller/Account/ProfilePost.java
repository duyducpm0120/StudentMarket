package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.studentmarket.Constants.PostProductReasonEnum;
import com.example.studentmarket.Controller.Common.Product;
import com.example.studentmarket.Controller.Common.productAdater;
import com.example.studentmarket.R;

import java.util.ArrayList;


public class ProfilePost extends Fragment {
    private GridView homeListProductGridView;
    private ArrayList<Product> arrayProduct;
    private com.example.studentmarket.Controller.Common.productAdater productAdater;
    private Button postNewProductButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_post, container, false);
        MappingProduct(view);
        productAdater = new productAdater(getContext(), R.layout.product, arrayProduct);
        homeListProductGridView.setAdapter(productAdater);
        postNewProductButton = view.findViewById(R.id.profile_info_post_new_product_button);
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
    private void MappingProduct(View view) {
        homeListProductGridView = (GridView) view.findViewById(R.id.profile_post_list_product);
        arrayProduct = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            arrayProduct.add(new Product(i, "Address", "body",
                    "https://product.hstatic.net/200000260587/product/zve03357_9bb9116b5f3341059fba977d701403f2_grande.png",
                    "timestapm", "DKNY t-shirt - colour block front logo" + i, i, i, "3" + i + ".000 VND", true));
        }

    }
}