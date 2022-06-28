package com.example.studentmarket.Controller.Common;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;
import static com.example.studentmarket.Helper.globalValue.getListProduct;
import static com.example.studentmarket.Helper.globalValue.getUserId;
import static com.example.studentmarket.Helper.globalValue.setListProduct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.studentmarket.Constants.IntentMessage;
import com.example.studentmarket.Controller.Account.ViewOtherProfile;
import com.example.studentmarket.Controller.Message.ListMessages;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.Helper.Popup.PopupHelperAction;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.example.studentmarket.Services.ProfileService;
import com.example.studentmarket.Store.SharedStorage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
    private ProductService productService;
    private String posterId;
    private String posterName;
    private String posterAvatar;
    private int[] categories;
    private String productName;
    private String productImage;
    private String productBody;
    private String productPrice;
    private int productId;

    UserProfileModel poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        SharedStorage storage = new SharedStorage(this);

        setContentView(R.layout.activity_product_detail);


        Intent myIntent = getIntent();
        productName = myIntent.getStringExtra("name");
        productPrice = myIntent.getStringExtra("price");
        productImage = myIntent.getStringExtra("image");
        productBody = myIntent.getStringExtra("body");
        productId = myIntent.getIntExtra("id", 0);
        categories = myIntent.getIntArrayExtra("categories");
        final boolean[] isHeart = {myIntent.getBooleanExtra("isHeart", false)};
        int id = myIntent.getIntExtra("id", 0);
//        getPosterProfile();

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


        productService = new ProductService(this);
        ProductService productService = new ProductService(this);


        detailProductName.setText(productName);

        String formatPrice = String.format("%,d", Integer.parseInt(productPrice)) + " đ";
        detailProductPrice.setText(formatPrice);
        detailProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile();
            }
        });
        detailProductAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile();
            }
        });
        Picasso.get().load(productImage).fit().into(detailProductImage);

        ArrayList<Product> listProduct = new ArrayList<>();
        listProduct = getListProduct();
        ArrayList<Product> finalListProduct = listProduct;
        try {
            getDetailPoster(String.valueOf(id));
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()) {
            handleHeart(isHeart, id, productService, finalListProduct);
        } else {
            handleShowButton();
        }

        detailProductGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        detailProductRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupHelper popupHelper = new PopupHelper(ProductDetail.this, id, "Thông báo", "Bạn chắc chắn muốn xóa bài đăng này", true, "Huỷ", "Xoá", new PopupHelperAction() {
                    @Override
                    public void onAction() {
                        ProductService productService1 = new ProductService(getApplicationContext());
                        try{
                            productService1.DeleteProduct(productId, new VolleyCallback() {
                                @Override
                                public void onSuccess(JSONObject response) throws JSONException {
                                    Toast.makeText(getApplicationContext(), "Xoá bài đăng thành công", Toast.LENGTH_SHORT);
                                    finish();
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Có lỗi. Xoá bài đăng thất bại", Toast.LENGTH_SHORT);
                                    finish();
                                }
                            });
                        } catch (Error err){
                            Toast.makeText(getApplicationContext(), "Có lỗi. Xoá bài đăng thất bại", Toast.LENGTH_SHORT);
                            finish();
                        }

                    }

                    @Override
                    public void onClose() {
                        return;
                    }
                });
                popupHelper.Show();


