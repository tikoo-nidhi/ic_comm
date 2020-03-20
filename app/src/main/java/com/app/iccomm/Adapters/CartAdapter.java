package com.app.iccomm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Activity.CartActivity;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.CartModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    List<CartModel> mList = new ArrayList<>();

    public CartAdapter(Context mContext, List<CartModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_recycler_view, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int i) {
        Log.d("abcV", String.valueOf(Integer.parseInt(holder.tv_prodQty.getText().toString()) > 1));
        final DataBaseHandler db = new DataBaseHandler(mContext);
        SharedPreferences preferences = mContext.getSharedPreferences("cur", Context.MODE_PRIVATE);
        final SharedPreferences spf = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);

        holder.tv_prodQty.setText(String.valueOf(mList.get(i).getProd_qty()));
        holder.tv_prodName.setText(mList.get(i).getProd_cart_name());
        holder.tv_prodPrice.setText(preferences.getString(SharedPref.CURRENCY, null) + mList.get(i).getProd_cart_price());
        Glide.with(mContext).load(mList.get(i).getProd_cart_img()).into(holder.imgView_cart);

        Log.d("variant", holder.tv_size.getText() + "," + holder.tv_color.getText());

        if (mList.get(i).getVariant().equals("")) {
            holder.tv_color.setVisibility(View.GONE);
            holder.tv_size.setVisibility(View.GONE);
        } else {
            String variantValue = mList.get(i).getVariant();
            String[] splitString = variantValue.split(",");
            String size;
            String color;
            if (splitString[0].startsWith("S")) {
                Log.d("colorValue", "1");

                size = splitString[0];
                color = splitString[1];
                if (size.equals("Size:") | color.equals("Color:")) {
                    Log.d("colorValue", "1:1");

                    if (size.equals("Size:")) {
                        Log.d("colorValue", "1:1:1");
                        String subString = color.substring(color.indexOf("#"));
                        String[] splitSubString = subString.split(":");
                        color = color.replace(splitSubString[0], "");

                        Log.d("val", color);
                        holder.tv_size.setVisibility(View.GONE);
                        holder.tv_color.setText(color);
                    } else {
                        Log.d("colorValue", "1:1:2");

                        holder.tv_color.setVisibility(View.GONE);
                        holder.tv_size.setText(size);
                    }
                } else {
                    Log.d("colorValue", "1:2");
                    String subString = color.substring(color.indexOf("#"));
                    String[] splitSubString = subString.split(":");
                    color = color.replace(splitSubString[0], "");

                    Log.d("val", color);
                    holder.tv_size.setText(size);
                    holder.tv_color.setText(color);
                }


            } else {
                Log.d("colorValue", "2");
                color = splitString[0];
                size = splitString[1];
                if (size.equals("Size:") | color.equals("Color:")) {
                    Log.d("colorValue", "2:1");

                    if (size.equals("Size:")) {
                        Log.d("colorValue", "2:1:1");

                        String subString = color.substring(color.indexOf("#"));
                        String[] splitSubString = subString.split(":");
                        color = color.replace(splitSubString[0], "");

                        Log.d("val", color);
                        holder.tv_size.setVisibility(View.GONE);
                        holder.tv_color.setText(color);
                    } else {
                        Log.d("colorValue", "2:1:2");

                        holder.tv_color.setVisibility(View.GONE);
                        holder.tv_size.setText(size);
                    }
                } else {
                    Log.d("colorValue", "2:2");

                    String subString = color.substring(color.indexOf("#"));
                    String[] splitSubString = subString.split(":");
                    color = color.replace(splitSubString[0], "");

                    Log.d("val", color);
                    holder.tv_size.setText(size);
                    holder.tv_color.setText(color);
                }


            }


        }
        holder.bt_removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCartItem(mList.get(i).getProd_cart_id(), mList.get(i).getVariant());
                holder.cart_cardView.setVisibility(View.GONE);
            }
        });

        holder.bt_moveToWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLogged = spf.getBoolean(SharedPref.IS_LOGGED, false);
                if (isLogged) {
                    addToWishList(mList.get(i).getProd_cart_id(), mList.get(i).getVariant());
                } else {
                    new AlertDialog.Builder(mContext)
                            .setMessage("You Need to Login First")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });


//        holder.imgView_deleteFromCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteValuesFromCart(mList.get(i).getProd_cart_id());
//                Animation leftExit = AnimationUtils.loadAnimation(mContext, R.anim.right_exit);
//                holder.cart_cardView.startAnimation(leftExit);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.cart_cardView.setVisibility(View.GONE);
//                    }
//                }, 500);

