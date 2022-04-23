package com.example.studentmarket.Controller.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.studentmarket.Helper.Validation.Validate;
import com.example.studentmarket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Forgot_password#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forgot_password extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forgot_password() {
        // Required empty public constructor
    }
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageButton forgotPasswordClose;
    private TextView forgotPasswordToRegister;
    private EditText forgotPasswordEdittextEmail;
    private Button forgotPasswordButtonAgree;
    private TextView forgotPasswordTextViewWarning;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment change_pass.
     */
    // TODO: Rename and change types and number of parameters
    public static Forgot_password newInstance(String param1, String param2) {
        Forgot_password fragment = new Forgot_password();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        forgotPasswordClose = (ImageButton) view.findViewById(R.id.forgot_password_button_close);
        forgotPasswordButtonAgree =(Button) view.findViewById(R.id.forgot_password_button_agree);
        forgotPasswordTextViewWarning = (TextView) view.findViewById(R.id.forgot_password_textview_warning);
        forgotPasswordEdittextEmail = (EditText) view.findViewById(R.id.forgot_password_edittext_email);
        forgotPasswordClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Login());
                fragmentTransaction.commit();
            }
        });
        forgotPasswordToRegister = (TextView) view.findViewById(R.id.forgot_password_to_register);
        forgotPasswordToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Pre_register());
                fragmentTransaction.commit();
            }
        });

        forgotPasswordButtonAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate.validateEmail(forgotPasswordEdittextEmail.getText().toString()))
                {
                    forgotPasswordTextViewWarning.setVisibility(View.INVISIBLE);
                    //do something
                }
                else
                {
                    forgotPasswordTextViewWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}