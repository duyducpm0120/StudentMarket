package com.example.studentmarket.Controller.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.studentmarket.Component.MultiSpinner;
import com.example.studentmarket.Constants.IntentMessage;
import com.example.studentmarket.Constants.PostProductReasonEnum;
import com.example.studentmarket.Constants.RequestCode;
import com.example.studentmarket.Helper.BitmapConverter;
import com.example.studentmarket.Helper.RetrofitHelper.RetrofitCallback;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.ProductModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class PostProduct extends AppCompatActivity {
    private MultiSpinner categoryDropdown;
    private ImageButton postCloseButton;
    private ImageView postUploadImage;
    private EditText postTitleEdittext;
    private EditText postPriceEdittext;
    private EditText postBodyEdittext;
    private Button postPostButton;
    private String[] listName = {};
    private ArrayList<String> categoryNameList = new ArrayList<>();
    private ArrayList<CategoryType> categoryList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayList<Integer> categoryIdListToRequest = new ArrayList<>();
    ProductService productService = new ProductService(this);
    Bitmap productImageBitmap;
    Uri avatarUri;
    String productName;
    String productImage;
    String productBody;
    int productPrice;
    int productId;
    private int[] categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);
        Intent myIntent = getIntent();
        String reason = myIntent.getStringExtra("reason");
        categoryDropdown = findViewById(R.id.category_dropdown);
        postCloseButton = findViewById(R.id.post_close_button);
        postUploadImage = findViewById(R.id.post_upload_image);
        postTitleEdittext = findViewById(R.id.post_title);
        postPriceEdittext = findViewById(R.id.post_price);
        postBodyEdittext = findViewById(R.id.post_body);
        postPostButton = findViewById(R.id.post_post);
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listName);
//        postDropdown.setAdapter(adapter);
        try {
            loadCategoryList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postTitleEdittext.getText().toString();
                int price = Integer.valueOf(String.valueOf(postPriceEdittext.getText()));
                String body = postBodyEdittext.getText().toString();
                long categoryId = categoryDropdown.getSelectedItemId();
                //get Image
                if (categoryId >= 0) {
                    if (!title.isEmpty()) {
                        if (price != 0) {
                            if (!body.isEmpty()) {
                                Integer[] categoryIdList = new Integer[categoryIdListToRequest.size()];
                                categoryIdList = categoryIdListToRequest.toArray(categoryIdList);
                                if (reason.equals(IntentMessage.EDIT_PRODUCT)) {
                                    editProduct(title, price, body, categoryIdList);
                                } else
                                    postProduct(title, price, body, categoryIdList);
                            } else {
                                Toast.makeText(PostProduct.this, "Bạn không được để trống mô tả", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PostProduct.this, "Bạn không được để trống giá", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PostProduct.this, "Bạn không được để trống tiêu đề", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PostProduct.this, "Bạn chưa chọn danh mục", Toast.LENGTH_SHORT).show();
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


        if (reason.equals(IntentMessage.EDIT_PRODUCT)) {
            Log.d("edit product", "edittt");
            setDefaultDataForEdit(myIntent);
            postPostButton.setText("Cập nhật");
        }
    }

    private void editProduct(String title, int price, String body, Integer[] categoryIdList) {
        productService.EditProduct(new ProductModel(productId, "", body, "", "", title, price), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                Log.d("edit success", "edit success");
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("edit prioduct fail", error.getMessage());
            }

        });
    }

    private void postProduct(String title, int price, String body, Integer[] categoryIdList) {
        productService.PostProduct(title, price, body, avatarUri, categoryIdList, new RetrofitCallback() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                Log.d("retrofit success", "aaaa");
                Toast.makeText(PostProduct.this, "Bạn đã đăng bài " + categoryDropdown.getSelectedItem().toString() + " " + postTitleEdittext.getText() + " " + postPriceEdittext.getText() + " " + postBodyEdittext.getText(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Object error) {
                Log.d("retrofit fail", "aaaa");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.SELECT_PICTURE && resultCode == -1) {
            Uri uri = data.getData();
            Picasso.get().load(uri).resize(180, 180).centerInside().into(postUploadImage);
            Uri picUri = data.getData();
            productImageBitmap = new BitmapConverter(this).convertUriToBitMap(uri);
            avatarUri = picUri;
        }
    }


    private void loadCategoryList() throws JSONException {

        productService.GetListCategory(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                Log.d("get list category response", response.toString());
                JSONArray listCate = response.getJSONArray("categories");
                if (listCate != null) {
                    categoryNameList = new ArrayList<String>();
                    for (int i = 0; i < listCate.length(); i++) {
                        JSONObject jsonObject = listCate.getJSONObject(i);
                        categoryNameList.add(jsonObject.getString("listingCategoryName"));
                        categoryList.add(new CategoryType(jsonObject.getString("listingCategoryId"), jsonObject.getString("listingCategoryName"), "drawable://" + R.drawable.type, true));
                        categoryAdapter.add(jsonObject.getString("listingCategoryName"));
                    }
                }
                categoryDropdown.setItems(new ArrayList<String>(categoryNameList), "Chọn danh mục", new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        for (int i = 0; i < selected.length; i++) {
                            Log.d("key", String.valueOf(selected[i]));
                            if (selected[i] == true) {
                                categoryIdListToRequest.add(categoryList.get(i).getId());
                            }
                        }
                    }
                });
                categoryDropdown.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private int findCategoryIndex(int id) {
        int result = 0;
        for (int i = 0; i < categoryNameList.size(); i++) {
            if (categoryList.get(i).getId() == id) result = i;
            break;
        }
        return result;
    }

    private void setDefaultDataForEdit(Intent myIntent) {
        productName = myIntent.getStringExtra("name");
        productPrice = myIntent.getIntExtra("price", 0);
        productImage = myIntent.getStringExtra("image");
        productBody = myIntent.getStringExtra("body");
        productId = myIntent.getIntExtra("id", 0);
        categories = myIntent.getIntArrayExtra("categories");

        postTitleEdittext.setText(productName);
        Picasso.get().load(productImage).into(postUploadImage);
        postPriceEdittext.setText(String.valueOf(productPrice));
        postBodyEdittext.setText(productBody);
//        for (int i = 0; i < categories.length; i++) {
//            categoryDropdown.setSelection(findCategoryIndex(categories[i]));
//        }

    }
}