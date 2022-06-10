package com.example.studentmarket.Controller.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmarket.R;
import static com.example.studentmarket.Helper.globalValue.*;
import com.example.studentmarket.Component.categoryInterface;
import com.squareup.picasso.Picasso;


import java.util.List;

public class typeAdapter extends RecyclerView.Adapter<typeAdapter.ViewHolder> {
    private List<type> typeList;
    private int type;
    private categoryInterface callback;

    public typeAdapter(List<type> typeList, int type,categoryInterface callback) {
        this.typeList = typeList;
        this.type = type;
        this.callback = callback;
    }

    public void setItem(List<type> typeList){
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View typeView = inflater.inflate(R.layout.type, parent, false);
        if (type == 2) typeView = inflater.inflate(R.layout.type2, parent, false);
        viewHolder = new ViewHolder(typeView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        int index =position;
        type typeValue = typeList.get(position);
        holder.name.setText(typeValue.getName());
        Picasso.get().load(typeValue.getImage()).into(holder.img);

        if (type == 1){
            if (typeValue.isShow()==true){
                holder.imageLayout.setVisibility(View.VISIBLE);
            }
            else {
                holder.imageLayout.setVisibility(View.INVISIBLE);
            }
        }
        holder.type_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != 2) {
                    holder.imageLayout.setVisibility(View.VISIBLE);
                    typeValue.setShow(true);
                    if (getIndex() > 0 && getIndex() != index) {
                        typeList.get(getIndex()).setShow(false);
                    }
                    notifyItemChanged(getIndex());
                    setIndex(index);
                }
                callback.action(typeValue.getId());
            }
        });
        if (holder.imageLayout !=null){
            holder.imageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.imageLayout.setVisibility(View.INVISIBLE);
                    typeValue.setShow(false);
                    callback.action(-1);
                }
            });
            if (typeValue.isShow()==true){
                holder.imageLayout.setVisibility(View.VISIBLE);
            }
            else {
                holder.imageLayout.setVisibility(View.INVISIBLE);
            }
            if (getIndex()!=position){
                holder.imageLayout.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public ImageView img;
        public TextView name;
        public FrameLayout type_clickable;
        public ImageView imageLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            name = itemView.findViewById(R.id.typeText);
            img = itemView.findViewById(R.id.typeImage);
            imageLayout = itemView.findViewById(R.id.type_image_layout);
            type_clickable = itemView.findViewById(R.id.type_frame_layout);
            if (type == 2) {
                name = itemView.findViewById(R.id.type2_textview_name);
                img = itemView.findViewById(R.id.type2_imageview);
                type_clickable = itemView.findViewById(R.id.type2_clickable);
                imageLayout = null;
            }
        }
    }
}