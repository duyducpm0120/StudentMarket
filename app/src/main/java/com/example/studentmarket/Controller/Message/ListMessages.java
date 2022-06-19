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
    private String myId="1";
    private String conversationId;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        conversationRef = database.getReference("Conversation");
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        conversationId = intent.getStringExtra("id");
        String imageUrl = intent.getStringExtra("imageUrl");

        if (conversationId == null) {
            final String[] newConversationId = {"1"};
            conversationRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FirebaseConversation conversation = dataSnapshot.getValue(FirebaseConversation.class);
                        newConversationId[0] = conversation.getId();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//            conversationRef.push().setValue(new FirebaseConversation(newConversationId[0], myId,, name, imageUrl));
        }

        listMessagesChatName = findViewById(R.id.list_messages_name);
        listMessagesChatName.setText(name);
        if (getUserId() != null) {
            myId = getUserId();
        }
        senderAuthor = new Author(myId,"test","https://i.pinimg.com/236x/c1/c8/49/c1c8498d9aec3d4e6c894ddba7882031.jpg");
        receiverAuthor = new Author("receiverId",name,"https://i.pinimg.com/236x/c1/c8/49/c1c8498d9aec3d4e6c894ddba7882031.jpg");
        mesageRef = database.getReference("Message_"+conversationId);
        listMessagesChatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListMessages.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
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
        messagesList.setAdapter(adapter);
        initViews(adapter);
        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),1);
            }
        });
        goBackButton = findViewById(R.id.chat_goback_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListMessages.this, "back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        String finalMyId = myId;
        mesageRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                if (task.getResult().getValue()==null){
                    isFirstAccess = false;
                }
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        FirebaseMessage message = dataSnapshot.getValue(FirebaseMessage.class);
                        Log.d("firebase", message.getAuthorId()+" "+finalMyId);

                        if (message.getAuthorId().equals(finalMyId)) {
                            adapter.addToStart(new Message(id,message.getMsg(),senderAuthor,message.getDate()), true);
                        } else {
                            adapter.addToStart(new Message(id,message.getMsg(),receiverAuthor,message.getDate()), true);
                        }
                    }
                }
            }
        });

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
                            isFirstAccess = false;
                        }
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
                Message message = new Message("abc", input.toString(),senderAuthor,new Date());
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                //validate and send message
                adapter.addToStart(message, true);
                FirebaseDatabase.getInstance()
                        .getReference("Message_"+conversationId)
                        .push()
                        .setValue(new FirebaseMessage(conversationId,input.toString(),"name1",myId,new Date()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("firebase", "success");
                                } else {
                                    Log.d("firebase", "failed");
                                }
                            }
                        });
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