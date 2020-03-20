package com.app.iccomm.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Activity.WishListActivity;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.WishListModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {
    Context mContext;
    List<WishListModel> mList = new ArrayList<>();

    public WishListAdapter(Context mContext, List<WishListModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.products_recycler_view, viewGroup, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WishListViewHolder holder, final int i) {
        if (mList.get(i).getDiscount().equals("0")) {
            holder.tv_prodActualPrice_1.setVisibility(View.GONE);
            holder.tv_prodDiscountPrice_1.setText(mContext.getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + mList.get(i).getProdActualPrice());
            holder.tv_discount_percentage.setVisibility(View.GONE);
        } else {
            holder.tv_prodActualPrice_1.setPaintFlags(holder.tv_prodActualPrice_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_prodActualPrice_1.setText(mContext.getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + mList.get(i).getProdActualPrice());
            if (mList.get(i).getDiscountType().equals("Flat")) {
                holder.tv_discount_percentage.setText(mContext.getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null)
                        + mList.get(i).getDiscount() + "\noff");
            } else {
                holder.tv_discount_percentage.setText(mList.get(i).getDiscount() + "%\noff");
            }
            holder.tv_prodDiscountPrice_1.setText(mContext.getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + mList.get(i).getProdDiscountedPrice());

        }

        holder.imgView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromWish(String.valueOf(mList.get(i).getProd_id()));

            }
        });

        holder.bt_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(String.valueOf(mList.get(i).getProd_id()));

            }
        });

        holder.frameLayoutWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("prodId",String.valueOf(mList.get(i).getProd_id()));
                    mContext.startActivity(new Intent(mContext,ProductDetails.class).putExtra("prodId",String.valueOf(mList.get(i).getProd_id())));
            }
        });


//        final DataBaseHandler db = new DataBaseHandler(mContext);
//        holder.ids.setText(String.valueOf(mList.get(i).getProd_id()));
//        holder.imgView_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteWisListEntry(mList.get(i).getProd_id());
//                final Dialog dialog = new Dialog(mContext);
//                dialog.setContentView(R.layout.progress_bar_layout);
//                dialog.setCancelable(true);
//                dialog.show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.frameLayoutWish.setVisibility(View.GONE);
//                        ((Activity) mContext).finish();
//                        mContext.startActivity(new Intent(mContext, WishListActivity.class));
//                    }
//                }, 500);
//            }
//        });
//
//        holder.bt_addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.frameLayoutWish.setVisibility(View.GONE);
//            }
//        });
//
        Glide.with(mContext).load(mList.get(i).getProd_img()).into(holder.imgView_prodImg_1);
//        Picasso.with(mContext).load(mList.get(i).getProd_img())
//        holder.tv_prodName_1.setText(mList.get(i).getProdName());
//        holder.tv_prodPrice_1.setText(mList.get(i).getProdPrice());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearProduct_1;
        ImageView imgView_prodImg_1, imgView_prod_wish_1, imgView_cancel;
        TextView tv_prodSeller_1, tv_prodName_1, tv_prodRating_1, ids, wishId, tv_discount_percentage, tv_prodDiscountPrice_1, tv_prodActualPrice_1;
        Button bt_addToCart;
        FrameLayout frameLayoutWish;
        CheckedTextView checkedTextView;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayoutWish = itemView.findViewById(R.id.frameLayoutWish);
            linearProduct_1 = itemView.findViewById(R.id.linearProduct_1);
            imgView_prodImg_1 = itemView.findViewById(R.id.imgView_prodImg_1);
            imgView_prod_wish_1 = itemView.findViewById(R.id.imgView_prod_wish_1);
            tv_prodSeller_1 = itemView.findViewById(R.id.tv_prodSeller_1);
            tv_prodName_1 = itemView.findViewById(R.id.tv_prodName_1);
            tv_prodDiscountPrice_1 = itemView.findViewById(R.id.tv_prodDiscountPrice_1);
            tv_prodActualPrice_1 = itemView.findViewById(R.id.tv_prodActualPrice_1);
            tv_prodRating_1 = itemView.findViewById(R.id.tv_prodRating_1);
            imgView_cancel = itemView.findViewById(R.id.imgView_cancel);
            bt_addToCart = itemView.findViewById(R.id.bt_addToCart);
            ids = itemView.findViewById(R.id.ids);
            wishId = itemView.findViewById(R.id.wish_id);
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
            tv_discount_percentage = itemView.findViewById(R.id.tv_discount_percentage);

            imgView_prod_wish_1.setVisibility(View.GONE);
            tv_prodSeller_1.setVisibility(View.GONE);
            tv_prodRating_1.setVisibility(View.GONE);
            checkedTextView.setVisibility(View.GONE);
        }
    }

    public void deleteFromWish(final String p_id) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_DELETE_WISH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deleteWishResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")){
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity) mContext).finish();
                                mContext.startActivity(new Intent(mContext, WishListActivity.class));
                            }
                        }, 500);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("user_id", mContext.getSharedPreferences("data", MODE_PRIVATE).getString(SharedPref.USER_ID, null));
                params.put("product_id", p_id);
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }


    public void addToCart(final String p_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseCart", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(mContext, "Item Added To Cart", Toast.LENGTH_SHORT).show();
                        deleteFromWish(p_id);

//                        snackBar("Item Added To Cart", false);
                    } else {
//                        snackBar("Item Not Added To Cart", true);
                        Toast.makeText(mContext, "Item Not Added To Cartt", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    snackBar("Something Went Wrong Come Back Later", true);
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                snackBar("Something Went Wrong Come Back Later", true);
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences spf = mContext.getSharedPreferences("data", MODE_PRIVATE);
                params.put("uid", spf.getString(SharedPref.USER_ID, null));
                params.put("id", p_id);
                params.put("qty", "1");
                params.put("variant", "");

                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
