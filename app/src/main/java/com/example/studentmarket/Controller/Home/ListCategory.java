package com.example.studentmarket.Controller.Home;

import static com.example.studentmarket.Helper.globalValue.setIndex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListCategory extends AppCompatActivity {
    private RecyclerView listCategory;
    private ArrayList<type> arrayCategory;
    private com.example.studentmarket.Controller.Common.typeAdapter typeAdapter;
    private String[] listName = {"All Woments","New Collection","Active / Sports","Luxury","Swimwear","Casual"};
    private ProductService productService;
    private ImageButton listCategoryGoBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        listCategoryGoBack = findViewById(R.id.list_category_goback);
        productService = new ProductService(this);
        listCategoryGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListCategory();
//        typeAdapter = new typeAdapter(arrayCategory,2);
//        listCategory.setAdapter(typeAdapter);
    }
    private void MappingType(JSONObject res) {
        listCategory = (RecyclerView) findViewById(R.id.list_category);
        arrayCategory = new ArrayList<>();
        try {
            JSONArray listCate = res.getJSONArray("categories");
            if (listCate!=null){
                for (int i = 0; i < listCate.length(); i++) {
                    JSONObject jsonObject = listCate.getJSONObject(i);
                    arrayCategory.add(new type(jsonObject.getString("listingCategoryId"),jsonObject.getString("listingCategoryName"), R.drawable.type, false));
                }
                typeAdapter = new typeAdapter(arrayCategory, 2);
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