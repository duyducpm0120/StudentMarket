package com.example.studentmarket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pre_register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pre_register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pre_register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pre_register.
     */
    // TODO: Rename and change types and number of parameters
    public static pre_register newInstance(String param1, String param2) {
        pre_register fragment = new pre_register();
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
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Login());
                fragmentTransaction.commit();
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new Login());
                fragmentTransaction.commit();
            }
        });
        Button button = (Button) view.findViewById(R.id.continueRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new register());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}