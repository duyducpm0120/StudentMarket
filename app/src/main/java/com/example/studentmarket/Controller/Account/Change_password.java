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
import android.widget.Toast;

import com.example.studentmarket.Helper.Validation.Validate;
import com.example.studentmarket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Change_password#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Change_password extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button changePasswordButtonSave;
    private EditText changePasswordEdittextCurrent;
    private EditText changePasswordEdittextNew;
    private EditText changePasswordEdittextConfirm;
    private ImageButton changePasswordClose;

    public Change_password() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forgot_password.
     */
    // TODO: Rename and change types and number of parameters
    public static Change_password newInstance(String param1, String param2) {
        Change_password fragment = new Change_password();
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        changePasswordButtonSave = (Button) view.findViewById(R.id.change_pass_button_save);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        changePasswordEdittextCurrent = (EditText) view.findViewById(R.id.change_pass_current);
        changePasswordEdittextNew = (EditText) view.findViewById(R.id.change_pass_new);
        changePasswordEdittextConfirm = (EditText) view.findViewById(R.id.change_pass_confirm);
        changePasswordButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Đã lưu",Toast.LENGTH_LONG).show();
                String currentPass = changePasswordEdittextCurrent.getText().toString();
                String newPass = changePasswordEdittextNew.getText().toString();
                String confirmPass = changePasswordEdittextConfirm.getText().toString();
                if (currentPass.isEmpty()||newPass.isEmpty()||confirmPass.isEmpty()){
                    Toast.makeText(view.getContext(),getString(R.string.empty),Toast.LENGTH_LONG).show();
                }
                else {
                    if (!newPass.equals(confirmPass)){
                        Toast.makeText(view.getContext(),"Mật khẩu mới và mật khẩu xác nhận không giống nhau",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (Validate.validPassword(newPass)){
                            //do something
                        }
                        else {
                            Toast.makeText(view.getContext(),getString(R.string.passwordValid),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        changePasswordClose = (ImageButton) view.findViewById(R.id.changePasswordClose);
        changePasswordClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Account());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}