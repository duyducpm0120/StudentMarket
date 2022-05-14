package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import io.getstream.avatarview.AvatarView;


public class Profile extends Fragment {
    private TabLayout tabLayout;
    private Fragment profile_info_fragment;
    private Fragment profile_post_fragment;
    private AvatarView profile_avatar;
    private UserProfile userProfile;

    public Profile(UserProfile userProfile) {
        this.userProfile = userProfile;
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
        profile_info_fragment = new ProfileInfo();
        profile_post_fragment = new ProfilePost();
        profile_avatar = view.findViewById(R.id.profile_avatar);


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
                Button buttonTest = bottomSheetView.findViewById(R.id.buttonTest);
                buttonTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AvatarView avatar = view.findViewById(R.id.profile_avatar);
                        navigateToViewAvatar(view, avatar.getBackground());
                    }
                });
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