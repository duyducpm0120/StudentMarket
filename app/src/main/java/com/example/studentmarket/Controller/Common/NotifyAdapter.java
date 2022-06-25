package com.example.studentmarket.Controller.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.studentmarket.Controller.Message.ListMessages;
import com.example.studentmarket.Controller.Message.Messenger;
import com.example.studentmarket.Controller.Message.MessengerApdater;
import com.example.studentmarket.Helper.DownloadImageTask.DownloadImageTask;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.NotifyService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder>{
    private List<NotifyClass> listNotify;
    private Context context;
    private NotifyService notifyService;

    public NotifyAdapter(List<NotifyClass> listNotify, Context context) {
        this.listNotify = listNotify;
        this.context = context;
        notifyService = new NotifyService(context);
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

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotifyClass ntf = listNotify.get(position);
        holder.notify_text.setText(Html.fromHtml("<b>"+ntf.getUser_name()+"</b>"+ntf.getBody(),Html.FROM_HTML_MODE_COMPACT));
        if (ntf.isRead()){
            holder.notify_clickable.setBackgroundColor(android.R.color.white);
        }
//        new DownloadImageTask(holder.notify_image).execute(ntf.getImage());
        Picasso.get().load(ntf.getImage()).into(holder.notify_image);
        holder.notify_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ntf.isRead()){
                    holder.notify_clickable.setBackgroundColor(android.R.color.white);
                    ntf.setRead(true);
                    try {
                        notifyService.markNotificationAsRead(ntf.getId(), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {

                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
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
