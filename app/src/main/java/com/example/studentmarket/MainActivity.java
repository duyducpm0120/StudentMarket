package com.example.studentmarket;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.studentmarket.Controller.Account.Account;
import com.example.studentmarket.Controller.Message.DialogList;
import com.example.studentmarket.Controller.Favorite.Favorite;
import com.example.studentmarket.Controller.Home.Home;
import com.example.studentmarket.Models.UserProfileModel;
import com.example.studentmarket.Store.SharedStorage;
import com.example.studentmarket.Store.UserProfileHolder;
import com.example.studentmarket.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        removeToken();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home());
        initUserProfile();
        Account account = new Account();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new Home());
                    break;
                case R.id.category:
                    replaceFragment(new DialogList());
                    break;
                case R.id.favorite:
                    replaceFragment(new Favorite());
                    break;
                case R.id.account:
                    replaceFragment(account.getInstance());
                    break;
                default:
                    return Boolean.parseBoolean(null); // Problem occurs at this condition!
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fr) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fr);
        fragmentTransaction.commit();

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeToken();
    }

    private void removeToken() {
        SharedStorage sharedStorage = new SharedStorage(getApplicationContext());
        sharedStorage.removeValue(TOKEN_ID_KEY);
    }

    private void initUserProfile () {
        UserProfileHolder.getInstance().setData(new UserProfileModel());
    }
}