package com.example.studentmarket.Controller.Account;

import static com.example.studentmarket.Constants.RequestCode.REQUEST_PERMISSIONS;
import static com.example.studentmarket.Constants.RequestCode.SELECT_PICTURE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProfileService;
import com.example.studentmarket.Store.UserProfileHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.getstream.avatarview.AvatarView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EditProfile extends AppCompatActivity {

    private EditText accountNameEditText;
    private EditText userNameEditText;
    private EditText universityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView changeAvatarTextView;
    private Button confirmButton;
    private AvatarView profile_avatar;

    private UserProfileModel userProfile;

    private Bitmap bitmap;
    private String filePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {

                    //textView.setText("File Selected");
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    new ProfileService(getApplicationContext()).updateUserAvatar(bitmap, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {
                            PopupHelper popupHelper = new PopupHelper(getApplicationContext(),"Cập nhật ảnh đại diện thành công","");
                        }

                        @Override
                        public void onError(VolleyError error) {
                            PopupHelper popupHelper = new PopupHelper(getApplicationContext(),"Cập nhật ảnh đại diện thất bại","");
                        }
                    });
                    Picasso.get().load(picUri).resize(110, 110).transform(new CropCircleTransformation()).centerCrop().into(profile_avatar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(
                        EditProfile.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userProfile = UserProfileHolder.getInstance().getData();
        initLayout();
        initLayoutFunction();
        setDefaultValue();
    }

    private void showFileChooser () {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private void initLayout() {
        changeAvatarTextView = findViewById(R.id.edit_profile_change_profile_edit_text);
        accountNameEditText = findViewById(R.id.edit_profile_account_name_text_box);
        universityEditText = findViewById(R.id.edit_profile_university_text_box);
        phoneEditText = findViewById(R.id.edit_profile_phone_text_box);
        emailEditText = findViewById(R.id.edit_profile_email_text_box);
        passwordEditText = findViewById(R.id.edit_profile_password_text_box);
        userNameEditText = findViewById(R.id.edit_profile_user_name_text_box);
        confirmButton = findViewById(R.id.confirm_button);
        profile_avatar = findViewById(R.id.edit_profile_profile_avatar);

    }

    private void initLayoutFunction() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("change avatar", "change Avatarttt");
                updateProfile();
                finish();
            }
        });
        changeAvatarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(EditProfile.this ,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(EditProfile.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(EditProfile.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    showFileChooser();
                }

            }
        });
    }

    private void setDefaultValue() {
        userNameEditText.setText(userProfile.getUserFullName());
        accountNameEditText.setText(userProfile.getAccountName());
        universityEditText.setText(userProfile.getUserUniversity());
        phoneEditText.setText(userProfile.getUserPhone());
        emailEditText.setText(userProfile.getUserEmail());
        passwordEditText.setText("***ADASDAS**");
        Picasso.get().load(userProfile.getUserPic()).transform(new CropCircleTransformation()).resize(110, 110).centerInside().into(profile_avatar);
    }

    private void updateProfile() {
        UserProfileModel newUserProfile = userProfile;
        newUserProfile.setUserFullName(userNameEditText.getText().toString());
        newUserProfile.setUserPhone(phoneEditText.getText().toString());
        ProfileService profileService = new ProfileService(this);
        try {
            profileService.updateUserProfile(newUserProfile, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    Log.d("update success", "");
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("update error", "");
                }
            });
        } catch (Exception err) {
        }
    }


}