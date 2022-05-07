package com.example.studentmarket.Component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentmarket.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProfileBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout view_avatar_section;
    private LinearLayout select_avatar_section;

    public ProfileBottomSheet() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_avatart_bottom_sheet,container,false);

        //init
        view_avatar_section = view.findViewById(R.id.view_avatar_section);
        select_avatar_section = view.findViewById(R.id.select_avatar_section);

        view_avatar_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        return view;
    }
}
