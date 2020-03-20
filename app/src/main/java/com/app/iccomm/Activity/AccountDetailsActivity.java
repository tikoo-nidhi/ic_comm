package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountDetailsActivity extends AppCompatActivity {
    ImageView imgView_profilePic, left_arrow_appBar, imgView_shoppingCart;
    TextView tv_orders, tv_addresses, tv_profileSettings, title, tv_user_name;
    Button bt_logOut;
    Dialog dialog;
    DataBaseHandler db = new DataBaseHandler(AccountDetailsActivity.this);
    SharedPreferences preferences;
    LinearLayout ordersLay, addressLay, profileLay, settingsLay;
    AddressModel addressModel = new AddressModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        //Initialize UI Elements
        imgView_profilePic = findViewById(R.id.imgView_profilePic);
        tv_user_name = findViewById(R.id.tv_user_name);
        ordersLay = findViewById(R.id.ordersLay);
        addressLay = findViewById(R.id.addressLay);
        profileLay = findViewById(R.id.profileLay);
        settingsLay = findViewById(R.id.settingsLay);
        bt_logOut = findViewById(R.id.bt_logOut);
        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        title = findViewById(R.id.title);

        Log.d("ActivityName: ", "AccountDetailsActivity");

        //Initialize Dialog
        dialog = new Dialog(AccountDetailsActivity.this);
        preferences = getSharedPreferences("data", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgView_shoppingCart.setVisibility(View.GONE);
        title.setVisibility(View.GONE);

        ((AccountDetailsActivity.this)).getSupportActionBar().setTitle("");

        tv_user_name.setText(preferences.getString(SharedPref.USERNAME,null));

        getUserDetails();

        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ordersLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountDetailsActivity.this, OrderActivity.class));
            }
        });

        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getAddress();
                startActivity(new Intent(AccountDetailsActivity.this, ShowAddressActivity.class));
            }
        });

        profileLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AccountDetailsActivity.this, ProfileSettingsActivity.class));
            }
        });

        bt_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AccountDetailsActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().commit();
                                editor.putBoolean(SharedPref.IS_LOGGED, false);
                                editor.commit();
                                db.dropTables();
                                finish();
                                startActivity(new Intent(AccountDetailsActivity.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public void getAddress() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_USER_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        db.deleteFromAddressTable();
                        JSONObject address = jsonObject.getJSONObject("address");
                        JSONArray address_data = address.getJSONArray("data");
                        for (int i = 0; i < address_data.length(); i++) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(SharedPref.HAS_ADDRESS, true);
                            editor.commit();
                            JSONObject object = address_data.getJSONObject(i);
                            addressModel.setKey(Integer.parseInt(object.optString("id")));
                            addressModel.setName(object.optString("user_name"));
//                            String val = object.optString("phone_number");
                            addressModel.setStd_code("+91");
                            addressModel.setMobile(object.optString("phone_number"));
                            addressModel.setPin_code(object.optString("pincode"));
                            addressModel.setBuilding(object.optString("building"));
                            addressModel.setTown(object.optString("town"));
                            addressModel.setDistrict(object.optString("district"));
                            addressModel.setState(object.optString("state"));
                            addressModel.setCountry(object.optString("country"));
                            addressModel.setAddress_type(object.optString("address_type"));
                            db.addAddress(addressModel);
                        }
                        finish();
                        dialog.cancel();
                        startActivity(new Intent(AccountDetailsActivity.this, ShowAddressActivity.class));

                    } else {
                        dialog.cancel();
                        startActivity(new Intent(AccountDetailsActivity.this, AddressActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.cancel();
                    Toast.makeText(AccountDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(AccountDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "getAddress");
                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AccountDetailsActivity.this);
        queue.add(stringRequest);
    }

    public void getUserDetails() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_USER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseUserDetails", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("True")){
                        Glide.with(AccountDetailsActivity.this).load(jsonObject.optString("image")).into(imgView_profilePic);
                        dialog.cancel();
                    }else {
                        Toast.makeText(AccountDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AccountDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();  }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AccountDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(AccountDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.USER_ID,null));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AccountDetailsActivity.this);
        queue.add(stringRequest);
    }

}
