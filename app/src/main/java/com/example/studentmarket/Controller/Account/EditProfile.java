package com.example.studentmarket.Controller.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProfileService;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {

    private EditText accountNameEditText;

    private EditText universityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;


    private UserProfile userProfile;
    TextView changeProfileTextView;


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
        changeProfileTextView = findViewById(R.id.edit_profile_change_profile_edit_text);
        accountNameEditText = findViewById(R.id.edit_profile_account_name_text_box);
        universityEditText = findViewById(R.id.edit_profile_university_text_box);
        phoneEditText = findViewById(R.id.edit_profile_phone_text_box);
        emailEditText = findViewById(R.id.edit_profile_email_text_box);
        passwordEditText = findViewById(R.id.edit_profile_password_text_box);

    }

    private void initLayoutFunction() {
        changeProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("change avatar", "change Avatarttt");
                updateProfile();
            }
        });
    }

    private void setDefaultValue() {
        accountNameEditText.setText(userProfile.getUserFullName());
        universityEditText.setText(userProfile.getUserUniversity());
        phoneEditText.setText(userProfile.getUserPhone());
        emailEditText.setText(userProfile.getUserEmail());
        passwordEditText.setText("***ADASDAS**");
    }

    private void updateProfile() {
        UserProfile newUserProfile = userProfile;
        newUserProfile.setUserFullName(accountNameEditText.getText().toString());
        ProfileService profileService = new ProfileService(this);
        try {
            profileService.updateUserProfile(newUserProfile, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception err) {
        }
    }


}