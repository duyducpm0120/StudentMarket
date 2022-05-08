package com.example.studentmarket.Controller.Common;

import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmarket.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductDetail extends AppCompatActivity {
    private TextView detailProductName;
    private TextView detailProductPrice;
    private TextView detailProductDescriptions;
    private ImageButton detailProductGoBack;
    private ImageButton detailProductHeart;
    private ImageButton detailProductChat;
    private ImageButton detailProductEdit;
    private ImageButton detailProductRemove;
    private ImageView detailProductAvatar;
    private TextView detailProductAvatarName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent myIntent = getIntent();
        String productName = myIntent.getStringExtra("name");
        String productPrice = myIntent.getStringExtra("price");
        detailProductName = findViewById(R.id.product_detail_textview_name_product);
        detailProductPrice = findViewById(R.id.product_detail_price);
        detailProductDescriptions = findViewById(R.id.product_detail_description);
        detailProductGoBack = findViewById(R.id.product_detail_goback);
        detailProductHeart = findViewById(R.id.product_detail_heart);
        detailProductChat = findViewById(R.id.product_detail_chat);
        detailProductEdit = findViewById(R.id.product_detail_edit);
        detailProductRemove = findViewById(R.id.product_detail_remove);
        detailProductAvatar = findViewById(R.id.product_detail_avatar);
        detailProductAvatarName = findViewById(R.id.product_detail_avatar_name);


        detailProductName.setText(productName);
        detailProductPrice.setText(productPrice);

        detailProductGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}