package com.example.studentmarket.Controller.Account;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.example.studentmarket.Constants.ProfileViewMode;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Helper.VolleyErrorHelper;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProfileService;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.UserProfileHolder;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ProfileInfo extends Fragment {

    private EditText accountNameEditText;

    private EditText universityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private UserProfileModel userProfile;
    private Button editProfileButton;
    private TextView logOutButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction ;
    private ProfileViewMode viewMode;

    public ProfileInfo(UserProfileModel userProfile, ProfileViewMode viewMode) {
        this.userProfile = userProfile;
        this.viewMode = viewMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        //init
        accountNameEditText = view.findViewById(R.id.edit_profile_account_name_text_box);

        universityEditText = view.findViewById(R.id.university_text_box);

        phoneEditText = view.findViewById(R.id.phone_text_box);

        emailEditText = view.findViewById(R.id.email_text_box);

        passwordEditText = view.findViewById(R.id.password_text_box);

        editProfileButton = view.findViewById(R.id.confirm_button);

        logOutButton = view.findViewById(R.id.logout);

        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Login());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                SharedStorage storage = new SharedStorage(getContext());
                storage.removeValue(TOKEN_ID_KEY);
                UserProfileHolder.getInstance().setData(null);
            }
        });

        setValues();


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), EditProfile.class);
                getContext().startActivity(myIntent);
            }
        });

        if (this.viewMode == ProfileViewMode.OTHER_PROFILE)
            editProfileButton.setVisibility(View.INVISIBLE);

        return view;
    }

    private void updateUserProfileHolder() {
//        UserProfileHolder.getInstance().updateUserData(getContext());
//        this.userProfile = UserProfileHolder.getInstance().getData();
        ProfileService profileService = new ProfileService(getContext());
        profileService.getMyProfile(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                //textView.setText("Response: " + response.toString());
                Log.d("reload profile response", response.toString());
                UserProfileModel userProfile = new Gson().fromJson(String.valueOf(response), UserProfileModel.class);
                UserProfileHolder.getInstance().setData(userProfile);
                Log.d("reload profile response user pic", userProfile.getUserPic());
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("reLoad profile fail", error.toString());
                VolleyErrorHelper volleyErrorHelper = new VolleyErrorHelper(getContext());
                volleyErrorHelper.showVolleyError(error, "Profile loaded fail");
            }
        });
    }

    private void setValues() {
        Log.d("user profile name", userProfile.getUserFullName());
        //set values
        accountNameEditText.setText(userProfile.getAccountName());
        universityEditText.setText(userProfile.getUserUniversity().getUniversityName());
        phoneEditText.setText(userProfile.getUserPhone());
        emailEditText.setText(userProfile.getUserEmail());
        passwordEditText.setText("***ADASDAS**");
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        updateUserProfileHolder();
//        FragmentManager fragmentManager;
//        FragmentTransaction fragmentTransaction;
//        fragmentManager = getParentFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.detach(this).attach(this).commit();
//    }
}