package com.example.studentmarket.Controller.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;


public class ProfileInfo extends Fragment {

    private EditText accountNameEditText;
    private EditText userNameEditText;
    private EditText universityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private UserProfile userProfile;

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
        accountNameEditText = view.findViewById(R.id.account_name_text_box);
        userNameEditText  = view.findViewById(R.id.name_text_box);;
        universityEditText  = view.findViewById(R.id.university_text_box);;
        phoneEditText  = view.findViewById(R.id.phone_text_box);;
        emailEditText  = view.findViewById(R.id.email_text_box);;
        passwordEditText  = view.findViewById(R.id.password_text_box);;


        //set values
        accountNameEditText.setText(userProfile.getAccountName());
        userNameEditText.setText(userProfile.getUserFullName());
        universityEditText.setText(userProfile.getUserUniversity());
        phoneEditText.setText(userProfile.getUserPhone());
        emailEditText.setText(userProfile.getUserEmail());
        passwordEditText.setText("***ADASDAS**");

        return view;
    }
}