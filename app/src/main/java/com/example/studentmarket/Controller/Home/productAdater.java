package com.example.studentmarket.Controller.Home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmarket.R;

import java.util.List;

public class productAdater extends BaseAdapter {
    private Context context;
    private int layout;
    private List<product> productList;

    public productAdater(Context context, int layout, List<product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgProduct;
        TextView nameProduct;
        TextView priceProduct;
        ImageButton heartProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null );
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.image_product);
            holder.nameProduct = (TextView) convertView.findViewById(R.id.name_product);
            holder.priceProduct = (TextView) convertView.findViewById(R.id.price_product);
            holder.heartProduct = (ImageButton) convertView.findViewById(R.id.product_heart);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        product product = productList.get(position);
        holder.imgProduct.setImageResource(product.getImageProduct());
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, product.getNameProduct(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.heartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!product.isHeartProduct()){
                holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
                }
                else {
                    holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                }
                product.setHeartProduct(!product.isHeartProduct());
                Toast.makeText(context, product.isHeartProduct() ? "Đã thích "+product.getNameProduct() : "Đã huỷ thích "+product.getNameProduct(), Toast.LENGTH_SHORT).show();
            }
        });
        if (product.isHeartProduct()){
            holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
        }
        else {
            holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
        }
        holder.nameProduct.setText(product.getNameProduct());
        holder.priceProduct.setText(product.getPriceProduct());

        return convertView;
    }
}
