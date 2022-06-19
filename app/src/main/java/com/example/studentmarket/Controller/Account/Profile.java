package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.example.studentmarket.Constants.IntentMessage;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Helper.VolleyErrorHelper;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProfileService;
import com.example.studentmarket.Store.UserProfileHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import io.getstream.avatarview.AvatarView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class Profile extends Fragment {
    private TabLayout tabLayout;
    private Fragment profile_info_fragment;
    private Fragment profile_post_fragment;
    private AvatarView profile_avatar;
    private TextView profile_name_text_view;

    private UserProfileModel userProfile;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    public Profile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
        Log.d("profile accnamne", userProfile.getAccountName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init
        profile_info_fragment = new ProfileInfo(userProfile);
        profile_post_fragment = new ProfilePost();
        fragmentManager = getParentFragmentManager();
        //init first fragment is profile_post
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.profile_fragmentContainerView, profile_post_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        ///
        profile_avatar = view.findViewById(R.id.edit_profile_profile_avatar);
        profile_name_text_view = view.findViewById(R.id.profile_name_text_view);
        // set values
        Picasso.get().load(userProfile.getUserPic()).transform(new CropCircleTransformation()).resize(110, 110).centerInside().into(profile_avatar);
        profile_name_text_view.setText(userProfile.getUserFullName());
        //Call TabLayout
        tabLayout = view.findViewById(R.id.profile_tab_layout);
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


        profile_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProfileBottomSheet profileBottomSheet = new ProfileBottomSheet();
//                profileBottomSheet.show(getActivity().getSupportFragmentManager(),"TAG");

//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//                View bottomSheetView = inflater.inflate(R.layout.fragment_profile_avatart_bottom_sheet, view.findViewById(R.id.bottom_sheet_id), false);
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();
                Intent myIntent = new Intent(getContext(), ViewAvatar.class);
                myIntent.putExtra(IntentMessage.VIEW_AVATAR, userProfile.getUserPic());
                getContext().startActivity(myIntent);
            }
        });

        return view;
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.profile_fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void navigateToViewAvatar(View view, Drawable background) {
        Intent intent = new Intent(view.getContext(), ViewAvatar.class);
        intent.putExtra("avatar", "aaa");
        startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        updateUserProfileHolder();
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(this).attach(this).commit();
    }
}