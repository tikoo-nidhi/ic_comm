package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.app.iccomm.Adapters.OrderItemListAdapter;
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

public class OrderDetailsActivity extends AppCompatActivity {
    ImageView left_arrow_appBar, imgView_shoppingCart;
    TextView title;
    Intent intent;
    SharedPreferences sharedPreferences;
    TextView orderId, orderDate, orderAmount, orderPaymentType, tv_name, tv_addressType, tv_fullAddBuilding, tv_fullAddTown, tv_fullAddDistrict,
            tv_fullAddState, tv_fullAddCountry, tv_fullAddPinCode, tv_fullAddNumber, tv_totalMRP, tv_discount, tv_tax, tv_couponDiscount, tv_deliveryCharges,
            tv_totalPayment;
    RecyclerView orderItem_rv;
    List<OrderModel> modelList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        title = findViewById(R.id.title);
        orderId = findViewById(R.id.orderId);
        orderDate = findViewById(R.id.orderDate);
        orderAmount = findViewById(R.id.orderAmount);
        orderItem_rv = findViewById(R.id.orderItem_rv);
        orderPaymentType = findViewById(R.id.orderPaymentType);
        tv_name = findViewById(R.id.tv_name);
        tv_addressType = findViewById(R.id.tv_addressType);
        tv_fullAddBuilding = findViewById(R.id.tv_fullAddBuilding);
        tv_fullAddTown = findViewById(R.id.tv_fullAddTown);
        tv_fullAddDistrict = findViewById(R.id.tv_fullAddDistrict);
        tv_fullAddState = findViewById(R.id.tv_fullAddState);
        tv_fullAddCountry = findViewById(R.id.tv_fullAddCountry);
        tv_fullAddPinCode = findViewById(R.id.tv_fullAddPinCode);
        tv_fullAddNumber = findViewById(R.id.tv_fullAddNumber);
        tv_totalMRP = findViewById(R.id.tv_totalMRP);
        tv_discount = findViewById(R.id.tv_discount);
        tv_tax = findViewById(R.id.tv_tax);
        tv_couponDiscount = findViewById(R.id.tv_couponDiscount);
        tv_deliveryCharges = findViewById(R.id.tv_deliveryCharges);
        tv_totalPayment = findViewById(R.id.tv_totalPayment);

        intent = getIntent();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
         dialog = new Dialog(OrderDetailsActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.show();
        getOrderDetails();

        title.setVisibility(View.GONE);

        imgView_shoppingCart.setVisibility(View.GONE);
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderDetailsActivity.this, OrderActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        OrderItemListAdapter mAdapter = new OrderItemListAdapter(OrderDetailsActivity.this, modelList);
        orderItem_rv.setLayoutManager(layoutManager);
        orderItem_rv.setAdapter(mAdapter);
    }

    public void getOrderDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ORDER_ITEM_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.cancel();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        orderId.setText("Order Id " + jsonObject.optString("ordersid"));
                        orderDate.setText("Order Date " + jsonObject.optString("odr_date"));
                        orderAmount.setText("Order Amount " + getSharedPreferences("cur",MODE_PRIVATE).getString(SharedPref.CURRENCY,null) + " " + jsonObject.optString("total"));
                        JSONObject orders = jsonObject.getJSONObject("order");
                        JSONArray items_array = orders.getJSONArray("items");
                        for (int i = 0; i < items_array.length(); i++) {
                            JSONObject object = items_array.getJSONObject(i);
                            OrderModel orderModel = new OrderModel();
                            orderModel.setOrderImg(object.optString("image"));
                            orderModel.setOrderName(object.optString("product_name"));
                            orderModel.setOrderPrice(object.optString("amount"));
                            orderModel.setOrderQty(object.optString("quantity"));
                            orderModel.setOrderSubTotal(String.valueOf(object.optInt("amount")*object.optInt("quantity")));
                            modelList.add(orderModel);
                            initRecyclerView();
                        }
                        orderPaymentType.setText(jsonObject.optString("payment_type"));
                        tv_name.setText(jsonObject.optString("user_name"));
                        tv_addressType.setText(jsonObject.optString("address_type"));
                        tv_fullAddBuilding.setText(jsonObject.optString("building"));
                        tv_fullAddTown.setText(jsonObject.optString("town"));
                        tv_fullAddDistrict.setText(jsonObject.optString("district") + " ");
                        tv_fullAddState.setText(jsonObject.optString("state"));
                        tv_fullAddCountry.setText(jsonObject.optString("country") + " ");
                        tv_fullAddPinCode.setText(jsonObject.optString("pincode"));
                        tv_fullAddNumber.setText(jsonObject.optString("phone_number"));
                        tv_totalMRP.setText(jsonObject.optString("total"));
                        tv_discount.setText("0");
                        tv_tax.setText("0");
                        tv_couponDiscount.setText("0");
                        tv_totalPayment.setText(jsonObject.optString("total"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderDetailsActivity.this, "Technical Issue Come Back Later", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("action", "getOrderDetail");
                params.put("order_id", intent.getStringExtra("orderId"));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(OrderDetailsActivity.this);
        queue.add(stringRequest);
    }
}
