package com.example.studentmarket.Controller.Message;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.studentmarket.Component.categoryInterface;
import com.example.studentmarket.Controller.Common.CategoryType;
import com.example.studentmarket.Controller.Common.typeAdapter;
import com.example.studentmarket.Helper.Utils;
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

import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private MessengerApdater messengerApdaterSearch;

    private LinearLayout chatContainerUser;
    private LinearLayout chatRequireLogin;
    private LinearLayout chatEmptySearch;
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
        chatEmptySearch = view.findViewById(R.id.chat_empty_search);
        SharedStorage storage = new SharedStorage(getContext());
        Utils utils = new Utils();


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

        chatEdittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()>0){
                    String rawInput = utils.stripAccents(s.toString());
                    ArrayList<Messenger> listSearch = new ArrayList<>();
                    for (int i=0;i<arrayMessenger.size();i++){
                        Messenger messengerItem = arrayMessenger.get(i);
                        String rawName = utils.stripAccents(messengerItem.getName());
                        if (rawName.toLowerCase().contains(rawInput.toLowerCase())){
                            listSearch.add(messengerItem);
                        }
                    }
                    if (listSearch.size()>0){
                        chatEmptySearch.setVisibility(View.INVISIBLE);
                        messengerApdaterSearch = new MessengerApdater(listSearch, getContext());
                        listMessenger.setAdapter(messengerApdaterSearch);
                    } else {
                        chatEmptySearch.setVisibility(View.VISIBLE);
                    }
                } else {
                    chatEmptySearch.setVisibility(View.INVISIBLE);
                    listMessenger.setAdapter(messengerApdater);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private String convertMiliSecondTimesToTimeAgo(String time){
        Date date = new Date();
        long timeLong = Long.parseLong(time);
        long timeAgo = date.getTime() - timeLong;
        long timeAgoSecond = timeAgo / 1000;
        long timeAgoMinute = timeAgoSecond / 60;
        long timeAgoHour = timeAgoMinute / 60;
        long timeAgoDay = timeAgoHour / 24;
        long timeAgoWeek = timeAgoDay / 7;
        long timeAgoMonth = timeAgoWeek / 4;
        long timeAgoYear = timeAgoMonth / 12;
        if (timeAgoSecond < 60){
            return "Vừa xong";
        } else if (timeAgoMinute < 60){
            return timeAgoMinute + " phút trước";
        } else if (timeAgoHour < 24){
            return timeAgoHour + " giờ trước";
        } else if (timeAgoDay < 7){
            return timeAgoDay + " ngày trước";
        } else if (timeAgoWeek < 4){
            return timeAgoWeek + " tuần trước";
        } else if (timeAgoMonth < 12){
            return timeAgoMonth + " tháng trước";
        } else {
            return timeAgoYear + " năm trước";
        }
    }

    private void displayChatConv(View view){
        listMessenger = (RecyclerView) view.findViewById(R.id.chat_list_friend);
        arrayMessenger = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Conversation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayMessenger.clear();
                for (DataSnapshot childConversation : snapshot.getChildren()){
                    try {
                        FirebaseConversation conversation = childConversation.getValue(FirebaseConversation.class);
                        String userId1 = conversation.getUser1();
                        String userId2 = conversation.getUser2();
                        String userName1 = conversation.getUserName1();
                        String userName2 = conversation.getUserName2();
                        String userName = "";
                        String avatar = "";
                        String userId = "";
                        String isRead = "";
                        if (!userId1.equals(myId)) {
                            userId = userId1;
                            userName = userName1;
                            avatar = conversation.getImg1();
                        } else {
                            userId = userId2;
                            userName = userName2;
                            avatar = conversation.getImg2();
                        }
                        String formatTime = convertMiliSecondTimesToTimeAgo(conversation.getLastMsgTime());
                        if (userId1.equals(myId) || userId2.equals(myId)) {
                            arrayMessenger.add(new Messenger(conversation.getId(), userId, userName, avatar, conversation.getLastMsg(), formatTime, conversation.getLastMsgTime()));
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //sort array to display newest message first
                Collections.sort(arrayMessenger, new Comparator<Messenger>() {
                    @Override
                    public int compare(Messenger o1, Messenger o2) {
                        return o2.getRawTime().compareTo(o1.getRawTime());
                    }
                });
                messengerApdater = new MessengerApdater(arrayMessenger,getContext());
                listMessenger.setAdapter(messengerApdater);
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