//                final ProgressDialog progressDialog = new ProgressDialog(mContext);
//                progressDialog.setTitle("Updating Your Basket");
//                progressDialog.setMessage("Loading...");
//                progressDialog.show();
//                ((Activity) mContext).finish();
//                mContext.startActivity(new Intent(mContext, CartActivity.class));
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                    }
//                }, 1000);

//            }
//        });
//        holder.imgView_wishList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.cart_cardView.setVisibility(View.GONE);
//            }
//        });

//        if (holder.tv_prodQty.getText().toString().equals("1")){
//            holder.imgView_descQty.setEnabled(false);
//        }else {
//            holder.imgView_descQty.setEnabled(true);
//        }
//        if (holder.tv_prodQty.getText().toString().equals("1")) {
//            holder.imgView_descQty.setVisibility(View.GONE);
//        }
        holder.imgView_descQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(holder.tv_prodQty.getText().toString());
                if (holder.tv_prodQty.getText().toString().equals("1")) {
                    holder.imgView_descQty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_grey_cart));
                } else {
                    qty = Integer.parseInt(holder.tv_prodQty.getText().toString()) - 1;
                }
                holder.tv_prodQty.setText(String.valueOf(qty));
            }
        });
        holder.imgView_incQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(holder.tv_prodQty.getText().toString()) + 1;
                if (qty > 1) {
                    holder.imgView_descQty.setVisibility(View.VISIBLE);
                    holder.imgView_descQty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_black_cart));

                } else {
//                    holder.imgView_descQty.setVisibility(View.GONE);
                }
                holder.tv_prodQty.setText(String.valueOf(qty));
            }
        });

        holder.tv_subTotalAmount.setText("Sub Total: " + preferences.getString(SharedPref.CURRENCY, null) + " " + (Float.parseFloat(mList.get(i).getProd_cart_price()) * Float.parseFloat(mList.get(i).getProd_qty())));


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        CardView cart_cardView;
        ImageView imgView_cart, imgView_wishList, imgView_incQty, imgView_descQty;
        TextView tv_prodPrice, tv_prodName, tv_subTotalAmount, tv_prodQty;
        TextView bt_removeFromCart, bt_moveToWish, tv_size, tv_color;
//        LinearLayout deleteCartItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_cardView = itemView.findViewById(R.id.cart_cardView);
            imgView_cart = itemView.findViewById(R.id.imgView_cart);
            imgView_wishList = itemView.findViewById(R.id.imgView_wishList);
//            imgView_deleteFromCart = itemView.findViewById(R.id.imgView_deleteFromCart);
            imgView_incQty = itemView.findViewById(R.id.imgView_incQty);
            imgView_descQty = itemView.findViewById(R.id.imgView_descQty);
            tv_prodPrice = itemView.findViewById(R.id.tv_prodPrice);
            tv_prodName = itemView.findViewById(R.id.tv_prodName);
            tv_subTotalAmount = itemView.findViewById(R.id.tv_subTotalAmount);
            tv_prodQty = itemView.findViewById(R.id.tv_prodQty);
            bt_removeFromCart = itemView.findViewById(R.id.bt_removeFromCart);
            bt_moveToWish = itemView.findViewById(R.id.bt_moveToWish);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_color = itemView.findViewById(R.id.tv_color);
//            deleteCartItem = itemView.findViewById(R.id.deleteCartItem);
        }
    }

    public void deleteCartItem(final String prod_id, final String variant) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_DELETE_CART_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseDeleteCart", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(mContext, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        ((Activity) mContext).finish();
                        mContext.startActivity(new Intent(mContext, CartActivity.class));
                    } else {
                        Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        Log.d("responseDeleteCart", "error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d("responseDeleteCart", String.valueOf(e));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d("responseDeleteCart", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
                Boolean isLogged = sharedPreferences.getBoolean(SharedPref.IS_LOGGED, false);
//                params.put("action", "removeCartItem");
                params.put("id", prod_id);

                if (isLogged) {
                    params.put("uid", sharedPreferences.getString(SharedPref.USER_ID, null));
                } else {
                    SharedPreferences spf = mContext.getSharedPreferences("udid", Context.MODE_PRIVATE);
                    params.put("uid", spf.getString(SharedPref.PHONE_ID, null));
                }
                params.put("variant", variant);
                Log.d("paramsDeleteCart", String.valueOf(params));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    public void addToWishList(final String p_id, final String variant) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_WISH_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseWish", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(mContext, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        deleteCartItem(p_id, variant);
                    } else {
                        Toast.makeText(mContext, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
                params.put("user_id", sharedPreferences.getString(SharedPref.USER_ID, null));
                params.put("product_id", p_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }


}
