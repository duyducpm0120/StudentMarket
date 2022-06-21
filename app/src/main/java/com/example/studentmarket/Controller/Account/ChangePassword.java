package com.example.studentmarket.Controller.Account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.Validation.Validate;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.AccountService;
import com.example.studentmarket.Store.UserProfileHolder;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    private EditText oldPasswordEdittext;
    private EditText newPasswordEdittext;
    private EditText confirmPasswordEditText;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initLayout();


    }

    private void initLayout() {
        oldPasswordEdittext = findViewById(R.id.change_pass_current);
        newPasswordEdittext = findViewById(R.id.change_pass_new);
        confirmPasswordEditText = findViewById(R.id.change_pass_confirm);
        confirmButton = findViewById(R.id.change_pass_button_save);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass = oldPasswordEdittext.getText().toString();
                String newPass = newPasswordEdittext.getText().toString();
                String confirmPass = confirmPasswordEditText.getText().toString();
                if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.empty), Toast.LENGTH_LONG).show();
                } else {
                    if (!newPass.equals(confirmPass)) {
                        Toast.makeText(getApplicationContext(), "Mật khẩu mới và mật khẩu xác nhận không giống nhau", Toast.LENGTH_LONG).show();
                    } else {
                        AccountService accountService = new AccountService(getApplicationContext());
                        accountService.ChangePassword(UserProfileHolder.getInstance().getData().userId, currentPass, newPass, new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("change password success", "");
                                Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành côpng", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.d("change password fail", "");
                                Toast.makeText(getApplicationContext(), "Có lỗi. Đổi mật khẩu thất bại", Toast.LENGTH_LONG).show();
                            }
                        });
//                        if (Validate.validPassword(newPass)) {
//                            //do something
//                            AccountService accountService = new AccountService(getApplicationContext());
//                            accountService.ChangePassword(UserProfileHolder.getInstance().getData().userId, currentPass, newPass, new VolleyCallback() {
//                                @Override
//                                public void onSuccess(JSONObject response) throws JSONException {
//                                    Log.d("change password success", "");
//                                }
//
//                                @Override
//                                public void onError(VolleyError error) {
//                                    Log.d("change password fail", "");
//                                }
//                            });
//                        } else {
//                            Toast.makeText(getApplicationContext(), getString(R.string.passwordValid), Toast.LENGTH_LONG).show();
//                        }
                    }
                }
            }
        });
    }
}
