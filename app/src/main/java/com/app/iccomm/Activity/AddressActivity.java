package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.app.iccomm.Adapters.OrderDetailsAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    CountryCodePicker ccp_address;
    EditText ed_addName, ed_addNumber, ed_addPinCode, ed_addBuildingDetails, ed_addTown, ed_addDistrict,
            ed_addState, ed_addCountry;
    String countryId;
    Button bt_saveAddress;
    TextInputLayout textInput_addName, textInput_addNumber, textInput_addPinCode, textInput_addBuildingDetails, textInput_addTown,
            textInput_addDistrict, textInput_addState, textInput_addCountry;
    RadioButton mRadioButton, radioBt_office, radioBt_home;
    TextView tv_cancelAddress, tv_addressType;
    ImageView errorImg;
    RadioGroup mRadioGroup;
    int selectedId;
    String selected_radiobutton;
    //    DataBaseHandler dataBaseHandler = new DataBaseHandler(AddressActivity.this);
    Intent intent;
    Boolean bool = false;
    SharedPreferences preferences;
    FrameLayout frame;
    Dialog dialog;
    DataBaseHandler db = new DataBaseHandler(AddressActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ccp_address = findViewById(R.id.ccp_address);
        ed_addName = findViewById(R.id.ed_addName);
        ed_addNumber = findViewById(R.id.ed_addNumber);
        ed_addPinCode = findViewById(R.id.ed_addPinCode);
        ed_addBuildingDetails = findViewById(R.id.ed_addBuildingDetails);
        ed_addTown = findViewById(R.id.ed_addTown);
        ed_addDistrict = findViewById(R.id.ed_addDistrict);
        ed_addState = findViewById(R.id.ed_addState);
        ed_addCountry = findViewById(R.id.ed_addCountry);
        bt_saveAddress = findViewById(R.id.bt_saveAddress);
        textInput_addName = findViewById(R.id.textInput_addName);
        textInput_addNumber = findViewById(R.id.textInput_addNumber);
        textInput_addPinCode = findViewById(R.id.textInput_addPinCode);
        textInput_addBuildingDetails = findViewById(R.id.textInput_addBuildingDetails);
        textInput_addTown = findViewById(R.id.textInput_addTown);
        textInput_addDistrict = findViewById(R.id.textInput_addDistrict);
        textInput_addState = findViewById(R.id.textInput_addState);
        textInput_addCountry = findViewById(R.id.textInput_addCountry);
        radioBt_home = findViewById(R.id.radioBt_home);
        radioBt_office = findViewById(R.id.radioBt_office);
        tv_cancelAddress = findViewById(R.id.tv_cancelAddress);
        errorImg = findViewById(R.id.errorImg);
        tv_addressType = findViewById(R.id.tv_addressType);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        frame = findViewById(R.id.frame);
        intent = getIntent();


        dialog = new Dialog(AddressActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(false);
        ((AddressActivity.this)).getSupportActionBar().setTitle("Address");

        preferences = getSharedPreferences("data", MODE_PRIVATE);


        ed_addState.setFocusable(false);
        ed_addState.setClickable(true);
        ed_addCountry.setEnabled(false);

//        bool = intent.getBooleanExtra("bool", false);
//        if (bool) {        }


//        ed_addDistrict.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                getStateId(ed_addDistrict.getText().toString());
//            }
//        });

        ed_addState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateId(ed_addDistrict.getText().toString());

            }
        });


        if (ccp_address.getSelectedCountryCode().equals(ccp_address.getDefaultCountryCode())) {
            getCountry(Integer.parseInt(ccp_address.getSelectedCountryCode()));
        }
        ccp_address.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                getCountry(Integer.parseInt(ccp_address.getSelectedCountryCode()));
            }
        });

        Log.d("ccp", ccp_address.getSelectedCountryCode());

        if (intent.hasExtra("val")) {
            if (intent.getStringExtra("val").equals("Edit")) {
                ed_addName.setText(intent.getStringExtra("name"));
                ccp_address.setCountryForPhoneCode(Integer.parseInt(intent.getStringExtra("std_code")));
                ed_addNumber.setText(intent.getStringExtra("number"));
                ed_addPinCode.setText(intent.getStringExtra("pinCode"));
                ed_addBuildingDetails.setText(intent.getStringExtra("building"));
                ed_addTown.setText(intent.getStringExtra("town"));
                ed_addDistrict.setText(intent.getStringExtra("district"));
                ed_addState.setText(intent.getStringExtra("state"));
                ed_addCountry.setText(intent.getStringExtra("country"));
                switch (intent.getStringExtra("addressType")) {
                    case "Home":
                        radioBt_home.setChecked(true);
                        break;
                    case "Office":
                        radioBt_office.setChecked(true);
                }


            } else if (intent.getStringExtra("val").equals("Add")) {
                Log.d("val", "Add");
            }
        }
        bt_saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(ed_addName.getText())) {
                    ed_addName.setError("Enter Name");
                    ed_addName.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_addNumber.getText())) {
                    ed_addNumber.setError("Enter Number");
                    ed_addNumber.requestFocus();
                    shakeIt();
                    return;
                }
                if (ed_addNumber.length() < 10) {
                    ed_addNumber.setError("Enter a Valid Number");
                    ed_addNumber.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_addPinCode.getText())) {
                    ed_addPinCode.setError("Enter PIN code");
                    ed_addPinCode.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_addBuildingDetails.getText())) {
                    ed_addBuildingDetails.setError("Enter Address");
                    ed_addBuildingDetails.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_addTown.getText())) {
                    ed_addTown.setError("Enter Town");
                    ed_addTown.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_addDistrict.getText())) {
                    ed_addDistrict.setError("Enter District");
                    ed_addDistrict.requestFocus();
                    shakeIt();
                    return;
                }
                if (mRadioGroup.getCheckedRadioButtonId() == -1) {
                    tv_addressType.requestFocus();
                    errorImg.setVisibility(View.VISIBLE);
                    Animation shakeAnim = AnimationUtils.loadAnimation(AddressActivity.this, R.anim.shake);
                    tv_addressType.startAnimation(shakeAnim);
                    shakeIt();
                    return;
                } else {
                    errorImg.setVisibility(View.GONE);
                    selectedId = mRadioGroup.getCheckedRadioButtonId();
                    mRadioButton = findViewById(selectedId);
                    selected_radiobutton = mRadioButton.getText().toString();
//                    Toast.makeText(AddressActivity.this, "" + selected_radiobutton, Toast.LENGTH_SHORT).show();

                }
                Log.d("key", String.valueOf(intent.getIntExtra("key", 0)));

                if (intent.hasExtra("val")) {
                    if (intent.getStringExtra("val").equals("Edit")) {
                        updateAddress(intent.getStringExtra("key"));
                        dialog.show();

                    } else if (intent.getStringExtra("val").equals("Add")) {
                        addAddress();
                        dialog.show();
//                        updateAddress("addAddress", null);
                    }
                } else {
//                    updateAddress("addAddress", null);
                }
            }
        });

        tv_cancelAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


    }

    public void shakeIt() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }


    // Get Country Name
    public void getCountry(int countryCode) {
        try {
            JSONObject obj = new JSONObject(loadJSONCountriesFromAsset());
            JSONArray mJsonArray = obj.getJSONArray("countries");

            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject jo_inside = mJsonArray.getJSONObject(i);
                Log.d("stringgggId", String.valueOf(countryCode));
//                Log.d("stringgggJo", jo_inside.getString("country_id"));

                if (countryCode == (jo_inside.getInt("phoneCode"))) {
                    String countryName = jo_inside.getString("name");
                    countryId = jo_inside.getString("id");
                    ed_addCountry.setText(countryName);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONCountriesFromAsset() {
        String json = null;
        try {
            InputStream is = AddressActivity.this.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Get State Id
    public void getStateId(String districtName) {
        try {
            JSONObject obj = new JSONObject(loadJSONStatesIdFromAsset());
            JSONArray mJsonArray = obj.getJSONArray("cities");

            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject jo_inside = mJsonArray.getJSONObject(i);
                Log.d("districtName2", String.valueOf(districtName));
                if (districtName.equals(jo_inside.getString("name"))) {
                    Log.d("districtName3", jo_inside.getString("state_id"));
                    String stateId = jo_inside.getString("state_id");
                    getState(stateId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONStatesIdFromAsset() {
        String json = null;
        try {
            InputStream is = AddressActivity.this.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Get Stare Name
    public void getState(String stateId) {
        try {
            JSONObject obj = new JSONObject(loadJSONStatesFromAsset());
            JSONArray mJsonArray = obj.getJSONArray("states");

            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject jo_inside = mJsonArray.getJSONObject(i);
                Log.d("stateId", String.valueOf(stateId));

                if (stateId.equals(jo_inside.getString("id"))) {
                    Log.d("stateName", jo_inside.getString("name"));
                    String stateName = jo_inside.getString("name");
                    ed_addState.setText(stateName);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONStatesFromAsset() {
        String json = null;
        try {
            InputStream is = AddressActivity.this.getAssets().open("states.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void addAddress() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseAddAddress", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(AddressActivity.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        if (intent.hasExtra("Activity")){
                            finish();
                            startActivity(new Intent(AddressActivity.this,ShowAddressActivity.class));
                        }else {
                            finish();
                            startActivity(new Intent(AddressActivity.this,ShippingDetailsActivity.class));
                        }
                        dialog.cancel();
                    }else {
                        Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("user_id", getSharedPreferences("data", MODE_PRIVATE).getString(SharedPref.USER_ID, null));
                params.put("name", ed_addName.getText().toString());
                params.put("pincode", ed_addPinCode.getText().toString());
                params.put("building", ed_addBuildingDetails.getText().toString());
                params.put("town", ed_addTown.getText().toString());
                params.put("district", ed_addDistrict.getText().toString());
                params.put("state", ed_addState.getText().toString());
                params.put("country", ed_addCountry.getText().toString());
                params.put("phone_number", ed_addNumber.getText().toString());
                params.put("address_type", selected_radiobutton);
                params.put("alt_number", "");
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(AddressActivity.this);
        queue.add(stringRequest);
    }

    private void updateAddress(final String key) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_UPDATE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseAddAddress", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(AddressActivity.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                        if (intent.hasExtra("Activity")){
                            finish();
                            startActivity(new Intent(AddressActivity.this,ShowAddressActivity.class));
                        }else {
                            finish();
                            startActivity(new Intent(AddressActivity.this, ShippingDetailsActivity.class));
                        }
                        dialog.cancel();
                    } else {
                        Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                } catch (JSONException e) {
                    Toast.makeText(AddressActivity.this, "Something Went Wrong"+e, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddressActivity.this, "Something Went Wrong"+error, Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String, String> params = new HashMap<>();
//                params.put("user_id",getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.USER_ID,null));
                params.put("id", key);
                params.put("name", ed_addName.getText().toString());
                params.put("pincode", ed_addPinCode.getText().toString());
                params.put("building", ed_addBuildingDetails.getText().toString());
                params.put("town", ed_addTown.getText().toString());
                params.put("district", ed_addDistrict.getText().toString());
                params.put("state", ed_addState.getText().toString());
                params.put("country", ed_addCountry.getText().toString());
                params.put("phone_number", ed_addNumber.getText().toString());
                params.put("address_type", selected_radiobutton);
                params.put("alt_number", "");
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(AddressActivity.this);
        queue.add(stringRequest);
    }

//    private void updateAddress(final String action, final String id) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_USER_DATA, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("addRes", response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.optString("status").equals("true")) {
//                        Toast.makeText(AddressActivity.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
//                        getAddress();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                }
////                getAddress();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AddressActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("action", action);
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                if (intent.hasExtra("val")) {
//                    if (intent.getStringExtra("val").equals("Edit"))
//                params.put("id", id);
//                }
//                params.put("name", ed_addName.getText().toString());
//                params.put("pincode", ed_addPinCode.getText().toString());
//                params.put("building", ed_addBuildingDetails.getText().toString());
//                params.put("town", ed_addTown.getText().toString());
//                params.put("district", ed_addDistrict.getText().toString());
//                params.put("state", ed_addState.getText().toString());
//                params.put("country", ed_addCountry.getText().toString());
//                params.put("phone_number",ed_addNumber.getText().toString());
//                params.put("address_type", selected_radiobutton);
//                Log.d("params", String.valueOf(params));
//                return params;
//            }
//
//        };
//        RequestQueue queue = Volley.newRequestQueue(AddressActivity.this);
//        queue.add(stringRequest);
//    }
//
//    public void getAddress() {
//        dialog.setContentView(R.layout.progress_bar_layout);
//        dialog.setCancelable(true);
//        dialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_ADDRESS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("resNull", response);
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.optString("status").equals("true")) {
//                        db.deleteFromAddressTable();
//                        JSONObject address = jsonObject.getJSONObject("address");
//                        JSONArray address_data = address.getJSONArray("data");
//                        for (int i = 0; i < address_data.length(); i++) {
//                            AddressModel addressModel = new AddressModel();
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putBoolean(SharedPref.HAS_ADDRESS, true);
//                            editor.commit();
//                            JSONObject object = address_data.getJSONObject(i);
//                            addressModel.setKey(Integer.parseInt(object.optString("id")));
//                            addressModel.setName(object.optString("user_name"));
////                            String val = object.optString("phone_number");
//                            addressModel.setStd_code("+91");
//                            addressModel.setMobile(object.optString("phone_number"));
//                            addressModel.setPin_code(object.optString("pincode"));
//                            addressModel.setBuilding(object.optString("building"));
//                            addressModel.setTown(object.optString("town"));
//                            addressModel.setDistrict(object.optString("district"));
//                            addressModel.setState(object.optString("state"));
//                            addressModel.setCountry(object.optString("country"));
//                            addressModel.setAddress_type(object.optString("address_type"));
//                            db.addAddress(addressModel);
//                        }
//                        finish();
//                        dialog.cancel();
//                        startActivity(new Intent(AddressActivity.this, ShowAddressActivity.class));
//
//                    } else {
//                        Toast.makeText(AddressActivity.this, "Something Went Wrong" , Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(AddressActivity.this, "Something Went Wrong" , Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AddressActivity.this, "Something Went Wrong" , Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
////                params.put("action", "getAddress");
//                params.put("user_id", preferences.getString(SharedPref.USER_ID,null));
//                Log.d("paramsLog", String.valueOf(params));
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(AddressActivity.this);
//        queue.add(stringRequest);
//    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (intent.hasExtra("Activity")){
            finish();
            startActivity(new Intent(AddressActivity.this,ShowAddressActivity.class));
        }else {
            finish();
            startActivity(new Intent(AddressActivity.this,ShippingDetailsActivity.class));

        }
    }
}

