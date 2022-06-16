package com.example.studentmarket.Controller.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
//import android.widget.GridView;
import com.example.studentmarket.Component.MyGridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.studentmarket.Component.categoryInterface;
import com.example.studentmarket.Controller.Common.NotifyClass;
import com.example.studentmarket.Controller.Common.NotifyScreen;
import com.example.studentmarket.Controller.Common.Product;
import com.example.studentmarket.Controller.Common.productAdater;
import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;
import static com.example.studentmarket.Helper.globalValue.*;

import com.example.studentmarket.Services.ProductService;
import com.example.studentmarket.Services.ProductService.*;
import com.example.studentmarket.Store.SharedStorage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MyGridView homeListProduct;
    private ArrayList<Product> arrayProduct;
    private com.example.studentmarket.Controller.Common.productAdater productAdater;

    private RecyclerView homeListType;
    private ArrayList<type> arrayType;
    private com.example.studentmarket.Controller.Common.typeAdapter homeTypeAdapter;

    private TextView homeTextViewSeeMore;
    private EditText homeEdittextSearch;
    private ImageButton goToNotify;
    private LinearLayout emptySearch;
    private NestedScrollView homeScroll;
    private int indexData=1;
    private boolean isOver = false;
    private ArrayList<Integer> arr = new ArrayList<>();

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ProductService productService;
    private SharedStorage storage;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    private String searchText;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        storage = new SharedStorage(getContext());
        homeTextViewSeeMore = (TextView) view.findViewById(R.id.home_textview_see_more);
        homeEdittextSearch = view.findViewById(R.id.home_edittext_search);
        goToNotify = view.findViewById(R.id.home_button_notice);
        productService = new ProductService(getContext());
        fragmentManager = getParentFragmentManager();
        homeScroll = view.findViewById(R.id.home_scroll);
        fragmentTransaction = fragmentManager.beginTransaction();
        emptySearch = view.findViewById(R.id.home_empty_search);
            LoadListProduct(view);
            getListCategory(view);
        homeTextViewSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), ListCategory.class);
                setIndex(-1);
                startActivityForResult(myIntent,999);
