package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;


public class ProfileInfo extends Fragment {

    private EditText accountNameEditText;

    private EditText universityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private UserProfile userProfile;
    private Button editProfileButton;

    public ProfileInfo(UserProfile userProfile) {
        this.userProfile = userProfile;
        Log.d("profile accnamne", userProfile.getAccountName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        //init
        accountNameEditText = view.findViewById(R.id.edit_profile_account_name_text_box);

        universityEditText  = view.findViewById(R.id.university_text_box);;
        phoneEditText  = view.findViewById(R.id.phone_text_box);;
        emailEditText  = view.findViewById(R.id.email_text_box);;
        passwordEditText  = view.findViewById(R.id.password_text_box);;
        editProfileButton = view.findViewById(R.id.profile_info_edit_profile_button);


        //set values
        accountNameEditText.setText(userProfile.getAccountName());
        universityEditText.setText(userProfile.getUserUniversity());
        phoneEditText.setText(userProfile.getUserPhone());
        emailEditText.setText(userProfile.getUserEmail());
        passwordEditText.setText("***ADASDAS**");

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), EditProfile.class);
                myIntent.putExtra("userProfile",userProfile);
                getContext().startActivity(myIntent);
            }
        });

        return view;
    }
}