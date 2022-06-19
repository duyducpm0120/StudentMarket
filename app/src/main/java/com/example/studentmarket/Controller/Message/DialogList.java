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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static com.example.studentmarket.Helper.globalValue.*;

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
    private String myId="2";

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

        if (getUserId()!=null){
            myId = getUserId();
        }

        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()){
            chatRequireLogin.setVisibility(View.INVISIBLE);
            chatContainerUser.setVisibility(View.VISIBLE);
            chatEdittextSearch.setVisibility(View.VISIBLE);
            displayChatConv(view);
        } else {
            chatContainerUser.setVisibility(View.INVISIBLE);
            chatRequireLogin.setVisibility(View.VISIBLE);
            chatEdittextSearch.setVisibility(View.INVISIBLE);
        }


        return view;
    }

    private void displayChatConv(View view){
        listMessenger = (RecyclerView) view.findViewById(R.id.chat_list_friend);
        arrayMessenger = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Conversation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    FirebaseConversation conversation = child.getValue(FirebaseConversation.class);
                    final String[] lastMsg = {"Hiện chưa có tin nhắn"};
                    final Date[] lastTime = {new Date()};
                    if (conversation.getUser1().equals(myId) || conversation.getUser2().equals(myId)){
                        FirebaseDatabase.getInstance().getReference("Message_"+conversation.getId()).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()){
                                    FirebaseMessage message = child.getValue(FirebaseMessage.class);
                                    lastMsg[0] = message.getMsg();
                                    lastTime[0] = message.getDate();
                                }
                                arrayMessenger.add(new Messenger(conversation.getId(),conversation.getUser1().equals(myId) ? conversation.getUser2() : conversation.getUser1(), conversation.getUserName1().equals(getUsername()) ? conversation.getUserName2() : conversation.getUserName1(), conversation.getImg1(),lastMsg[0],String.valueOf(lastTime[0].getTime())));
                                messengerApdater = new MessengerApdater(arrayMessenger,getContext());
                                listMessenger.setAdapter(messengerApdater);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        listMessenger.addItemDecoration(dividerItemDecoration);

    }

}