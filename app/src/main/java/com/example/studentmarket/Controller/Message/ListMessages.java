package com.example.studentmarket.Controller.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.R;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;


public class ListMessages extends AppCompatActivity {
    private MessagesList messagesList;
    private MessageInput inputView;
    private ImageButton goBackButton;
    private TextView listMessagesChatName;
    private MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int height = displayMetrics.heightPixels;
//                int width = displayMetrics.widthPixels;
//                imageView.getLayoutParams().width = (int) (width*0.8);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.get().load(url).into(imageView);
            }
        };

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        listMessagesChatName = findViewById(R.id.list_messages_name);
        listMessagesChatName.setText(name);
        listMessagesChatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListMessages.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
        String senderId = "test1";
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
    }

    private void initViews(MessagesListAdapter<Message> adapter) {
        inputView = findViewById(R.id.message_input);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                Author author = new Author("test","test","https://i.pinimg.com/236x/c1/c8/49/c1c8498d9aec3d4e6c894ddba7882031.jpg");
                Message message = new Message("test", input.toString(),author,new Date());
                //validate and send message
                adapter.addToStart(message, true);
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