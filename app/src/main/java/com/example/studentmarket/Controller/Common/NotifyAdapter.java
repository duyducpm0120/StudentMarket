package com.example.studentmarket.Controller.Common;

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

import com.example.studentmarket.Controller.Message.ListMessages;
import com.example.studentmarket.Controller.Message.Messenger;
import com.example.studentmarket.Controller.Message.MessengerApdater;
import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder>{
    private List<NotifyClass> listNotify;
    private Context context;

    public NotifyAdapter(List<NotifyClass> listNotify, Context context) {
        this.listNotify = listNotify;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotifyAdapter.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View notifyView = inflater.inflate(R.layout.notify, parent, false);
        viewHolder = new NotifyAdapter.ViewHolder(notifyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotifyClass ntf = listNotify.get(position);
        holder.notify_text.setText(ntf.getBody());
//        new DownloadImageTask(holder.notify_image).execute(ntf.getImage());
        Picasso.get().load(ntf.getImage()).into(holder.notify_image);
        holder.notify_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ntf.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNotify.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView notify_image;
        private TextView notify_text;
        private RelativeLayout notify_clickable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notify_image = itemView.findViewById(R.id.notify_image);
            notify_text = itemView.findViewById(R.id.notify_body);
            notify_clickable = itemView.findViewById(R.id.notify_clickable);
        }
    }
}
