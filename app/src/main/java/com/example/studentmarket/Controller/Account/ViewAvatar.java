package com.example.studentmarket.Controller.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmarket.Constants.IntentMessage;
import com.example.studentmarket.Constants.RequestCode;
import com.example.studentmarket.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ViewAvatar extends AppCompatActivity {
    private ImageButton goBackButton;
    private ImageView avatar;
    public ViewAvatar(){

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        goBackButton = findViewById(R.id.view_avatar_goback_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        avatar = findViewById(R.id.view_avatart_avatar_image_view);
//        Intent intent = getIntent();
//        String avatar_url = intent.getStringExtra(IntentMessage.VIEW_AVATAR);
//
//        Picasso.get().load(avatar_url).transform(new CropCircleTransformation()).resize(110, 110).centerInside().into(avatar);

        setContentView(R.layout.activity_view_avatar);
    }





}
