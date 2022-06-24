package com.example.studentmarket.Controller.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.example.studentmarket.Store.SharedStorage;
import com.squareup.picasso.Picasso;

import java.util.List;
import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

import org.json.JSONException;
import org.json.JSONObject;


public class productAdater extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> productList;

    public productAdater(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    public void clear() {
        productList.clear();
        notifyDataSetChanged();
    }

    public void setItem(List<Product> list){
        this.productList = list;
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

    private class ViewHolder {
        ImageView imgProduct;
        TextView nameProduct;
        TextView priceProduct;
        ImageButton heartProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SharedStorage storage = new SharedStorage(context);
        ProductService productService = new ProductService(context);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.image_product);
            holder.nameProduct = (TextView) convertView.findViewById(R.id.name_product);
            holder.priceProduct = (TextView) convertView.findViewById(R.id.price_product);
            holder.heartProduct = (ImageButton) convertView.findViewById(R.id.product_heart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()){
            holder.heartProduct.setVisibility(ImageView.VISIBLE);
        } else {
            holder.heartProduct.setVisibility(ImageView.INVISIBLE);
        }

        Product product = productList.get(position);
        // holder.imgProduct.setImageResource(product.get());
        Picasso.get().load(product.getImage()).fit().into(holder.imgProduct);
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, product.getNameProduct(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, ProductDetail.class);
                myIntent.putExtra("name", product.getTitle());
                myIntent.putExtra("price", product.getPrice());
                myIntent.putExtra("image", product.getImage());
                myIntent.putExtra("body", product.getBody());
                myIntent.putExtra("id",product.getId());
                myIntent.putExtra("isHeart",product.isHeart());
                context.startActivity(myIntent);
            }
        });
        holder.heartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!product.isHeart()) {
                    try {
                        productService.SaveFavorite(String.valueOf(product.getId()), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("save",response.toString());
                                holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.d("save",error.toString());

                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } else {
                    holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                    try {
                        productService.UnsaveFavorite(String.valueOf(product.getId()), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("unsave",response.toString());
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.d("unsave",error.toString());
                                holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                product.setHeart(!product.isHeart());
                Toast.makeText(context,
                        product.isHeart() ? "Đã thích " + product.getTitle() : "Đã huỷ thích " + product.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        if (product.isHeart()) {
            holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
        } else {
            holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
        }
        holder.nameProduct.setText(product.getTitle());
        String formatPrice = String.format("%,d", Integer.parseInt(product.getPrice()))+" đ";
        holder.priceProduct.setText(formatPrice);

        return convertView;
    }

}
