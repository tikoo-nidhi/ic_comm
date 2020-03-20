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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Adapters.ShowAddressAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAddressActivity extends AppCompatActivity {
    RecyclerView showAddressRecyclerView;
    ImageView left_arrow_appBar, imgView_shoppingCart;
    TextView title;
    List<AddressModel> mList = new ArrayList<>();
    //    AddressModel addressModel = new AddressModel();
//    DataBaseHandler db = new DataBaseHandler(ShowAddressActivity.this);
    Dialog dialog;
    SharedPreferences preferences;
    LinearLayout addAddressLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);
        showAddressRecyclerView = findViewById(R.id.showAddressRecyclerView);
        title = findViewById(R.id.title);
        addAddressLay = findViewById(R.id.addAddressLay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ShowAddressActivity.this)).getSupportActionBar().setTitle("");

        dialog = new Dialog(ShowAddressActivity.this);
        preferences = getSharedPreferences("data", MODE_PRIVATE);

        title.setText("Address");
        getAddress();
//        ((ShowAddressActivity.this)).getSupportActionBar().setTitle("Address");


        addAddressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ShowAddressActivity.this, AddressActivity.class);
                finish();
                mIntent.putExtra("val", "Add");
                mIntent.putExtra("Activity", "ShowAddress");
                startActivity(mIntent);
            }
        });

//        List<AddressModel> list = db.getAddressTable();
//
//        for (int i = 0; i < list.size(); i++) {
//            AddressModel addressModel = list.get(i);
//            AddressModel addressModel1 = new AddressModel();
//            addressModel1.setKey(addressModel.getKey());
//            addressModel1.setName(addressModel.getName());
//            addressModel1.setStd_code(addressModel.getStd_code());
//            addressModel1.setMobile(addressModel.getMobile());
//            addressModel1.setPin_code(addressModel.getPin_code());
//            addressModel1.setBuilding(addressModel.getBuilding());
//            addressModel1.setTown(addressModel.getTown());
//            addressModel1.setDistrict(addressModel.getDistrict());
//            addressModel1.setState(addressModel.getState());
//            addressModel1.setCountry(addressModel.getCountry());
//            addressModel1.setAddress_type(addressModel.getAddress_type());
//            mList.add(addressModel1);
//            initRecyclerView();
//
//
//        }

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
//        title.setVisibility(View.GONE);

        imgView_shoppingCart.setVisibility(View.GONE);
//        title.setText("Cart");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShowAddressActivity.this, LinearLayoutManager.VERTICAL, false);
        ShowAddressAdapter mAdapter = new ShowAddressAdapter(ShowAddressActivity.this, mList);
        showAddressRecyclerView.setLayoutManager(layoutManager);
        showAddressRecyclerView.setAdapter(mAdapter);
    }

    public void getAddress() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resNull", response);
//                if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
//                            db.deleteFromAddressTable();
                        JSONObject address = jsonObject.getJSONObject("address");
                        JSONArray address_data = address.getJSONArray("data");
                        if (address_data.length() == 0) {

                            Toast.makeText(ShowAddressActivity.this, "No Address Added", Toast.LENGTH_SHORT).show();

                        } else {
                            for (int i = 0; i < address_data.length(); i++) {
                                AddressModel addressModel = new AddressModel();
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
//                                db.addAddress(addressModel);
                                mList.add(addressModel);
                                initRecyclerView();
                            }
                        }
//                            finish();
                        dialog.cancel();
//

                    } else {
                        Toast.makeText(ShowAddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ShowAddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
//                } else {
//                    dialog.cancel();
//                    startActivity(new Intent(ShippingDetailsActivity.this, AddressActivity.class));
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(CartActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(ShowAddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("action", "getAddress");
                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ShowAddressActivity.this);
        queue.add(stringRequest);
    }
}
