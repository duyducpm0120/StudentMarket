package com.example.studentmarket.Controller.Account;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentmarket.Controller.Common.PostProduct;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.UserProfileHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button accountButtonLogin;
    private Button accountButotnForgotPass;
    private Button accountNotice;
    private Button accountPost;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction ;

    private static final Account account = new Account();

    public Account getInstance () {
        if (account != null) return account;
        return new Account();
    }


    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        String token = new SharedStorage(getContext()).getValue(TOKEN_ID_KEY);
        Log.d("@@@@@@@@@@token", token);
        if(token != "") {
            UserProfileModel userProfile = UserProfileHolder.getInstance().getData();
            if(userProfile!= null) {}
            fragmentTransaction.replace(R.id.fragmentContainerView, new Profile(userProfile));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);



        accountButtonLogin = (Button) view.findViewById(R.id.button);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        accountButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Login());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        accountButotnForgotPass = (Button) view.findViewById(R.id.button1);
        accountButotnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Change_password());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        accountNotice = (Button) view.findViewById(R.id.button2);
        accountNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setMessage("Bạn nhập sai tên đăng nhập hoặc mật khẩu. Vui lòng kiểm tra lại và nhập đúng thông tin");
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                TextView title = new TextView(getActivity());
                title.setText("Thông báo");
                title.setPadding(10, 20, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextSize(25);
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                builder.setCustomTitle(title);
                AlertDialog alertDialog = builder.show();
                TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
                alertDialog.show();
            }
        });
        accountPost = view.findViewById(R.id.button_post);
        accountPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), PostProduct.class);
                startActivity(myIntent);
            }
        });
        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

            }
        });


        return view;
    }
}