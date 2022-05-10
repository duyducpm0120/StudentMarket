package com.example.studentmarket.Controller.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.R;

import java.util.ArrayList;


public class ListCategory extends AppCompatActivity {
    private RecyclerView listCategory;
    private ArrayList<type> arrayCategory;
    private com.example.studentmarket.Controller.Common.typeAdapter typeAdapter;
    private String[] listName = {"All Woments","New Collection","Active / Sports","Luxury","Swimwear","Casual"};

    private ImageButton listCategoryGoBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        listCategoryGoBack = findViewById(R.id.list_category_goback);

        listCategoryGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MappingType();
        typeAdapter = new typeAdapter(arrayCategory,2);
        listCategory.setAdapter(typeAdapter);
    }
    private void MappingType(){
        listCategory = (RecyclerView) findViewById(R.id.list_category);
        arrayCategory = new ArrayList<>();
        for (int i=0;i<listName.length;i++){
            arrayCategory.add(new type(listName[i],R.drawable.type,false));
        }
    }
}