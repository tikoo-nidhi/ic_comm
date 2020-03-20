package com.app.iccomm.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.app.iccomm.Adapters.OrderDetailsAdapter;
import com.app.iccomm.Config.Config;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Model.CartModel;
import com.app.iccomm.Model.OrderDetailsModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    Button bt_payNow;
    RadioGroup radioGrp_payment;
    RadioButton rb_payPal, rb_cod, mRadioButton;
    TextView tv_noOfItems, tv_itemAmount, tv_deliveryCharges, tv_totalPayable, tv_name, tv_addressType, tv_fullAddBuilding, tv_fullAddTown,
            tv_fullAddDistrict, tv_fullAddState, tv_fullAddCountry, tv_fullAddPinCode, tv_fullAddNumber, tv_changeAddress, tv_totalAmount;
    int selectedId;
    String selected_radiobutton;
    public static final int PAYPAL_REQUEST_CODE = 3216;
    public static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    String amount = "";
    ImageView left_arrow_appBar, imgView_shoppingCart;
    TextView title;
    DataBaseHandler db = new DataBaseHandler(PaymentActivity.this);
    Intent mIntent;
    RecyclerView rv_orderDetails;
    List<OrderDetailsModel> orderDetailsModelList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onDestroy() {
        stopService(new Intent(PaymentActivity.this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bt_payNow = findViewById(R.id.bt_payNow);
        radioGrp_payment = findViewById(R.id.radioGrp_payment);
        rb_payPal = findViewById(R.id.rb_payPal);
        rb_cod = findViewById(R.id.rb_cod);
        tv_noOfItems = findViewById(R.id.tv_noOfItems);
        tv_itemAmount = findViewById(R.id.tv_itemAmount);
        tv_deliveryCharges = findViewById(R.id.tv_deliveryCharges);
        tv_totalPayable = findViewById(R.id.tv_totalPayable);
        tv_name = findViewById(R.id.tv_name);
        tv_addressType = findViewById(R.id.tv_addressType);
        tv_fullAddBuilding = findViewById(R.id.tv_fullAddBuilding);
        tv_fullAddTown = findViewById(R.id.tv_fullAddTown);
        tv_fullAddDistrict = findViewById(R.id.tv_fullAddDistrict);
        tv_fullAddState = findViewById(R.id.tv_fullAddState);
        tv_fullAddCountry = findViewById(R.id.tv_fullAddCountry);
        tv_fullAddPinCode = findViewById(R.id.tv_fullAddPinCode);
        tv_fullAddNumber = findViewById(R.id.tv_fullAddNumber);
        tv_changeAddress = findViewById(R.id.tv_changeAddress);
        tv_totalAmount = findViewById(R.id.tv_totalAmount);
        title = findViewById(R.id.title);
        rv_orderDetails = findViewById(R.id.rv_orderDetails);

        mIntent = getIntent();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((PaymentActivity.this)).getSupportActionBar().setTitle("");


        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);

        dialog = new Dialog(PaymentActivity.this);

        imgView_shoppingCart.setVisibility(View.GONE);
        title.setText("Payment Details");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getCart();

        tv_changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, ShippingDetailsActivity.class));
            }
        });
        //start paypal service

        Intent myIntent = new Intent(PaymentActivity.this, PayPalService.class);
        myIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(myIntent);
        bt_payNow.setEnabled(false);
//        Log.d("selectedAddress",)

        tv_name.setText(mIntent.getStringExtra("name"));
        tv_addressType.setText(mIntent.getStringExtra("addressType"));
        tv_fullAddBuilding.setText(mIntent.getStringExtra("building"));
        tv_fullAddTown.setText(mIntent.getStringExtra("town"));
        tv_fullAddDistrict.setText(mIntent.getStringExtra("district") + " ");
        tv_fullAddState.setText(mIntent.getStringExtra("state"));
        tv_fullAddCountry.setText(mIntent.getStringExtra("country") + " ");
        tv_fullAddPinCode.setText(mIntent.getStringExtra("pinCode"));
        tv_fullAddNumber.setText(mIntent.getStringExtra("number"));



        radioGrp_payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGrp_payment.getCheckedRadioButtonId() != -1) {
                    bt_payNow.setEnabled(true);
                    bt_payNow.setBackground(getResources().getDrawable(R.color.colorLogoBlue));
                    selectedId = radioGrp_payment.getCheckedRadioButtonId();
                    mRadioButton = findViewById(selectedId);
                    selected_radiobutton = mRadioButton.getText().toString();
//                    Toast.makeText(PaymentActivity.this, "Button Enabled", Toast.LENGTH_SHORT).show();
                    switch (selected_radiobutton) {
                        case "PayPal":
                            bt_payNow.setText("Pay Now");
                            bt_payNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    processPayment();
                                }
                            });
                            break;
                        case "Cash On Delivery":
                            bt_payNow.setText("Confirm Order");
                            bt_payNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    final Dialog dialog = new Dialog(PaymentActivity.this);
