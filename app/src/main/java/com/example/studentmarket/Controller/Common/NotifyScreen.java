package com.example.studentmarket.Controller.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.studentmarket.R;

import java.util.ArrayList;

public class NotifyScreen extends AppCompatActivity {
    private ImageButton closeButton;
    private RecyclerView listNotify;
    private ArrayList<NotifyClass> arrayNotify;
    private NotifyAdapter notifyAdapter;
    private String imgUrl="https://haycafe.vn/wp-content/uploads/2021/11/Anh-avatar-dep-chat-lam-hinh-dai-dien.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        closeButton = findViewById(R.id.notify_close_button);
        listNotify = findViewById(R.id.list_notify);

        MappingType();
        notifyAdapter = new NotifyAdapter(arrayNotify,getApplicationContext());
        listNotify.setAdapter(notifyAdapter);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void MappingType(){
        arrayNotify = new ArrayList<>();
        for (int i=0;i<10;i++){
            arrayNotify.add(new NotifyClass(String.valueOf(i),"Minh Minh"," đã đánh giá trang cá nhân của bạn",imgUrl,"123","title","userid"));
        }
    }
}