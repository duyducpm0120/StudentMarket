package com.example.studentmarket.Controller.Common;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmarket.R;
import static com.example.studentmarket.Helper.globalValue.*;
import com.example.studentmarket.Component.categoryInterface;
import com.squareup.picasso.Picasso;


import java.util.List;

public class typeAdapter extends RecyclerView.Adapter<typeAdapter.ViewHolder> {
    private List<CategoryType> categoryTypeList;
    private int type;
    private categoryInterface callback;

    public typeAdapter(List<CategoryType> categoryTypeList, int type, categoryInterface callback) {
        this.categoryTypeList = categoryTypeList;
        this.type = type;
        this.callback = callback;
    }

    public void setItem(List<CategoryType> categoryTypeList){
        this.categoryTypeList = categoryTypeList;
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
        CategoryType categoryTypeValue = categoryTypeList.get(position);
        holder.name.setText(categoryTypeValue.getName());
        Picasso.get().load(categoryTypeValue.getImage()).into(holder.img);
////            if (categoryTypeValue.isShow()){
////                holder.imageLayout.setVisibility(View.VISIBLE);
////            }
////            else {
////                holder.imageLayout.setVisibility(View.INVISIBLE);
////            }
//        holder.imageLayout.setVisibility(View.VISIBLE);
        holder.type_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != 2) {
                    holder.imageLayout.setVisibility(View.VISIBLE);
                    categoryTypeValue.setShow(true);
                    if (getIndex() > 0 && getIndex() != index) {
                        categoryTypeList.get(getIndex()).setShow(false);
                    }
                    notifyItemChanged(getIndex());
                    setIndex(index);
                }

                callback.action(categoryTypeValue.getId());
            }
        });
            holder.imageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==1){
                        holder.imageLayout.setVisibility(View.INVISIBLE);
                        categoryTypeValue.setShow(false);
                        callback.action(-1);
                    }
                }
            });
            if (categoryTypeValue.isShow()){
                holder.imageLayout.setVisibility(View.VISIBLE);
            }
            else {
                holder.imageLayout.setVisibility(View.INVISIBLE);
            }
            if (getIndex()!=position && type == 1){
                holder.imageLayout.setVisibility(View.INVISIBLE);
            }

    }

    @Override
    public int getItemCount() {
        return categoryTypeList.size();
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
                imageLayout = itemView.findViewById(R.id.type2_image_layout);
            }
        }
    }
}