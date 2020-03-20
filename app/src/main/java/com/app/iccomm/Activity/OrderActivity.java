package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.app.iccomm.Adapters.OrderAdapter;
import com.app.iccomm.Data.Constants;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.OrderModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    RecyclerView orders_recyclerView;
    ImageView left_arrow_appBar,imgView_shoppingCart;
    TextView title;
    SharedPreferences preferences;
    Boolean isLogged;
    List<OrderModel> modelList = new ArrayList<>();
    Dialog dialog;
    LinearLayout noConnectionLayout;
    Button bt_tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        title = findViewById(R.id.title);
        noConnectionLayout = findViewById(R.id.noConnectionLayout);
        bt_tryAgain = findViewById(R.id.bt_tryAgain);
        Log.d("ActivityName: ", "Order Activity");

        preferences = getSharedPreferences("data",MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED,false);

        title.setText("Orders");
        imgView_shoppingCart.setVisibility(View.GONE);
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialog = new Dialog(OrderActivity.this);



        if (isLogged){
            checkConnectivity();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SharedPref.INTENT_VALUE,"Order");
            editor.commit();
            startActivity(new Intent(OrderActivity.this,LoginSignUpPageActivity.class));
        }

        bt_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
            }
        });

        orders_recyclerView = findViewById(R.id.orders_recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((OrderActivity.this)).getSupportActionBar().setTitle("");
        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this,LinearLayoutManager.VERTICAL,false);
        OrderAdapter mAdapter = new OrderAdapter(OrderActivity.this,modelList);
        orders_recyclerView.setLayoutManager(layoutManager);
        orders_recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(OrderActivity.this,MainActivity.class));
    }

    public void getOrderList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseOrder",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")){
                        JSONObject user = jsonObject.getJSONObject("user");
                        JSONArray orders = user.getJSONArray("orders");
                        for (int i = 0;i<orders.length();i++){
                            JSONObject object = orders.getJSONObject(i);
                            OrderModel orderModel = new OrderModel();
                            orderModel.setOrderId(object.optString("order_id"));
                            orderModel.setOrderStatus(object.optString("status"));
                            orderModel.setOrderPrice(getSharedPreferences("cur",MODE_PRIVATE).getString(SharedPref.CURRENCY,null)+" "+object.optString("total"));
                            orderModel.setOrderDate(object.optString("odr_date"));
                            modelList.add(orderModel);
                            initRecyclerView();
                        }
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, "Technical Issue Come Back Later", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String> params = new HashMap<>();
                params.put("user_id",preferences.getString(SharedPref.USER_ID,null));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(OrderActivity.this);
        queue.add(stringRequest);
    }

    public void checkConnectivity() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    noConnectionLayout.setVisibility(View.GONE);
                    getOrderList();
                } else {
                    dialog.cancel();
                    noConnectionLayout.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }
}
