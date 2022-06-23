package com.example.studentmarket.Controller.Message;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.PushNotificationService;
import com.example.studentmarket.Store.SharedStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;
import static com.example.studentmarket.Helper.globalValue.*;

public class ListMessages extends AppCompatActivity {
    private MessagesList messagesList;
    private MessageInput inputView;
    private ImageButton goBackButton;
    private TextView listMessagesChatName;
    private MessagesListAdapter<Message> adapter;
    private Author senderAuthor;
    private Author receiverAuthor;
    private boolean isFirstAccess = true;
    private DatabaseReference mesageRef;
    private DatabaseReference conversationRef;
    private String myId="2";
    private String conversationId;
    private PushNotificationService pushNotificationService;
    private String posterId;
    private FirebaseDatabase database;
    private String posterName;
    private String posterAvatar;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(url).into(imageView);
            }
        };
        pushNotificationService = new PushNotificationService(this);
        database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Conversation");
        Intent intent = getIntent();
        posterName = intent.getStringExtra("posterName");
        posterId = intent.getStringExtra("posterId");
        posterAvatar = intent.getStringExtra("posterAvatar");
        conversationId = intent.getStringExtra("id");
        imageUrl = getUserImg();
        if (getUserId() != null) {
            myId = getUserId();
        }
        if (conversationId == null) {
            final String[] newConversationId = {"1"};

            if (Integer.parseInt(posterId) < Integer.parseInt(myId)) {
                newConversationId[0] = posterId +"-"+ myId;
            } else {
                newConversationId[0] = myId +"-"+ posterId;
            }

            conversationRef.child("Conversation_"+newConversationId[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue()==null){
                        conversationRef.child("Conversation_"+newConversationId[0]).setValue(new FirebaseConversation(newConversationId[0], myId,posterId,getUsername(),posterName, posterAvatar, imageUrl,"Hiện không có tin nhắn nào",String.valueOf(new Date().getTime())));
                        conversationId = newConversationId[0];
                    } else {
                        try {
                            FirebaseConversation conversation = snapshot.getValue(FirebaseConversation.class);
                            if (conversation.getId() != null) {
                                conversationId = conversation.getId();
                                initListener();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            initListener();
        }

        listMessagesChatName = findViewById(R.id.list_messages_name);
        listMessagesChatName.setText(posterName);

        senderAuthor = new Author(myId,"test","https://i.pinimg.com/236x/c1/c8/49/c1c8498d9aec3d4e6c894ddba7882031.jpg");
        receiverAuthor = new Author("receiverId",posterName,posterAvatar);
        Log.d("posterId",posterAvatar);
//        listMessagesChatName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ListMessages.this, "test", Toast.LENGTH_SHORT).show();
//            }
//        });
        String senderId = myId;
        messagesList = findViewById(R.id.message_list);
        adapter = new MessagesListAdapter<Message>(senderId, imageLoader){
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (holder.getItemViewType() == -132) { // -132 is an outgoing picture message
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.itemView.findViewById(com.stfalcon.chatkit.R.id.image).getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_END);
                }
            }
        };
        adapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int total) {
                Log.d("test",total+"");
                Log.d("test",page+"");

            }
        });
        messagesList.setAdapter(adapter);
        initViews(adapter);
//        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
//            @Override
//            public void onAddAttachments() {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Title"),1);
//            }
//        });
        goBackButton = findViewById(R.id.chat_goback_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        mesageRef = database.getReference("Message_"+conversationId);
        listenerFirstMessage(myId);
        listenerMessage(myId);
    }

    private void listenerFirstMessage(String finalMyId) {
        mesageRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().getValue()==null){
                    isFirstAccess = false;
                }
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        try {
                            FirebaseMessage message = dataSnapshot.getValue(FirebaseMessage.class);
                            if (message.getAuthorId().equals(finalMyId)) {
                                adapter.addToStart(new Message(id, message.getMsg(), senderAuthor, message.getDate()), true);
                            } else {
                                adapter.addToStart(new Message(id, message.getMsg(), receiverAuthor, message.getDate()), true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void listenerMessage(String finalMyId) {
        mesageRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for(DataSnapshot snapshot_ : snapshot.getChildren()) {
                        FirebaseMessage message = snapshot_.getValue(FirebaseMessage.class);
                        if (!message.getAuthorId().equals(finalMyId)) {
                            if (!isFirstAccess){
                                adapter.addToStart(new Message(message.getconversationId(),message.getMsg(),receiverAuthor,message.getDate()), true);
                            }
                        }
                        isFirstAccess = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews(MessagesListAdapter<Message> adapter) {
        inputView = findViewById(R.id.message_input);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                Message message = new Message("SenderId", input.toString(),senderAuthor,new Date());
                //validate and send message
                adapter.addToStart(message, true);
                database
                        .getReference("Message_"+conversationId)
                        .push()
                        .setValue(new FirebaseMessage(conversationId,input.toString(),getUsername(),myId,new Date()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("firebase", "success");
                                    pushNotificationService.sendNewMessageNotification(posterId);
                                }
                            }
                        });
                //update last message and time to Conversation table
                database
                        .getReference("Conversation")
                        .child("Conversation_"+conversationId)
                        .setValue(new FirebaseConversation(conversationId, myId,posterId,getUsername(),posterName, posterAvatar, imageUrl,input.toString(),String.valueOf(new Date().getTime())));
                return true;
            }
        });
    }
    private String id;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @javax.annotation.Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode ==1 && resultCode==-1){
            id = id=="test1" ? "test" : "test1";
            Uri uri = data.getData();
            Author author = new Author(id,"test","https://i.pinimg.com/236x/c1/c8/49/c1c8498d9aec3d4e6c894ddba7882031.jpg");
            Message message = new Message(id,author,new Date(),uri.toString());
            adapter.addToStart(message,true);
        }
    }
}