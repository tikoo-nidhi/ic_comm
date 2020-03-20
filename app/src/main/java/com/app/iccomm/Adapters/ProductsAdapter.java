package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.WishListModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context mContext;
    List<ProductsModel> listModel = new ArrayList<>();
    String prod_id;
    int j = 0;
    boolean checked = false;
    WishListModel wishListModel = new WishListModel();
    Boolean isLogged;


    public ProductsAdapter(Context mContext, List<ProductsModel> listModel) {
        this.mContext = mContext;
        this.listModel = listModel;
    }



    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_recycler_view, viewGroup, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsViewHolder holder, final int i) {
        final SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED, false);

        Log.d("prodDiscount",listModel.get(i).getProdDiscount());

        holder.ids.setText(listModel.get(i).getProdId());
        holder.tv_prodName_1.setText(listModel.get(i).getProdName());

        if (listModel.get(i).getProdStars().equals("null")){
            holder.tv_prodRating_1.setVisibility(View.GONE);
        }else {
            holder.tv_prodRating_1.setText(listModel.get(i).getProdStars());
        }

        if (listModel.get(i).getProdDiscount().equals("\u20B90")||listModel.get(i).getProdDiscount().startsWith("0")){
            holder.tv_prodActualPrice_1.setVisibility(View.GONE);
            holder.tv_discount_percentage.setVisibility(View.GONE);
            holder.tv_prodDiscountPrice_1.setText(listModel.get(i).getProdActualPrice());
        }else {
            holder.tv_prodActualPrice_1.setPaintFlags(holder.tv_prodActualPrice_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_prodActualPrice_1.setText(listModel.get(i).getProdActualPrice());
            holder.tv_discount_percentage.setText(listModel.get(i).getProdDiscount()+"\noff");
            holder.tv_prodDiscountPrice_1.setText(listModel.get(i).getProdDiscountedPrice());
        }
        Glide.with(mContext).load(listModel.get(i).getProdImg()).into(holder.imgView_prodImg_1);
        holder.linearProduct_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductDetails.class);
                mIntent.putExtra("prod_id", String.valueOf(listModel.get(i).getProdId()));
                Log.d("prod_id", String.valueOf(listModel.get(i).getProdId()));
                mContext.startActivity(mIntent);
            }
        });

        holder.linearProduct_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext,ProductDetails.class);
                mIntent.putExtra("prodId",listModel.get(i).getProdId());
                mContext.startActivity(mIntent);
            }
        });




//        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.checkedTextView.isChecked()) {
//                    if (isLogged) {
//                        addWishList("delete", holder.ids.getText().toString());
//                    } else {
//                        db.deleteWisListEntry(listModel.get(i).getProdId());
//                    }
//                    holder.checkedTextView.setCheckMarkDrawable(R.drawable.ic_wishlist_hollow);
//                    holder.checkedTextView.setChecked(false);
//                    Snackbar.make(v, "Item Removed From Wishlist", Snackbar.LENGTH_LONG).show();
//                } else {
//                    if (isLogged) {
//                        addWishList("add", holder.ids.getText().toString());
//                        holder.checkedTextView.setCheckMarkDrawable(R.drawable.ic_wishlist_fill);
//                        holder.checkedTextView.setChecked(true);
//                    } else {
//                        wishListModel.setProd_id(listModel.get(i).getProdId());
//                        wishListModel.setProd_img(listModel.get(i).getProdImg());
//                        wishListModel.setProdName(listModel.get(i).getProdName());
//                        wishListModel.setProdPrice(listModel.get(i).getProdPrice());
//                        db.addToWishList(wishListModel);
//                    }
//                        holder.checkedTextView.setCheckMarkDrawable(R.drawable.ic_wishlist_fill);
//                        holder.checkedTextView.setChecked(true);
//
//                    Snackbar.make(v, "Item Added To Wishlist", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }


    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearProduct_1;
        ImageView imgView_prodImg_1, imgView_cancel;
        TextView  tv_prodName_1,  tv_prodRating_1, ids,tv_discount_percentage,tv_prodDiscountPrice_1, tv_prodActualPrice_1;
        Button bt_addToCart;
        CheckedTextView checkedTextView;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            linearProduct_1 = itemView.findViewById(R.id.linearProduct_1);
            imgView_prodImg_1 = itemView.findViewById(R.id.imgView_prodImg_1);
            tv_prodName_1 = itemView.findViewById(R.id.tv_prodName_1);
            tv_prodDiscountPrice_1 = itemView.findViewById(R.id.tv_prodDiscountPrice_1);
            tv_prodActualPrice_1 = itemView.findViewById(R.id.tv_prodActualPrice_1);
            tv_prodRating_1 = itemView.findViewById(R.id.tv_prodRating_1);
            imgView_cancel = itemView.findViewById(R.id.imgView_cancel);
            bt_addToCart = itemView.findViewById(R.id.bt_addToCart);
            tv_discount_percentage = itemView.findViewById(R.id.tv_discount_percentage);
            ids = itemView.findViewById(R.id.ids);

            bt_addToCart.setVisibility(View.GONE);
            imgView_cancel.setVisibility(View.GONE);

        }
    }

//    private void addWishList(final String action, final String prod_id) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_WISH_LIST, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("wishRes", response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
//                Map<String, String> params = new HashMap<>();
//                params.put("action", action);
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                params.put("product_id", prod_id);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//        requestQueue.add(stringRequest);
//    }
}