//                PopupHelper popupHelper = new PopupHelper(ProductDetail.this, id, "Thông báo", "Bạn chắc chắn muốn xóa bài đăng này?", true, "Huỷ", "Xoá");
//                popupHelper.Show();
            }
        });

        detailProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), PostProduct.class);
                myIntent.putExtra("reason", IntentMessage.EDIT_PRODUCT);
                myIntent.putExtra("name", productName);
                myIntent.putExtra("price", productPrice);
                myIntent.putExtra("image", productImage);
                myIntent.putExtra("body", productBody);
                myIntent.putExtra("id", productId);
                myIntent.putExtra("categories", categories);

                ProductDetail.this.startActivity(myIntent);
            }
        });

        detailProductChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListMessages.class);
                myIntent.putExtra("posterId", posterId);
                myIntent.putExtra("posterName", posterName);
                myIntent.putExtra("posterAvatar", posterAvatar);
                ProductDetail.this.startActivity(myIntent);
            }
        });
        detailProductDescriptions.setText(productBody);

        heartCheckShow(isHeart[0]);


    }

    private void heartCheckShow(boolean canShow) {
        if (canShow) {
            detailProductHeart.setColorFilter(getColor(R.color.secondary));
        } else {
            detailProductHeart.setColorFilter(getColor(R.color.gray));
        }
    }

    private void handleHeart(boolean[] isHeart, int id, ProductService productService, ArrayList<Product> finalListProduct) {
        detailProductHeart.setVisibility(ImageView.VISIBLE);
        try {
            productService.CanSaveFavorite(String.valueOf(id), new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    //convert response to object
                    Boolean value = response.getBoolean("value");
                    heartCheckShow(!value);
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("cansave", error.toString());

                }
            });

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        detailProductHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHeart[0]) {
                    try {
                        productService.UnsaveFavorite(String.valueOf(id), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                            }

                            @Override
                            public void onError(VolleyError error) {
                                //unsave succesfull
                                detailProductHeart.setColorFilter(getColor(R.color.gray));
                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } else {
                    try {
                        productService.SaveFavorite(String.valueOf(id), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                //save successfull
                                detailProductHeart.setColorFilter(getColor(R.color.secondary));
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                isHeart[0] = !isHeart[0];
                for (int i = 0; i < finalListProduct.size(); i++) {
                    if (finalListProduct.get(i).getId() == id) {
                        finalListProduct.get(i).setHeart(isHeart[0]);
                        break;
                    }
                }
                setListProduct(finalListProduct);
            }
        });
    }

    public void getDetailPoster(String id) throws JSONException {
        productService.GetDetailPoster(id, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                //convert response to object
                Log.d("response", response.toString());
                posterName = response.getString("userFullName");
                posterAvatar = response.getString("userPic");
                Picasso.get().load(posterAvatar).fit().into(detailProductAvatar);
                detailProductAvatarName.setText(posterName);
                posterId = response.getString("userId");
                if (getUserId().equals(posterId)) {
                    detailProductEdit.setVisibility(View.VISIBLE);
                    detailProductEdit.setActivated(true);
                    detailProductRemove.setVisibility(View.VISIBLE);
                    detailProductRemove.setActivated(true);
                    detailProductChat.setVisibility(ImageView.INVISIBLE);
                    detailProductChat.setActivated(false);

                } else {
                    detailProductChat.setVisibility(View.VISIBLE);
                    detailProductChat.setActivated(true);
                    detailProductEdit.setVisibility(ImageView.INVISIBLE);
                    detailProductRemove.setVisibility(ImageView.INVISIBLE);
                    detailProductRemove.setActivated(false);
                    detailProductEdit.setActivated(false);
                }
            }

            @Override
            public void onError(VolleyError error) {
                handleShowButton();
            }
        });
    }

    private void handleShowButton() {
        detailProductHeart.setVisibility(ImageView.INVISIBLE);
        detailProductChat.setVisibility(ImageView.INVISIBLE);
        detailProductEdit.setVisibility(ImageView.INVISIBLE);
        detailProductRemove.setVisibility(ImageView.INVISIBLE);
        detailProductChat.setActivated(false);
        detailProductEdit.setActivated(false);
        detailProductRemove.setActivated(false);
    }

    public void viewProfile() {
        ProfileService profileService = new ProfileService(getApplicationContext());
        profileService.getUserProfileByProductId(productId, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                Intent myIntent = new Intent(getApplicationContext(), ViewOtherProfile.class);
                myIntent.putExtra("userProfileModel", response.toString());
                startActivity(myIntent);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void getPosterProfile() {
        UserProfileModel[] model = new UserProfileModel[1];
        ProfileService profileService = new ProfileService(getApplicationContext());
        profileService.getUserProfileByProductId(productId, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                UserProfileModel userProfileModel = new Gson().fromJson(response.toString(), UserProfileModel.class);
                model[0] = userProfileModel;
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        this.poster = model[0];
//        Picasso.get().load(productImage).fit().into(detailProductImage);
    }

}