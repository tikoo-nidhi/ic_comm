package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.app.iccomm.Adapters.WishListAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.WishListModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishListActivity extends AppCompatActivity {
    RecyclerView wishList_recyclerView;
    ImageView left_arrow_appBar, imgView_shoppingCart, imgView_dot;
    DataBaseHandler db = new DataBaseHandler(WishListActivity.this);
    List<WishListModel> mListWish = new ArrayList<>();
    TextView title;
    SharedPreferences preferences;
    //    WishListModel wishListModel = new WishListModel();
    LinearLayout emptyWishLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((WishListActivity.this)).getSupportActionBar().setTitle("");

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        wishList_recyclerView = findViewById(R.id.wishList_recyclerView);
        title = findViewById(R.id.title);
        emptyWishLay = findViewById(R.id.emptyWishLay);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        emptyWishLay.setVisibility(View.GONE);

        if (preferences.getBoolean(SharedPref.IS_LOGGED,false)){
            getWishList();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SharedPref.INTENT_VALUE,"WishList");
            editor.commit();
            finish();
            startActivity(new Intent(WishListActivity.this,LoginSignUpPageActivity.class));
        }

//        if (db.getWishListTable().size() > 0) {
//            emptyWishLay.setVisibility(View.GONE);
//        } else {
//            emptyWishLay.setVisibility(View.VISIBLE);
//        }

//        if (preferences.getBoolean(SharedPref.IS_LOGGED, false)) {
////            getWishList();
////        } else {
////            List<WishListModel> mList = db.getWishListTable();
////            for (int i = 0; i < mList.size(); i++) {
////                WishListModel mModel = mList.get(i);
////                final WishListModel mModel1 = new WishListModel();
////                mModel1.setProd_id(mModel.getProd_id());
////                mModel1.setProd_img(mModel.getProd_img());
////                mModel1.setProdName(mModel.getProdName());
////                mModel1.setProdPrice(mModel.getProdPrice());
////                wishListModels.add(mModel1);
////                initRecyclerView();
////            }
////        }


        title.setText("Wishlist");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgView_shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WishListActivity.this, CartActivity.class));
            }
        });


    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(WishListActivity.this, 2);
        WishListAdapter wishListAdapter = new WishListAdapter(WishListActivity.this, mListWish);
        wishList_recyclerView.setLayoutManager(layoutManager);
        wishList_recyclerView.setAdapter(wishListAdapter);
    }

    public void getWishList() {
        final Dialog dialog = new Dialog(WishListActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_WISH_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("wishResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {
                        if (jsonObject.optString("status").equals("false")) {
                            emptyWishLay.setVisibility(View.VISIBLE);
                        }
                    } else {
                        JSONObject products = jsonObject.getJSONObject("products");
                        JSONArray data = products.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            WishListModel wishListModel = new WishListModel();
                            wishListModel.setProd_id(object.optInt("id"));
                            wishListModel.setProdName(object.optString("name"));
                            wishListModel.setProdActualPrice(object.optString("price"));
                            wishListModel.setProdDiscountedPrice(object.optString("discounted_price"));
                            wishListModel.setDiscountType(object.optString("discount_type"));
                            wishListModel.setDiscount(object.optString("discount"));
                            wishListModel.setProd_img(object.optString("image"));
                            mListWish.add(wishListModel);
                            initRecyclerView();
                        }
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(WishListActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WishListActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(WishListActivity.this);
        queue.add(stringRequest);
    }
}
