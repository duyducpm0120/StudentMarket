package com.example.studentmarket.Controller.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentmarket.R;

import java.time.Duration;

public class ProfileAvatarBottomSheet extends Fragment {
    private LinearLayout view_avatar_section;
    private LinearLayout select_avatar_section;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_avatart_bottom_sheet,container,false);
        view_avatar_section = view.findViewById(R.id.view_avatar_section);
        select_avatar_section = view.findViewById(R.id.select_avatar_section);


        view_avatar_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click view avatar","clicked");
                Intent intent = new Intent(view.getContext(),ViewAvatar.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
