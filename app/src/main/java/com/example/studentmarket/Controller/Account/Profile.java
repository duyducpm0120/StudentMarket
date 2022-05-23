package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentmarket.Helper.CircleTransform.CircleTransform;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import io.getstream.avatarview.AvatarView;


public class Profile extends Fragment {
    private TabLayout tabLayout;
    private Fragment profile_info_fragment;
    private Fragment profile_post_fragment;
    private AvatarView profile_avatar;
    private TextView profile_name_text_view;

    private UserProfile userProfile;

    public Profile(UserProfile userProfile) {
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
        profile_avatar = view.findViewById(R.id.profile_avatar);
        profile_name_text_view = view.findViewById(R.id.profile_name_text_view);

        // set values
        Picasso.get().load(userProfile.getUserPic()).resize(110, 110).centerCrop().transform(new CircleTransform()).into(profile_avatar);
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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                View bottomSheetView = inflater.inflate(R.layout.fragment_profile_avatart_bottom_sheet, view.findViewById(R.id.bottom_sheet_id), false);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

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
}