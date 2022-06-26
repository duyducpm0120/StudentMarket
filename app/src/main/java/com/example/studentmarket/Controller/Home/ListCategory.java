package com.example.studentmarket.Controller.Home;

import static com.example.studentmarket.Helper.globalValue.setIndex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.studentmarket.Component.categoryInterface;
import com.example.studentmarket.Controller.Common.CategoryType;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.studentmarket.Helper.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ListCategory extends AppCompatActivity {
    private RecyclerView listCategory;
    private ArrayList<CategoryType> arrayCategory;
    private com.example.studentmarket.Controller.Common.typeAdapter typeAdapter;
    private typeAdapter typeAdapterSearch;
    private ProductService productService;
    private ImageButton listCategoryGoBack;
    private EditText listCategorySearch;
    private LinearLayout listCategoryEmtySearch;
    private String defaultImage = "https://cdn-icons-png.flaticon.com/512/95/95365.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        listCategoryGoBack = findViewById(R.id.list_category_goback);
        productService = new ProductService(this);
        listCategorySearch = findViewById(R.id.list_category_edittext_search);
        listCategoryEmtySearch = findViewById(R.id.list_category_empty_search);
        Utils utils = new Utils();
        listCategoryGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()>0){
                    String rawInput = utils.stripAccents(s.toString());
                    ArrayList<CategoryType> listSearch = new ArrayList<>();
                    for (int i=0;i<arrayCategory.size();i++){
                        CategoryType arrayCategoryItem = arrayCategory.get(i);
                        String rawName = utils.stripAccents(arrayCategoryItem.getName());
                        if (rawName.toLowerCase().contains(rawInput.toLowerCase())){
                            listSearch.add(arrayCategoryItem);
                        }
                    }
                    if (listSearch.size()>0){
                        listCategoryEmtySearch.setVisibility(View.INVISIBLE);
                        typeAdapterSearch = new typeAdapter(listSearch, 2, new categoryInterface() {
                            @Override
                            public void action(int index) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("index", String.valueOf(index));
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        });
                        listCategory.setAdapter(typeAdapterSearch);
                    } else {
                        listCategoryEmtySearch.setVisibility(View.VISIBLE);
                    }
                } else {
                    listCategoryEmtySearch.setVisibility(View.INVISIBLE);
                    listCategory.setAdapter(typeAdapter);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getListCategory();
    }
    private void MappingType(JSONObject res) {
        listCategory = (RecyclerView) findViewById(R.id.list_category);
        arrayCategory = new ArrayList<>();
        try {
            JSONArray listCate = res.getJSONArray("categories");
            if (listCate!=null){
                arrayCategory.add(new CategoryType("0", "All",defaultImage,false));
                for (int i = 0; i < listCate.length(); i++) {
                    JSONObject jsonObject = listCate.getJSONObject(i);
                    arrayCategory.add(new CategoryType(jsonObject.getString("listingCategoryId"),jsonObject.getString("listingCategoryName"),jsonObject.getString("listingCategoryIcon"), false));
                }
                typeAdapter = new typeAdapter(arrayCategory, 2, new categoryInterface() {
                    @Override
                    public void action(int index) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("index", String.valueOf(index));
                            setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                });
                listCategory.setAdapter(typeAdapter);
                setIndex(-1);
            }
        } catch (JSONException err){
            Log.d("conver list category err",err.toString());
        }
    }
    private void getListCategory(){
        try {
            productService.GetListCategory(new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    MappingType(response);
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
}