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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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
    private String name="testuser127711@gmail.com";
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
//        MappingMessenger(view);
        chatContainerUser = view.findViewById(R.id.chat_container_user);
        chatRequireLogin = view.findViewById(R.id.chat_require_login);
        chatEdittextSearch = view.findViewById(R.id.chat_edittext_search);
        SharedStorage storage = new SharedStorage(getContext());

//        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()){
//            chatRequireLogin.setVisibility(View.INVISIBLE);
//            chatContainerUser.setVisibility(View.VISIBLE);
//            chatEdittextSearch.setVisibility(View.VISIBLE);
//
//        } else {
//            chatContainerUser.setVisibility(View.INVISIBLE);
//            chatRequireLogin.setVisibility(View.VISIBLE);
//            chatEdittextSearch.setVisibility(View.INVISIBLE);
//        }
        listMessenger = (RecyclerView) view.findViewById(R.id.chat_list_friend);
        arrayMessenger = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Converstation").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot child : snapshot.getChildren()){
                   FirebaseConversation conversation = child.getValue(FirebaseConversation.class);
                   arrayMessenger.add(new Messenger(conversation.getId(),conversation.getUser1(),conversation.getImg1(),"test","10"));
               }
               messengerApdater = new MessengerApdater(arrayMessenger,getContext());
               listMessenger.setAdapter(messengerApdater);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        displayChatConv();

//        String[] ids= {"1","2","3"};
//        String img1="data:image/jpeg;base64,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";
//        String img2 ="data:image/jpeg;base64,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";
//
//        int dem=0;
//        for (int i=0;i<3;i++){
//            for (int j=0;j<3;j++){
//                if (i!=j){
//                    FirebaseDatabase.getInstance().getReference("Converstation").push().setValue(new FirebaseConversation(String.valueOf(dem),ids[i],ids[j],img1,img2));
//                    dem++;
//                }
//            }
//        }
        return view;
    }

    private void displayChatConv(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        listMessenger.addItemDecoration(dividerItemDecoration);
    }

}