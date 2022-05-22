package com.example.studentmarket.Controller.Message;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessengerApdater extends RecyclerView.Adapter<MessengerApdater.ViewHolder> {
    private List<Messenger> listMessenger;
    private Context context;

    public MessengerApdater(List<Messenger> listMessenger,Context context) {
        this.listMessenger = listMessenger;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessengerApdater.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messengerView = inflater.inflate(R.layout.messenger, parent, false);
        viewHolder = new MessengerApdater.ViewHolder(messengerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Messenger msgValue = listMessenger.get(position);
        holder.name.setText(msgValue.getName());
//        new DownloadImageTask(holder.avatar).execute(msgValue.getImageUrl());
        Picasso.get().load(msgValue.getImageUrl()).into(holder.avatar);
        holder.msg.setText(msgValue.getMsg());
        holder.time.setText(msgValue.getTime());
        holder.messenger_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context,ListMessages.class);
                myIntent.putExtra("name",msgValue.getName());
                context.startActivity(myIntent);
                Toast.makeText(v.getContext(), msgValue.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMessenger.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        private ImageView avatar;
        private TextView name;
        private TextView msg;
        private TextView time;
        private RelativeLayout messenger_clickable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            time= itemview.findViewById(R.id.chat_time);
            avatar = itemview.findViewById(R.id.chat_avatar);
            name = itemview.findViewById(R.id.chat_user_name);
            msg = itemview.findViewById(R.id.chat_user_new_msg);
            messenger_clickable = itemview.findViewById(R.id.messenger_clickable);
        }
    }
}