//                                    dialog.setContentView(R.layout.progress_bar_layout);
//                                    dialog.setCancelable(true);
//                                    dialog.show();
                                    placeOrderApi();
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            final Dialog dialog = new Dialog(PaymentActivity.this);
//                                            dialog.setContentView(R.layout.order_placed_dialog);
//                                            dialog.setCancelable(true);
//                                            dialog.show();
//                                            finish();
//                                            startActivity(new Intent(PaymentActivity.this, OrderDetailsActivity.class));
//                                        }
//                                    }, 500);

                                }
                            });
                            break;
                    }
                }
            }
        });


    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PaymentActivity.this);
        OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(PaymentActivity.this, orderDetailsModelList);
        rv_orderDetails.setLayoutManager(layoutManager);
        rv_orderDetails.setAdapter(orderDetailsAdapter);
    }

    private void processPayment() {
        String abc = tv_totalAmount.getText().toString();
        amount = abc.replace("\u20B9 ", "");
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount), "USD", "Your Total Amount", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaymentActivity.this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(PaymentActivity.this, PayPalPaymentActivity.class)
                                .putExtra("paymentDetails", paymentDetails)
                                .putExtra("paymentAmount", amount)
                        );
                        Log.d("paymentdetails", paymentDetails);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }


    public void placeOrderApi() {
        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_PLACE_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseOrder", response);
                dialog.cancel();
                final Dialog dialog1 = new Dialog(PaymentActivity.this);
                dialog1.setContentView(R.layout.order_placed_dialog);
                dialog1.setCancelable(true);
                dialog1.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                    }
                },1000);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(PaymentActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                params.put("ptype", "COD");
                params.put("action", "placeOrder");
                params.put("name", tv_name.getText().toString());
                params.put("user_id", sharedPreferences.getString(SharedPref.USER_ID, null));
                params.put("building", tv_fullAddBuilding.getText().toString());
                params.put("town", tv_fullAddTown.getText().toString());
                params.put("district", tv_fullAddDistrict.getText().toString());
                params.put("state", tv_fullAddState.getText().toString());
                params.put("country", tv_fullAddCountry.getText().toString());
                params.put("phone_number", tv_fullAddNumber.getText().toString());
                params.put("address_type", tv_addressType.getText().toString());
                params.put("pincode", tv_fullAddPinCode.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        queue.add(stringRequest);
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
                        for (int i = 0; i < products_array.length(); i++) {
                            JSONObject object = products_array.getJSONObject(i);
                            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                            orderDetailsModel.setOrderItemId(object.optString("product_id"));
                            orderDetailsModel.setOrderName(object.optString("name"));
                            orderDetailsModel.setOrderImg(object.optString("image"));
                            orderDetailsModel.setOrderPrice(object.optString("final_price"));
                            orderDetailsModel.setOrderQty(object.optString("qty"));
                            orderDetailsModel.setOrderSubTotal(String.valueOf(object.optInt("final_price")*object.optInt("qty")));
                            Log.d("subTotal",String.valueOf(object.optInt("final_price")*object.optInt("qty")));
                            orderDetailsModelList.add(orderDetailsModel);
                            initRecyclerView();
                        }

                        tv_totalAmount.setText(getSharedPreferences("cur",MODE_PRIVATE).getString(SharedPref.CURRENCY,null)+getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.CART_PRICE,"0"));
                    } else {
                        Toast.makeText(PaymentActivity.this, "Cart Is Empty", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
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
                params.put("action", "getCartItems");
                params.put("uid", getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.USER_ID, null));
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        queue.add(stringRequest);
    }
}


