package com.example.studentmarket.Controller.Common;

import static com.example.studentmarket.Constants.StorageKeyConstant.TOKEN_ID_KEY;

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
import com.example.studentmarket.Models.ProductModel;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.ProductService;
import com.example.studentmarket.Store.SharedStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ProductModel> productList;

    public ProductAdapter(Context context, int layout, List<ProductModel> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    public void setItem(List<ProductModel> list) {
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

        if (!storage.getValue(TOKEN_ID_KEY).isEmpty()) {
            holder.heartProduct.setVisibility(ImageView.VISIBLE);
        } else {
            holder.heartProduct.setVisibility(ImageView.INVISIBLE);
        }

        ProductModel product = productList.get(position);
        // holder.imgProduct.setImageResource(product.get());
        Picasso.get().load(product.getListingImage()).into(holder.imgProduct);
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, product.getNameProduct(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, ProductDetail.class);
                myIntent.putExtra("name", product.getListingTitle());
                myIntent.putExtra("price", product.getListingPrice());
                myIntent.putExtra("image", product.getListingImage());
                myIntent.putExtra("body", product.getListingBody());
                myIntent.putExtra("id", product.getListingId());
                myIntent.putExtra("categories", new int[]{1, 3});
                myIntent.putExtra("isHeart", true);
                context.startActivity(myIntent);
            }
        });
        /////
        try {
            productService.CanSaveFavorite(String.valueOf(product.getListingId()), new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    Log.d("cansave", response.toString());
                    if (response.toString() == "true") {
                        holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
                    } else {
                        holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("cansave", error.toString());

                }
            });
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        ////
        holder.heartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (false) {
                    try {
                        productService.SaveFavorite(String.valueOf(product.getListingId()), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("save", response.toString());
                                holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.d("save", error.toString());

                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } else {
                    holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                    try {
                        productService.UnsaveFavorite(String.valueOf(product.getListingId()), new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("unsave", response.toString());
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.d("unsave", error.toString());
                                holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
//                product.setListingIsLoved(!product.getListingIsLoved());
//                Toast.makeText(context,
//                        product.getListingIsLoved() ? "Đã thích " + product.getListingTitle() : "Đã huỷ thích " + product.getListingTitle(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
        if (true) {
            holder.heartProduct.setColorFilter(context.getColor(R.color.secondary));
        } else {
            holder.heartProduct.setColorFilter(context.getColor(R.color.gray));
        }
        holder.nameProduct.setText(product.getListingTitle());
        holder.priceProduct.setText(String.valueOf(product.getListingPrice()) + "VND");

        return convertView;
    }

}
