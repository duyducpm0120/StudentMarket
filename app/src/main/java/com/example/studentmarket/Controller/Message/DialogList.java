package com.example.studentmarket.Controller.Message;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentmarket.R;
import com.example.studentmarket.Store.SharedStorage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SIGN_IN_REQUEST_CODE = 10;
    private static final int RESULT_OK = 11;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView listMessenger;
    private ArrayList<Messenger> arrayMessenger;
    private MessengerApdater messengerApdater;
    private LinearLayout chatContainerUser;
    private LinearLayout chatRequireLogin;
    private EditText chatEdittextSearch;
    private String name="testuser12771@gmail.com";
    private String pass="Testuser1277";

    public DialogList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogList newInstance(String param1, String param2) {
        DialogList fragment = new DialogList();
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        MappingMessenger(view);
        messengerApdater = new MessengerApdater(arrayMessenger,getContext());
        chatContainerUser = view.findViewById(R.id.chat_container_user);
        chatRequireLogin = view.findViewById(R.id.chat_require_login);
        chatEdittextSearch = view.findViewById(R.id.chat_edittext_search);
        SharedStorage storage = new SharedStorage(getContext());

        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()){
//            chatRequireLogin.setVisibility(View.INVISIBLE);
//            chatContainerUser.setVisibility(View.VISIBLE);
//            chatEdittextSearch.setVisibility(View.VISIBLE);

        } else {
            chatContainerUser.setVisibility(View.INVISIBLE);
            chatRequireLogin.setVisibility(View.VISIBLE);
            chatEdittextSearch.setVisibility(View.INVISIBLE);
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null) {
            // Start sign in/sign up activity
            mAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        displayChatMessages("đăng nhập");
                    } else {
                        mAuth.createUserWithEmailAndPassword(name,pass).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    displayChatMessages("tạo");
                                } else {
                                    Log.d("tk",task.getException().toString());
                                    Toast.makeText(getContext(),
                                            "Đăng nhập thất bại",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast


            // Load chat room contents
            displayChatMessages("có sẵn");
        }
        return view;
    }

    private void MappingMessenger(View view){
        listMessenger = (RecyclerView) view.findViewById(R.id.chat_list_friend);
        arrayMessenger = new ArrayList<>();
        for (int i=0;i<20;i++){
            arrayMessenger.add(new Messenger("Ten"+i,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJl4HD7evSsUvmjasC52hxhvBeikYlyhu7uQ&usqp=CAU","Đây là tin nhắn để test"+i,"10:4"+i%10+"PM"));
        }
    }
    private void displayChatMessages(String signal){
        Log.d("signal",signal);
        chatRequireLogin.setVisibility(View.INVISIBLE);
            chatContainerUser.setVisibility(View.VISIBLE);
            chatEdittextSearch.setVisibility(View.VISIBLE);
            String user="user";
//            if (FirebaseAuth.getInstance()
//                    .getCurrentUser()
//                    .getDisplayName()!=null){
//                user=FirebaseAuth.getInstance()
//                        .getCurrentUser()
//                        .getDisplayName();
//            }
        Toast.makeText(getContext(),
                "Welcome " + user,
                Toast.LENGTH_LONG)
                .show();
        listMessenger.setAdapter(messengerApdater);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        listMessenger.addItemDecoration(dividerItemDecoration);
    }

}