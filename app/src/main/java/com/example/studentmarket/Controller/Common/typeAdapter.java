package com.example.studentmarket.Controller.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmarket.R;

import java.util.List;

public class typeAdapter extends RecyclerView.Adapter<typeAdapter.ViewHolder> {
    private List<type> typeList;
    private int type;

    public typeAdapter(List<type> typeList,int type) {
        this.typeList = typeList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View typeView = inflater.inflate(R.layout.type, parent, false);
        if (type == 2) typeView = inflater.inflate(R.layout.type2,parent,false);
        viewHolder = new ViewHolder(typeView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        type type = typeList.get(position);
        holder.name.setText(type.getName());
        holder.img.setImageResource(type.getImage());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public ImageView img;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            name = itemView.findViewById(R.id.typeText);
            img = itemView.findViewById(R.id.typeImage);
            if (type == 2){
                name = itemView.findViewById(R.id.type2_textview_name);
                img = itemView.findViewById(R.id.type2_imageview);
            }

//            Xử lý khi nút Chi tiết được bấm
//            detail_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            studentname.getText() +" | "
//                                    + " Demo function", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            });
        }
    }
}