package com.example.studentmarket.Controller.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.studentmarket.Controller.Account.Login;
import com.example.studentmarket.Controller.Common.product;
import com.example.studentmarket.Controller.Common.productAdater;
import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView homeListProduct;
    private ArrayList<product> arrayProduct;
    private com.example.studentmarket.Controller.Common.productAdater productAdater;

    private RecyclerView homeListType;
    private ArrayList<type> arrayType;
    private com.example.studentmarket.Controller.Common.typeAdapter typeAdapter;

    private TextView homeTextViewSeeMore;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeTextViewSeeMore = (TextView) view.findViewById(R.id.home_textview_see_more);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();

        MappingProduct(view);
        productAdater = new productAdater(getContext(),R.layout.product,arrayProduct);
        homeListProduct.setAdapter(productAdater);

        MappingType(view);
        typeAdapter = new typeAdapter(arrayType,1);
        homeListType.setAdapter(typeAdapter);

        homeTextViewSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragmentContainerView,new ListCategory());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void MappingProduct(View view) {
        homeListProduct = (GridView) view.findViewById(R.id.home_list_products);
        arrayProduct = new ArrayList<>();
        for (int i=0;i<10;i++){
            arrayProduct.add(new product("DKNY t-shirt - colour block front logo"+i,"39.000 VND",R.drawable.img,true));
        }

    }

    private void MappingType(View view){
        homeListType = (RecyclerView) view.findViewById(R.id.home_list_type);
        arrayType = new ArrayList<>();
        for (int i=0;i<10;i++){
            arrayType.add(new type("tên "+i,R.drawable.type));
        }
    }
}