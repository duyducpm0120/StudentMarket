package com.example.studentmarket.Controller.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmarket.Component.MultiSpinner;
import com.example.studentmarket.MainActivity;
import com.example.studentmarket.R;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;


public class Post extends AppCompatActivity {
    private MultiSpinner postDropdown;
    private ImageButton postCloseButton;
    private ImageView postUploadImage;
    private EditText postTitleEdittext;
    private EditText postPriceEdittext;
    private EditText postBodyEdittext;
    private Button postPostButton;
    private String[] listName = {"All Woments", "New Collection", "Active / Sports", "Luxury", "Swimwear", "Casual"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postDropdown = findViewById(R.id.post_dropdown);
        postCloseButton = findViewById(R.id.post_close_button);
        postUploadImage = findViewById(R.id.post_upload_image);
        postTitleEdittext = findViewById(R.id.post_title);
        postPriceEdittext = findViewById(R.id.post_price);
        postBodyEdittext = findViewById(R.id.post_body);
        postPostButton = findViewById(R.id.post_post);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listName);
//        postDropdown.setAdapter(adapter);
        postDropdown.setItems(new ArrayList<String>(Arrays.asList(listName)), "Chọn danh mục", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for (int i = 0; i < selected.length; i++) {
                    Log.d("key", String.valueOf(selected[i]));
                }
            }
        });

        postPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postTitleEdittext.getText().toString();
                String price = postPriceEdittext.getText().toString();
                String body = postBodyEdittext.getText().toString();
                String category = postDropdown.getSelectedItem().toString();
                long categoryId = postDropdown.getSelectedItemId();
                //get Image
                if (categoryId != 0) {
                    if (!title.isEmpty()) {
                        if (!price.isEmpty()) {
                            if (!body.isEmpty()) {
                                Toast.makeText(Post.this, "Bạn đã đăng bài " + postDropdown.getSelectedItem().toString() + " " + postTitleEdittext.getText() + " " + postPriceEdittext.getText() + " " + postBodyEdittext.getText(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Post.this, "Bạn không được để trống mô tả", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Post.this, "Bạn không được để trống giá", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Post.this, "Bạn không được để trống tiêu đề", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Post.this, "Bạn chưa chọn danh mục", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), 1);
            }
        });

        postCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            Uri uri = data.getData();
            postUploadImage.setImageURI(uri);
        }
    }
}