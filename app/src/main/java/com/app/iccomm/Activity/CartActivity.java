package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.app.iccomm.Adapters.CartAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Model.CartModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    RecyclerView cart_recyclerView;
    Button bt_placeOrder;
    ImageView left_arrow_appBar, imgView_shoppingCart;
    TextView title, tv_totalMRP, tv_totalPayment, tv_total_amt_paid;
    SharedPreferences preferences;
    Boolean isLogged;
    DataBaseHandler db = new DataBaseHandler(CartActivity.this);
    List<CartModel> mList = new ArrayList<>();
    Dialog dialog;
    AddressModel addressModel = new AddressModel();
    FrameLayout emptyBasketLay;
    Float totalAmount = 0f;
    LinearLayout ll_priceDetails, bottomLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart_recyclerView = findViewById(R.id.cart_recyclerView);
        bt_placeOrder = findViewById(R.id.bt_placeOrder);
        title = findViewById(R.id.title);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((CartActivity.this)).getSupportActionBar().setTitle("");

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        emptyBasketLay = findViewById(R.id.emptyBasketLay);
        tv_totalMRP = findViewById(R.id.tv_totalMRP);
        tv_totalPayment = findViewById(R.id.tv_totalPayment);
        tv_total_amt_paid = findViewById(R.id.tv_total_amt_paid);
        ll_priceDetails = findViewById(R.id.ll_priceDetails);
        bottomLay = findViewById(R.id.bottomLay);

        dialog = new Dialog(CartActivity.this);

        getCart();

        imgView_shoppingCart.setVisibility(View.GONE);
        title.setText("Cart");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        initRecyclerView();
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED, false);

//        if (db.getCartTable().size() > 0) {
//            emptyBasketLay.setVisibility(View.GONE);
//        } else {
//            emptyBasketLay.setVisibility(View.VISIBLE);
//        }
//
//
//        List<CartModel> modelList = db.getCartTable();
//        for (int i = 0; i < modelList.size(); i++) {
//            CartModel cartModel = modelList.get(i);
//            CartModel cartModel1 = new CartModel();
//            cartModel1.setProd_cart_id(cartModel.getProd_cart_id());
//            cartModel1.setProd_cart_img(cartModel.getProd_cart_img());
//            cartModel1.setProd_cart_name(cartModel.getProd_cart_name());
//            cartModel1.setProd_cart_sold_by(cartModel.getProd_cart_sold_by());
//            cartModel1.setProd_cart_price(cartModel.getProd_cart_price());
//            cartModel1.setProd_qty(cartModel.getProd_qty());
//
//            mList.add(cartModel1);
//            initRecyclerView();
//
//        }

        bt_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogged) {
//                    getAddress();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(SharedPref.CART_PRICE,totalAmount.toString());
                    editor.commit();
                    startActivity(new Intent(CartActivity.this, ShippingDetailsActivity.class));
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(SharedPref.INTENT_VALUE, "Cart");
                    editor.commit();
                    finish();
                    startActivity(new Intent(CartActivity.this, LoginSignUpPageActivity.class));
                }

            }
        });

    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, mList);
        cart_recyclerView.setLayoutManager(layoutManager);
        cart_recyclerView.setAdapter(cartAdapter);
    }


    public void getCart() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("cartResp", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        JSONArray products_array = jsonObject.getJSONArray("products");
                        emptyBasketLay.setVisibility(View.GONE);
                        for (int i = 0; i < products_array.length(); i++) {
                            JSONObject object = products_array.getJSONObject(i);
                            CartModel cartModel = new CartModel();
                            cartModel.setProd_cart_id(object.optString("product_id"));
                            cartModel.setProd_cart_name(object.optString("name"));
                            cartModel.setProd_cart_img(object.optString("image"));
                            cartModel.setProd_cart_price(object.optString("final_price"));
                            cartModel.setProd_qty(object.optString("qty"));
                            cartModel.setVariant(object.optString("variant"));
                            totalAmount = totalAmount + Float.parseFloat(object.optString("final_price"));
                            mList.add(cartModel);
                            initRecyclerView();
                        }
                        ll_priceDetails.setVisibility(View.VISIBLE);
                        bottomLay.setVisibility(View.VISIBLE);
                        tv_totalPayment.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + " " + totalAmount.toString());
                        tv_totalMRP.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + " " + totalAmount.toString());
                        tv_total_amt_paid.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + " " + totalAmount.toString());
                    } else {
                        emptyBasketLay.setVisibility(View.VISIBLE);
                        Toast.makeText(CartActivity.this, "Cart Is Empty", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CartActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("action", "getCartItems");
                if (isLogged) {
                    params.put("uid", preferences.getString(SharedPref.USER_ID, null));
                } else {
                    SharedPreferences spf = getSharedPreferences("udid", MODE_PRIVATE);
                    params.put("uid", spf.getString(SharedPref.PHONE_ID, null));
                }

                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
        queue.add(stringRequest);
    }
}
