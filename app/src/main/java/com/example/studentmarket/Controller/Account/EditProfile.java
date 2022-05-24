package com.example.studentmarket.Controller.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.CircleTransform.CircleTransform;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProfileService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

    private UserProfile userProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userProfile = (UserProfile) getIntent().getSerializableExtra("userProfile");
        initLayout();
        initLayoutFunction();
        setDefaultValue();
    }

    private void initLayout() {
        changeAvatarTextView = findViewById(R.id.edit_profile_change_profile_edit_text);
        accountNameEditText = findViewById(R.id.edit_profile_account_name_text_box);
        universityEditText = findViewById(R.id.edit_profile_university_text_box);
        phoneEditText = findViewById(R.id.edit_profile_phone_text_box);
        emailEditText = findViewById(R.id.edit_profile_email_text_box);
        passwordEditText = findViewById(R.id.edit_profile_password_text_box);
        userNameEditText = findViewById(R.id.edit_profile_user_name_text_box);
        confirmButton = findViewById(R.id.profile_info_edit_profile_button);
        profile_avatar = findViewById(R.id.edit_profile_profile_avatar);

    }

    private void initLayoutFunction() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("change avatar", "change Avatarttt");
                updateProfile();
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
        Picasso.get().load(userProfile.getUserPic()).resize(100, 100).centerCrop().transform(new CropCircleTransformation()).into(profile_avatar);
    }

    private void updateProfile() {
        UserProfile newUserProfile = userProfile;
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