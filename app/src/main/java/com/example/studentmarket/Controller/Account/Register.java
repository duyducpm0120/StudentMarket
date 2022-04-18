package com.example.studentmarket.Controller.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.studentmarket.R;
import com.example.studentmarket.Service.AccountService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button registerButton ;
    private EditText emailEditText;
    private EditText userNameEditText;
    private EditText userFullNameEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;




    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_register, container, false);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.close);
        registerButton =(Button) view.findViewById(R.id.register_button);
        emailEditText =(EditText) view.findViewById(R.id.pre_register_email_edit_text);
        userNameEditText =(EditText) view.findViewById(R.id.register_username_edit_text);
        userFullNameEditText =(EditText) view.findViewById(R.id.register_user_full_name_edit_text);
        phoneNumberEditText =(EditText) view.findViewById(R.id.register_phonenumber_edit_text);
        passwordEditText =(EditText) view.findViewById(R.id.register_password_edit_text);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Pre_register());
                fragmentTransaction.commit();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Click on button","clickedddd");
                AccountService accountService = new AccountService();
                accountService.SignUp("aaaa", userNameEditText.getText().toString(),userFullNameEditText.getText().toString(),phoneNumberEditText.getText().toString(),passwordEditText.getText().toString(),getContext());
            }
        });
        return view;
    }
}