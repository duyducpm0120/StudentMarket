package com.example.studentmarket.Controller.Common;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmarket.Controller.Account.Post;
import com.example.studentmarket.Controller.Message.ListMessages;
import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.R;
import com.squareup.picasso.Picasso;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.studentmarket.Helper.globalValue.*;

import java.util.ArrayList;


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
    private ImageView detailProductImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent myIntent = getIntent();
        String productName = myIntent.getStringExtra("name");
        String productPrice = myIntent.getStringExtra("price");
        String productImage = myIntent.getStringExtra("image");
        String productBody = myIntent.getStringExtra("body");
        final boolean[] isHeart = {myIntent.getBooleanExtra("isHeart", false)};
        int id=myIntent.getIntExtra("id",0);
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
        detailProductImage = findViewById(R.id.product_detail_image);


        detailProductName.setText(productName);
        detailProductPrice.setText(productPrice);

        detailProductGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Picasso.get().load(productImage).into(detailProductImage);

        detailProductRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupHelper popupHelper = new PopupHelper(ProductDetail.this,id,"Thông báo","Bạn chắc chắn muốn xóa bài đăng này",true,"Huỷ","Xoá");
                popupHelper.Show();
            }
        });

        detailProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Post.class);
                myIntent.putExtra("name","test");
                ProductDetail.this.startActivity(myIntent);
            }
        });

        detailProductChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProductDetail.this, ListMessages.class);
                myIntent.putExtra("name","avatar");
                ProductDetail.this.startActivity(myIntent);
            }
        });
//        detailProductDescriptions.setText(productBody);
        String text = detailProductDescriptions.getText().toString();
        detailProductDescriptions.setText(text+text+text+text+text+text+text);

        if (isHeart[0]){
            detailProductHeart.setColorFilter(getColor(R.color.secondary));
        }
        else {
            detailProductHeart.setColorFilter(getColor(R.color.gray));
        }

        ArrayList<Product> listProduct = new ArrayList<>();
        listProduct = getListProduct();
        ArrayList<Product> finalListProduct = listProduct;
        detailProductHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHeart[0]){
                    detailProductHeart.setColorFilter(getColor(R.color.gray));

                } else {
                    detailProductHeart.setColorFilter(getColor(R.color.secondary));
                }
                isHeart[0] = !isHeart[0];
                for (int i=0;i< finalListProduct.size();i++){
                    if (finalListProduct.get(i).getId()==id){
                        finalListProduct.get(i).setHeart(isHeart[0]);
                        break;
                    }
                }
                setListProduct(finalListProduct);
            }
        });
    }
}