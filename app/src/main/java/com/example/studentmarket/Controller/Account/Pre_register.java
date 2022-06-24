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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.studentmarket.Component.MultiSpinner;
import com.example.studentmarket.Component.UniqueSelectSpinner;
import com.example.studentmarket.Controller.Common.CategoryType;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.Models.UniversityModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Helper.Validation.Validate;
import com.example.studentmarket.Services.ProductService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pre_register extends Fragment {

    public Pre_register() {
        // Required empty public constructor
    }

    private EditText editTextPreRegiter;
    private Bundle bundle;
    private UniqueSelectSpinner uniListDropdown;
    private ArrayList<UniversityModel> uniList = new ArrayList<UniversityModel>();
    private ArrayList<String> uniEmailSuffixList = new ArrayList<String>();
    private ArrayAdapter<String> uniListAdapter;
    private UniversityModel uniSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre_register, container, false);
        editTextPreRegiter = (EditText) view.findViewById(R.id.pre_register_email_edit_text);
        uniListDropdown = view.findViewById(R.id.uni_list_dropdown);
        uniListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        getUniList();
        TextView preRegisterLoginTextView = (TextView) view.findViewById(R.id.pre_register_login);
        Button continueRegisterButton = (Button) view.findViewById(R.id.continueRegister);
        bundle = new Bundle();
        Register registerFragment = new Register();
        registerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImageButton preRegisterClose = (ImageButton) view.findViewById(R.id.preRegisterClose);
        preRegisterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Login());
                fragmentTransaction.commit();
            }
        });
        preRegisterLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView, new Login());
                fragmentTransaction.commit();
            }
        });
        continueRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextPreRegiter.getText().toString() + uniSelected.universityEmailSuffix;
                if (Validate.validateEmail(email)) {
                    bundle.putString("EmailData", editTextPreRegiter.getText().toString() + uniSelected.universityEmailSuffix);
                    bundle.putString("uniId", String.valueOf(uniSelected.getUniversityId()));
                    fragmentTransaction.replace(R.id.fragmentContainerView, registerFragment);
                    fragmentTransaction.commit();
                } else
                    Toast.makeText(getContext(), "Email không hợp lệ", Toast.LENGTH_SHORT);
            }
        });
        editTextPreRegiter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validate.validateEmail(s.toString() + uniSelected.universityEmailSuffix)) {
                    continueRegisterButton.setEnabled(true);
                } else {
                    continueRegisterButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void getUniList() {
        ProductService productService = new ProductService(getContext());
        productService.getUniList(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                JSONArray list = response.getJSONArray("universityList");
                if (uniList != null) {
                    uniSelected =  new Gson().fromJson(list.getJSONObject(0).toString(), UniversityModel.class);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject = list.getJSONObject(i);
                        uniEmailSuffixList.add(jsonObject.getString("universityEmailSuffix"));
                        uniList.add(new Gson().fromJson(jsonObject.toString(), UniversityModel.class));
                        uniListAdapter.add(jsonObject.getString("universityEmailSuffix"));
                    }
                }
                uniListDropdown.setItems(new ArrayList<String>(uniEmailSuffixList), "Chọn email", new UniqueSelectSpinner.UniqueSelectListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        for (int i = 0; i < selected.length; i++) {
                            Log.d("key", String.valueOf(selected[i]));
                            if (selected[i] == true) {
                                uniSelected = findUniByIndex(i);
                            }
                        }
                    }
                });
                uniListDropdown.setAdapter(uniListAdapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private UniversityModel findUniByIndex(int index) {
        return uniList.get(index);
    }
}