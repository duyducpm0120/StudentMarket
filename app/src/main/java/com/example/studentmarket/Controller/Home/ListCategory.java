package com.example.studentmarket.Controller.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmarket.Controller.Common.type;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView listCategory;
    private ArrayList<type> arrayCategory;
    private com.example.studentmarket.Controller.Common.typeAdapter typeAdapter;
    private String[] listName = {"All Woments","New Collection","Active / Sports","Luxury","Swimwear","Casual"};

    public ListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static ListCategory newInstance(String param1, String param2) {
        ListCategory fragment = new ListCategory();
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
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        MappingType(view);
        typeAdapter = new typeAdapter(arrayCategory,2);
        listCategory.setAdapter(typeAdapter);
        return view;
    }

    private void MappingType(View view){
        listCategory = (RecyclerView) view.findViewById(R.id.list_category);
        arrayCategory = new ArrayList<>();
        for (int i=0;i<listName.length;i++){
            arrayCategory.add(new type(listName[i],R.drawable.type));
        }
    }
}