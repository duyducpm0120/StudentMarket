package com.example.studentmarket.Controller.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmarket.R;
import com.example.studentmarket.Services.AccountService;

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


    private Button registerButton;
    private EditText emailEditText;
    private EditText userNameEditText;
    private EditText userFullNameEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private TextView errorText;


    public Register() {
        // Required empty public constructor
    }

    private Bundle bundle;
    private String textEmail;

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        TextView regiterToLogin = (TextView) view.findViewById(R.id.registerToLogin);
        bundle = this.getArguments();
        getTextEmail(bundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImageButton regiterClose = (ImageButton) view.findViewById(R.id.regiterClose);
        registerButton = (Button) view.findViewById(R.id.register_button);
        userNameEditText = (EditText) view.findViewById(R.id.register_username_edit_text);
        userFullNameEditText = (EditText) view.findViewById(R.id.register_user_full_name_edit_text);
        phoneNumberEditText = (EditText) view.findViewById(R.id.register_phonenumber_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.register_password_edit_text);
        errorText = (TextView) view.findViewById(R.id.regiterErrorText);
        regiterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Pre_register());
                fragmentTransaction.commit();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(textEmail, userFullNameEditText.getText().toString(), userFullNameEditText.getText().toString(), phoneNumberEditText.getText().toString(), passwordEditText.getText().toString());
//                if (Validate.validatePhoneNumber(phoneNumberEditText.getText().toString())) {
//                    Log.d("validate successfully", "1");
//                    signUp(textEmail, userFullNameEditText.getText().toString(), userFullNameEditText.getText().toString(), phoneNumberEditText.getText().toString(), passwordEditText.getText().toString());
////                    Toast toast = Toast.makeText(getContext(), "Đã xảy ra lỗi,vui lòng thử lại sau", Toast.LENGTH_SHORT);
////                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
////                    toast.show();
////                    errorText.setVisibility(View.INVISIBLE);
//                } else {
//                    Log.d("validate fail", "0");
//                    errorText.setVisibility(View.VISIBLE);
//                }
            }
        });
        regiterToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Login());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void getTextEmail(Bundle bundle) {
        if (bundle != null) {
            textEmail = bundle.getString("EmailData", "");
            Toast.makeText(getContext(), textEmail, Toast.LENGTH_SHORT).show();
        }
    }


    public void signUp(String email, String username, String userFullName, String phoneNumber, String password) {
        AccountService accountService = new AccountService();
        try {
            accountService.SignUp(email, username, userFullName, phoneNumber, password, getContext());
        } catch (Exception err) {
             Toast toast = Toast.makeText(getContext(),
                     getString(R.string.SignUp_Error),
                     Toast.LENGTH_LONG);
        }
    }
}