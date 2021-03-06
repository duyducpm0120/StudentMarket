package com.example.studentmarket.Controller.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentmarket.Constants.IntentMessage;
import com.example.studentmarket.Constants.ProfileViewMode;
import com.example.studentmarket.Controller.Common.ProductDetail;
import com.example.studentmarket.Controller.Message.ListMessages;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Store.UserProfileHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import io.getstream.avatarview.AvatarView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ViewOtherProfile extends AppCompatActivity {

    private UserProfileModel userProfileModel;

    private TabLayout tabLayout;
    private Fragment profile_info_fragment;
    private Fragment profile_post_fragment;
    private AvatarView profile_avatar;
    private TextView profile_name_text_view;
    private ImageButton closeButton;
    private ImageButton chatButton;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    public ViewOtherProfile() {
    }

    public ViewOtherProfile(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_profile);
        Intent myIntent = getIntent();
        try {
            JSONObject jsonModel = new JSONObject(myIntent.getStringExtra("userProfileModel"));
            UserProfileModel userProfileModel = new Gson().fromJson(jsonModel.toString(), UserProfileModel.class);
            this.userProfileModel = userProfileModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tabLayout = findViewById(R.id.profile_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == getResources().getString(R.string.profile_info)) {
                    openFragment(profile_info_fragment);

                } else {
                    openFragment(profile_post_fragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //init
        profile_info_fragment = new ProfileInfo(userProfileModel, ProfileViewMode.OTHER_PROFILE);
        profile_post_fragment = new ProfilePost(userProfileModel, ProfileViewMode.OTHER_PROFILE);
        fragmentManager = getSupportFragmentManager();
        //init first fragment is profile_post
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.profile_fragmentContainerView, profile_info_fragment);
        transaction.add(R.id.profile_fragmentContainerView, profile_post_fragment);
        transaction.replace(R.id.profile_fragmentContainerView, profile_post_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        tabLayout.getTabAt(1).select();
        ///
        profile_avatar = findViewById(R.id.edit_profile_profile_avatar);
        profile_name_text_view = findViewById(R.id.profile_name_text_view);
        closeButton = findViewById(R.id.close_button);
        chatButton = findViewById(R.id.view_other_profile_chat);
        // set values
        Picasso.get().load(userProfileModel.getUserPic()).transform(new CropCircleTransformation()).fit().into(profile_avatar);
        profile_name_text_view.setText(userProfileModel.getUserFullName());

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListMessages.class);
                myIntent.putExtra("posterId", userProfileModel.getUserId());
                myIntent.putExtra("posterName", userProfileModel.getUserFullName());
                myIntent.putExtra("posterAvatar", userProfileModel.getUserPic());
                ViewOtherProfile.this.startActivity(myIntent);
            }
        });
        chatButton.setVisibility(View.INVISIBLE);
//        if(this.userProfileModel.getUserId() != UserProfileHolder.getInstance().getData().getUserId())
//            chatButton.setVisibility(View.VISIBLE);


        profile_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ViewAvatar.class);
                myIntent.putExtra(IntentMessage.VIEW_AVATAR, userProfileModel.getUserPic());
                startActivity(myIntent);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.profile_fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
