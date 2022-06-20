package com.example.studentmarket.Controller.Favorite;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;
import static com.example.studentmarket.Helper.globalValue.setListProduct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.studentmarket.Controller.Common.Product;
import com.example.studentmarket.Controller.Common.productAdater;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.example.studentmarket.Store.SharedStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorite extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView homeListProduct;
    private ArrayList<Product> arrayProduct;
    private com.example.studentmarket.Controller.Common.productAdater productAdater;
    private LinearLayout favoriteRequireLogin;
    private EditText favoriteEdittextSearch;
    private ProductService productService;


    public Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorite newInstance(String param1, String param2) {
        Favorite fragment = new Favorite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favoriteRequireLogin = view.findViewById(R.id.favorite_require_login);
        favoriteEdittextSearch = view.findViewById(R.id.favorite_edittext_search);

        SharedStorage storage = new SharedStorage(getContext());
        if (storage.getValue(TOKEN_ID_KEY).isEmpty()){
            favoriteRequireLogin.setVisibility(View.VISIBLE);
            favoriteEdittextSearch.setVisibility(View.INVISIBLE);
        } else {
            favoriteRequireLogin.setVisibility(View.INVISIBLE);
            favoriteEdittextSearch.setVisibility(View.VISIBLE);
        }
        productService = new ProductService(getContext());

        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()){
            LoadListProduct(view);
        } else {
            homeListProduct = (GridView) view.findViewById(R.id.favorite_list_product);
            arrayProduct = new ArrayList<>();
            productAdater = new productAdater(getContext(), R.layout.product, arrayProduct);
            homeListProduct.setAdapter(productAdater);
        }
        return view;
    }

    private void MappingProduct(View view) {
        arrayProduct = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayProduct.add(new Product(i, "Address", "body",
                    "https://product.hstatic.net/200000260587/product/zve03357_9bb9116b5f3341059fba977d701403f2_grande.png",
                    "timestapm", "DKNY t-shirt - colour block front logo" + i, i, i, "3" + i + ".000 VND", true));
        }

    }
    private void LoadListProduct(View view) {
        homeListProduct = (GridView) view.findViewById(R.id.favorite_list_product);
        arrayProduct = new ArrayList<>();
        try {
            productService.GetListFavorite(new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray listingPage = response.getJSONArray("listings");
                        if (listingPage.length() != 0){
                            for (int i=0;i<listingPage.length();i++){
                                JSONObject jsonObject = listingPage.getJSONObject(i);
                                arrayProduct.add(new Product(Integer.parseInt(jsonObject.getString("listingId")), jsonObject.getString("listingAddress"), jsonObject.getString("listingBody"),
                                        jsonObject.getString("listingImage"),
                                        jsonObject.getString("listingTimestamp"), jsonObject.getString("listingTitle"), i, i, jsonObject.getString("listingPrice"), true));
                            }
                            productAdater = new productAdater(getContext(), R.layout.product, arrayProduct);
                            homeListProduct.setAdapter(productAdater);
                            setListProduct(arrayProduct);
                        }
                    }
                    catch (JSONException jsonException){
                        Log.d("json",jsonException.toString());
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    MappingProduct(view);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            MappingProduct(view);
        }
    }
}