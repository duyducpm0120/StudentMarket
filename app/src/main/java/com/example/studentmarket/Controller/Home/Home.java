package com.example.studentmarket.Controller.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmarket.Controller.Common.ProductDetail;
import com.example.studentmarket.Controller.Common.product;
import com.example.studentmarket.Controller.Common.productAdater;
import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.R;
import static com.example.studentmarket.Controller.Common.IndexCategory.*;

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
    private EditText homeEdittextSearch;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    private String searchText;
    private String[] listName = {"All Woments","New Collection","Active / Sports","Luxury","Swimwear","Casual"};

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
        homeEdittextSearch = view.findViewById(R.id.home_edittext_search);

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
//                fragmentTransaction.replace(R.id.fragmentContainerView,new ListCategory1());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                Intent myIntent = new Intent(getContext(), ListCategory.class);
                getContext().startActivity(myIntent);
            }
        });

        homeEdittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = s.toString();
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {

                }
            }
        });

        return view;
    }

    private void MappingProduct(View view) {
        homeListProduct = (GridView) view.findViewById(R.id.home_list_products);
        arrayProduct = new ArrayList<>();
        for (int i=0;i<10;i++){
            arrayProduct.add(new product("DKNY t-shirt - colour block front logo"+i,"3"+i+".000 VND",R.drawable.img,true));
        }

    }

    private void MappingType(View view){
        homeListType = (RecyclerView) view.findViewById(R.id.home_list_type);
        arrayType = new ArrayList<>();
        for (int i=0;i<listName.length;i++){
            arrayType.add(new type(listName[i],R.drawable.type,false));
        }
        setIndex(-1);
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                Toast.makeText(getContext(), searchText, Toast.LENGTH_SHORT).show();
            }
        }
    };
}