//                getContext().startActivity(myIntent);
            }
        });

        homeEdittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = s.toString();
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                        refresh();
                }
            }
        });

        goToNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), NotifyScreen.class);
                getContext().startActivity(myIntent);
            }
        });
        homeScroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (homeScroll.getChildAt(0).getBottom()
                                <= (homeScroll.getHeight() + homeScroll.getScrollY())) {
                            //scroll view is at bottom
                            if (homeEdittextSearch.getText().toString().isEmpty()){
                                try {
                                        if (!isOver){
                                        if (!isFirstAcess()){
                                            getListProductIndex();
                                        } else {
                                            setFirstAcess(false);
                                        }
                                    } else {
                                    }
                                }
                                catch (NullPointerException | InterruptedException e){
//                                Log.d("home toast err",e.toString());
                                }
                            }
                        } else {
                            //scroll view is not at bottom
                        }
                    }
                });


        return view;
    }

    private void LoadListProduct(View view) {
        homeListProduct = view.findViewById(R.id.home_list_products);
        arrayProduct = new ArrayList<>();
        try {
            productService.GetListProduct(11, 1, arr, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray listingPage = response.getJSONArray("listingPage");
                        if (listingPage.length() != 0){
                            for (int i=0;i<listingPage.length();i++){
                                JSONObject jsonObject = listingPage.getJSONObject(i);
                                arrayProduct.add(new Product(Integer.parseInt(jsonObject.getString("listingId")), jsonObject.getString("listingAddress"), jsonObject.getString("listingBody"),
                                jsonObject.getString("listingImage"),
                                jsonObject.getString("listingTimestamp"), jsonObject.getString("listingTitle"), i, i, jsonObject.getString("listingPrice"), false));
                            }
                            productAdater = new productAdater(getContext(), R.layout.product, arrayProduct);
                            homeListProduct.setAdapter(productAdater);
                            setListProduct(arrayProduct);
                            emptySearch.setVisibility(View.INVISIBLE);
                            indexData++;
                        }
                        else {
                            isOver = true;
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

    private void getListProductIndex() throws InterruptedException {
        Thread.sleep(1000);
        try {
            productService.GetListProduct(11, indexData, arr, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray listingPage = response.getJSONArray("listingPage");
                        if (listingPage.length() != 0){
                            for (int i=0;i<listingPage.length();i++){
                                JSONObject jsonObject = listingPage.getJSONObject(i);
                                arrayProduct.add(new Product(Integer.parseInt(jsonObject.getString("listingId")), jsonObject.getString("listingAddress"), jsonObject.getString("listingBody"),
                                        jsonObject.getString("listingImage"),
                                        jsonObject.getString("listingTimestamp"), jsonObject.getString("listingTitle"), i, i, jsonObject.getString("listingPrice"), false));
                            }
                            if (productAdater!=null){
                                productAdater.setItem(arrayProduct);
                                productAdater.notifyDataSetChanged();
                            } else {
                                productAdater = new productAdater(getContext(), R.layout.product, arrayProduct);
                            }
                            indexData++;
                        }
                        else {
                            isOver = true;
                        }

                    }
                    catch (JSONException jsonException){
                        Log.d("json",jsonException.toString());
                    }

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void MappingProduct(View view) {
        for (int i = 0; i < 10; i++) {
            arrayProduct.add(new Product(i, "Address", "body",
                    "https://product.hstatic.net/200000260587/product/zve03357_9bb9116b5f3341059fba977d701403f2_grande.png",
                    "timestapm", "DKNY t-shirt - colour block front logo" + i, i, i, "3" + i + ".000 VND", true));
        }
        setListProduct(arrayProduct);
    }

    private void MappingType(View view,JSONObject res) {
        try {
            JSONArray listCate = res.getJSONArray("categories");
            if (listCate!=null){
                homeListType = (RecyclerView) view.findViewById(R.id.home_list_type);
                arrayType = new ArrayList<>();
                for (int i = 0; i < listCate.length(); i++) {
                    JSONObject jsonObject = listCate.getJSONObject(i);
                    arrayType.add(new type(jsonObject.getString("listingCategoryId"),jsonObject.getString("listingCategoryName"), jsonObject.getString("listingCategoryIcon"), false));
                }
                createTypeAdapter();
                homeListType.setAdapter(homeTypeAdapter);
                setIndex(-1);
            }
        } catch (JSONException err){
            Log.d("conver list category err",err.toString());
        }
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                try {
                    productService.SearchProduct(searchText, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {
                            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(homeEdittextSearch.getWindowToken(), 0);
                            homeScroll.scrollTo(0,0);
                            JSONArray listSearch = response.getJSONArray("listingPage");
                            if (listSearch!=null && listSearch.length()!=0){
                                ArrayList<Product> arrSearch = new ArrayList<>();
                                for (int i=0;i<listSearch.length();i++){
                                    JSONObject jsonObject = listSearch.getJSONObject(i);
                                    arrSearch.add(new Product(Integer.parseInt(jsonObject.getString("listingId")), jsonObject.getString("listingAddress"), jsonObject.getString("listingBody"),
                                            jsonObject.getString("listingImage"),
                                            jsonObject.getString("listingTimestamp"), jsonObject.getString("listingTitle"), i, i, jsonObject.getString("listingPrice"), false));
                                    if (productAdater!=null){
                                        productAdater.setItem(arrSearch);
                                        productAdater.notifyDataSetChanged();
                                    }
                                    else  {
                                        productAdater = new productAdater(getContext(), R.layout.product, arrSearch);
                                        homeListProduct.setAdapter(productAdater);
                                    }
                                    emptySearch.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                emptySearch.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            emptySearch.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }
    };

    private void getListCategory(View view){
        try {
        productService.GetListCategory(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                MappingType(view,response);
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("load list category err",error.toString());
            }
        });
        } catch (JSONException exception){
            Log.d("err",exception.toString());
        }
    }

    private void refresh(){
        if (getListProduct()==null){
            return;
        }
        if (productAdater!=null){
            productAdater.setItem(getListProduct());
            productAdater.notifyDataSetChanged();

        } else {
            productAdater = new productAdater(getContext(), R.layout.product, getListProduct());
            homeListProduct.setAdapter(productAdater);
        }
        emptySearch.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (999) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newIndex = data.getStringExtra("index");
                    int Id = Integer.parseInt(newIndex);
                    for (int i=0;i<arrayType.size();i++){
                        type elementTypeOfIndex = arrayType.get(i);
                        if (elementTypeOfIndex.getId()==Id){
                            arrayType.set(i,new type(newIndex,elementTypeOfIndex.getName(),elementTypeOfIndex.getImage(),true));
                            setIndex(i);
                            continue;
                        }
                        arrayType.set(i,new type(String.valueOf(elementTypeOfIndex.getId()),elementTypeOfIndex.getName(),elementTypeOfIndex.getImage(),false));
                    }
                    homeTypeAdapter.setItem(arrayType);
                    homeListType.setAdapter(homeTypeAdapter);
                    arr.clear();
                    arr.add(Id);
                    indexData=1;
                    isOver = false;
                    ArrayList<Product> arrayProductCate = new ArrayList<>();
                    homeGetListProduct(arrayProductCate);
                }
                break;
            }
        }
    }

    private void createTypeAdapter(){
        homeTypeAdapter = new typeAdapter(arrayType, 1, new categoryInterface() {
            @Override
            public void action(int index) {
                arr.clear();
                arrayProduct.clear();
                indexData=1;
                isOver = false;
                if (index == -1){
                    productAdater.setItem(getListProduct());
                    productAdater.notifyDataSetChanged();
                    return;
                }
                ArrayList<Product> arrayProductCate = new ArrayList<>();
                arr.add(index);
                homeGetListProduct(arrayProductCate);

            }
        });
    }
    private void homeGetListProduct(ArrayList<Product> productArrayList){
        try {
            productService.GetListProduct(11, indexData, arr, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    HandleOnSuccessGetListProduct(response,productArrayList);
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (JSONException je){

        }
    }
    private void HandleOnSuccessGetListProduct(JSONObject response,ArrayList<Product> productArrayList){
        try {
            JSONArray listingPage = response.getJSONArray("listingPage");
            if (listingPage.length() != 0){
                for (int i=0;i<listingPage.length();i++){
                    JSONObject jsonObject = listingPage.getJSONObject(i);
                    productArrayList.add(new Product(Integer.parseInt(jsonObject.getString("listingId")), jsonObject.getString("listingAddress"), jsonObject.getString("listingBody"),
                            jsonObject.getString("listingImage"),
                            jsonObject.getString("listingTimestamp"), jsonObject.getString("listingTitle"), i, i, jsonObject.getString("listingPrice"), false));
                }
                productAdater.setItem(productArrayList);
                homeListProduct.setAdapter(productAdater);
            }
            else {
                Toast.makeText(getContext(), "Hiện không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }

        }
        catch (JSONException jsonException){
            Log.d("json",jsonException.toString());
        }
    }
}