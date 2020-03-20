package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Adapters.AddressDetailsAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingDetailsActivity extends AppCompatActivity {
    RecyclerView address_recyclerView, details_recyclerView;
    TextView tv_totalAmount, tv_select, title;
    CardView cv_addNewAddress;
    Button bt_continue;
    LinearLayout linearLayout_orderDetails, selectAddress;
    LinearLayout totalOrders[];
    List<AddressModel> mAddressModel = new ArrayList<>();
    List<AddressModel> mAddressModel1 = new ArrayList<>();

    DataBaseHandler dataBaseHandler = new DataBaseHandler(ShippingDetailsActivity.this);
    AddressModel address = new AddressModel();
    ImageView imgError, left_arrow_appBar, imgView_shoppingCart;
    Intent intent;

    Dialog dialog;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ShippingDetailsActivity.this)).getSupportActionBar().setTitle("");
        address_recyclerView = findViewById(R.id.address_recyclerView);
        tv_totalAmount = findViewById(R.id.tv_totalAmount);
        cv_addNewAddress = findViewById(R.id.cv_addNewAddress);
        bt_continue = findViewById(R.id.bt_continue);
        linearLayout_orderDetails = findViewById(R.id.linearLayout_orderDetails);
        selectAddress = findViewById(R.id.selectAddress);
        imgError = findViewById(R.id.imgError);
        tv_select = findViewById(R.id.tv_select);
        title = findViewById(R.id.title);
        details_recyclerView = findViewById(R.id.details_recyclerView);

        dialog = new Dialog(ShippingDetailsActivity.this);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        getAddress();

        final Animation shakeAnim = AnimationUtils.loadAnimation(ShippingDetailsActivity.this, R.anim.shake);


        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);


        imgView_shoppingCart.setVisibility(View.GONE);
        title.setText("Shipping Details");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        intent = new Intent(ShippingDetailsActivity.this, PaymentActivity.class);

        tv_totalAmount.setText(getSharedPreferences("cur",MODE_PRIVATE).getString(SharedPref.CURRENCY,null)+preferences.getString(SharedPref.CART_PRICE,"0"));

        cv_addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ShippingDetailsActivity.this, AddressActivity.class);
                mIntent.putExtra("val", "Add");
                finish();
                startActivity(mIntent);
            }
        });

//        List<AddressModel> addressModels = dataBaseHandler.getAddressTable();
//        for (int i = 0; i < addressModels.size(); i++) {
//            AddressModel addressModel = addressModels.get(i);
//            final AddressModel addressModel1 = new AddressModel();
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
//            mAddressModel.add(addressModel1);
//            initRecyclerView();
//        }

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < address_recyclerView.getChildCount(); i++) {

                    RadioButton rb = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.radioButton_name);
                    TextView tv_number = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddNumber);
                    TextView tv_pinCode = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddPinCode);
                    TextView tv_building = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddBuilding);
                    TextView tv_town = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddTown);
                    TextView tv_district = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddDistrict);
                    TextView tv_state = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddState);
                    TextView tv_country = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_fullAddCountry);
                    TextView tv_addressType = address_recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tv_addressType);
                    if (rb.isChecked()) {
                        intent.putExtra("name", rb.getText());
                        intent.putExtra("number", tv_number.getText());
                        intent.putExtra("pinCode", tv_pinCode.getText());
                        intent.putExtra("building", tv_building.getText());
                        intent.putExtra("town", tv_town.getText());
                        intent.putExtra("district", tv_district.getText());
                        intent.putExtra("state", tv_state.getText());
                        intent.putExtra("country", tv_country.getText());
                        intent.putExtra("addressType", tv_addressType.getText());
                        imgError.setVisibility(View.GONE);
                        startActivity(intent);
                        break;
                    } else {
                        imgError.requestFocus();
                        imgError.setVisibility(View.VISIBLE);
                        selectAddress.startAnimation(shakeAnim);
                        if (Build.VERSION.SDK_INT >= 26) {
                            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
                        }
                        Snackbar.make(v, "Select a shipping Address", 2000).setAction("Action", null).show();
                    }
                }
            }
        });
    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShippingDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        AddressDetailsAdapter mAdapter = new AddressDetailsAdapter(ShippingDetailsActivity.this, mAddressModel);
        address_recyclerView.setLayoutManager(layoutManager);
        address_recyclerView.setAdapter(mAdapter);
    }

    public void display(List<AddressModel> s) {
        mAddressModel = new ArrayList<>();
        for (int a = 0; a < s.size(); a++) {
            if (s.get(a).getSelectAddress()) {
                AddressModel addressModel = new AddressModel();
                addressModel.setName(s.get(a).getName());
                addressModel.setMobile(s.get(a).getMobile());
                addressModel.setPin_code(s.get(a).getPin_code());
                addressModel.setBuilding(s.get(a).getBuilding());
                addressModel.setTown(s.get(a).getTown());
                addressModel.setDistrict(s.get(a).getDistrict());
                addressModel.setState(s.get(a).getState());
                addressModel.setCountry(s.get(a).getCountry());
                addressModel.setAddress_type(s.get(a).getAddress_type());
                mAddressModel1.add(addressModel);
            }

            Log.d("workingAddress", String.valueOf(s.get(a).getSelectAddress()));

        }
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
                            if (address_data.length()==0){
                                Intent mIntent = new Intent(ShippingDetailsActivity.this, AddressActivity.class);
                                mIntent.putExtra("val","Add") ;
                                startActivity(mIntent);
                            }else {
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
                                    mAddressModel.add(addressModel);
                                    initRecyclerView();
                                }
                            }
//                            finish();
                            dialog.cancel();
//

                        } else {
                            Toast.makeText(ShippingDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ShippingDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ShippingDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(ShippingDetailsActivity.this);
        queue.add(stringRequest);
    }


}
