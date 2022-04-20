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

import com.example.studentmarket.R;
import com.example.studentmarket.Validate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pre_register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pre_register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pre_register() {
        // Required empty public constructor
    }
    private EditText editTextPreRegiter;
    private Bundle bundle;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pre_register.
     */
    // TODO: Rename and change types and number of parameters
    public static Pre_register newInstance(String param1, String param2) {
        Pre_register fragment = new Pre_register();
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
        View view= inflater.inflate(R.layout.fragment_pre_register, container, false);
        editTextPreRegiter = (EditText) view.findViewById(R.id.pre_register_email_edit_text);
        TextView preRegisterLoginTextView = (TextView) view.findViewById(R.id.pre_register_login);
        Button continueRegisterButton = (Button) view.findViewById(R.id.continueRegister);

        bundle = new Bundle();
        Register registerFragment = new Register();
        registerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        ImageButton preRegisterClose = (ImageButton) view.findViewById(R.id.preRegisterClose);
        preRegisterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Login());
                fragmentTransaction.commit();
            }
        });
        preRegisterLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Login());
                fragmentTransaction.commit();
            }
        });
        continueRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("EmailData",editTextPreRegiter.getText().toString());
                fragmentTransaction.replace(R.id.fragmentContainerView,registerFragment);
                fragmentTransaction.commit();
            }
        });
        editTextPreRegiter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validate.validateEmail(s.toString())){
                    continueRegisterButton.setEnabled(true);
                }
                else {
                    continueRegisterButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}