package com.example.studentmarket.Controller.Account;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

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

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Helper.VolleyErrorHelper;
import com.example.studentmarket.Models.LoginResponse;
import com.example.studentmarket.Models.UserProfile;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.AccountService;
import com.example.studentmarket.Services.ProfileService;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.UserProfileHolder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
                fragmentTransaction.replace(R.id.fragmentContainerView, new Pre_register());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        loginForgotPassword = (TextView) view.findViewById(R.id.login_forgot_password);
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Forgot_password());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
            accountService.Login(accountName, password, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    //save token
                    LoginResponse loginResponse = new Gson().fromJson(String.valueOf(response), LoginResponse.class);
                    Log.d("Login response token", loginResponse.getToken());
                    SharedStorage storage = new SharedStorage(getContext());
                    storage.saveValue(TOKEN_ID_KEY, loginResponse.getToken());
                    getMyProfileAndNavigate();
                }

                @Override
                public void onError(VolleyError error) {
                    // TODO: Handle error
                    VolleyErrorHelper helper = new VolleyErrorHelper(getContext());
                    helper.showVolleyError(error, "Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại");
                }
            });

        } catch (JSONException err) {
            PopupHelper popup = new PopupHelper(getContext(), "Có lỗi xảy ra. Vui lòng thừ lại", "");
            popup.Show();
        }
    }


    private void getMyProfileAndNavigate() {
        ProfileService profileService = new ProfileService(getContext());
        profileService.getMyProfile(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                //textView.setText("Response: " + response.toString());
                Log.d("get profile response",response.toString());
                UserProfile userProfile = new Gson().fromJson(String.valueOf(response), UserProfile.class);
                UserProfileHolder.getInstance().setData(userProfile);
                // navigate
                fragmentTransaction.replace(R.id.fragmentContainerView, new Profile(userProfile));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("Load profile fail", error.toString());
                VolleyErrorHelper volleyErrorHelper = new VolleyErrorHelper(getContext());
                volleyErrorHelper.showVolleyError(error, "Profile loaded fail");
            }
        });
    }


}