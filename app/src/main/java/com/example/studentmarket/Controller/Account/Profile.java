package com.example.studentmarket.Controller.Account;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmarket.Component.ProfileBottomSheet;
import com.example.studentmarket.R;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;


public class Profile extends Fragment {
    private TabLayout tabLayout;
    private Fragment profile_info_fragment;
    private Fragment profile_post_fragment;
    private CircularImageView profile_avatar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init
        profile_info_fragment = new Profile_info();
        profile_post_fragment = new Profile_post();
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
                ProfileBottomSheet profileBottomSheet = new ProfileBottomSheet();
                profileBottomSheet.show(getActivity().getSupportFragmentManager(),"TAG");
            }
        });

        return view;
    }
    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.profile_fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}