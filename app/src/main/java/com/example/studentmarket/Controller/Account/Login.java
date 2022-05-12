package com.example.studentmarket.Controller.Account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.AccountService;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.StorageKeyConstant;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button loginButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageButton loginClose;
    private TextView loginToRegister;
    private TextView loginForgotPassword;
    private EditText loginEditTextEmail;
    private EditText loginEditTextPassword;

    private UserProfile userProfile;

    public Login() {
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
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        loginEditTextEmail = (EditText) view.findViewById(R.id.login_username_edit_text);
        loginEditTextPassword = (EditText) view.findViewById(R.id.login_password_edit_text);
        loginClose = (ImageButton) view.findViewById(R.id.login_close_button);
        loginClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction.replace(R.id.fragmentContainerView,new Account());
//                fragmentTransaction.commit();
                getParentFragmentManager().popBackStackImmediate();
            }
        });
        loginToRegister = (TextView) view.findViewById(R.id.login_to_register);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Pre_register());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        loginForgotPassword = (TextView) view.findViewById(R.id.login_forgot_password);
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Forgot_password());
                fragmentTransaction.addToBackStack(null);
            }
        });
        loginButton = view.findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = loginEditTextEmail.getText().toString();
                String textPassword = loginEditTextPassword.getText().toString();
                if (!textEmail.isEmpty() && !textPassword.isEmpty()) {
                    login(textEmail, textPassword);
                } else {
//                    PopupHelper popup = new PopupHelper(getContext(), "Đăng nhập thành công", "");
                    Toast.makeText(getActivity(), getString(R.string.empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    public void login(String accountName, String password) {
        AccountService accountService = new AccountService(getContext());
        try {
            accountService.Login(accountName, password);
            String key = new SharedStorage(getContext()).getValue(new StorageKeyConstant().getTokenIdKey());
            if(key != "") {
                UserProfile userProfile = new UserProfile("111","aaa", "email", "0123", "123123");
                fragmentTransaction.replace(R.id.fragmentContainerView,new Profile(userProfile));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } catch (JSONException err) {
            PopupHelper popup = new PopupHelper(getContext(), "Đăng nhập không thành công", "");
            popup.Show();
        }
    }

}