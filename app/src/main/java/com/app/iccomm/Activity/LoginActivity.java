package com.app.iccomm.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView tv_sign_up, tv_forgot_password;
    EditText ed_emailId, ed_password;
    TextInputLayout textInput_id, textInput_password;
    Button bt_logIn;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(LoginActivity.this);
    SharedPreferences preferences;
    Boolean isLogged;
    ProgressDialog pd;
    LinearLayout parentLayout;
    String snackText;
    List<CartModel> mList = new ArrayList<>();
    CartModel cartModel = new CartModel();
    DataBaseHandler db = new DataBaseHandler(LoginActivity.this);
    int pos;
    AddressModel addressModel = new AddressModel();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        ed_emailId = findViewById(R.id.ed_emailId);
        ed_password = findViewById(R.id.ed_password);
        textInput_id = findViewById(R.id.textInput_id);
        textInput_password = findViewById(R.id.textInput_password);
        bt_logIn = findViewById(R.id.bt_logIn);
        parentLayout = findViewById(R.id.parentLayout);

        ((LoginActivity.this)).getSupportActionBar().setTitle("Log In");

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED, false);
        dialog = new Dialog(LoginActivity.this);

        Log.d("isLogged", String.valueOf(isLogged));
        Log.d("intentValLA", preferences.getString(SharedPref.INTENT_VALUE, null));


        if (isLogged) {
            intentVal();
//            finish();
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        bt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (TextUtils.isEmpty(ed_emailId.getText())) {
                    ed_emailId.setError("Enter Email Id");
                    return;
                }
                if (!emailValidator(ed_emailId.getText().toString())) {
                    ed_emailId.setError("Enter Valid Email");
                    return;
                }
                if (TextUtils.isEmpty(ed_password.getText())) {
                    ed_password.setError("Enter Password");
                    return;
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Logging You In");
                    pd.setCancelable(false);
                    pd.show();
                    logIn();
                } else {
                    snackText = "No Network Connection";
                    snackBar(snackText);
                }
//                if (dataBaseHandler.getAddressTable().size()>0){
//                    startActivity(new Intent(LoginActivity.this,ShippingDetailsActivity.class));
//                }else {
//                    startActivity(new Intent(LoginActivity.this, AddressActivity.class));
//                }
            }
        });
    }


    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;

        if (email.length() > 0) {
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            Log.d("matcher", String.valueOf(matcher.matches()));
            return matcher.matches();

        } else {
            return true;
        }


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, LoginSignUpPageActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    public void logIn() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res1", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    String mes = jsonObject.optString("message");
                    if (jsonObject.optString("status").equals("true") && jsonObject.optString("verified").equals("true")) {
                        JSONObject object = jsonObject.getJSONObject("user");
                        snackBar(object.optString("message"));
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SharedPref.USERNAME, object.optString("user_fname") + " " + object.optString("user_lname"));
                        editor.putString(SharedPref.USER_FIRST_NAME, object.optString("user_fname"));
                        editor.putString(SharedPref.USER_LAST_NAME, object.optString("user_lname"));
                        editor.putString(SharedPref.PASSWORD, object.optString("user-password"));
                        editor.putString(SharedPref.USER_ID, object.optString("userid"));
                        String phone = object.optString("user_phone");
                        String data = phone.substring(phone.indexOf(" ") + 1);
                        Log.d("phoneNumber", data);
                        editor.putString(SharedPref.USER_MOBILE, data);
                        editor.putString(SharedPref.USER_EMAIL_ID, object.optString("user_email"));
                        editor.putString(SharedPref.USER_GENDER, object.optString("user_gender"));
                        editor.putBoolean(SharedPref.IS_LOGGED, true);
                        editor.commit();
//                        Toast.makeText(LoginActivity.this, ""+preferences.getString(SharedPref.USERNAME,null), Toast.LENGTH_LONG).show();
                        intentVal();

                    } else if (jsonObject.optString("verified").equals("false")) {
                        pd.cancel();
                        Toast.makeText(LoginActivity.this, "Please Verify Your Email First", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, OTPActivity.class).putExtra("emailId", ed_emailId.getText().toString()));
//                        snackBar(snackText);
                    } else {
                        pd.cancel();
                        snackText = "Invalid Email or Password";
                        snackBar(snackText);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("exception", String.valueOf(error));
                snackText = "Technical Issue Come Back Later";
                snackBar(snackText);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPreferences spf = getSharedPreferences("udid", MODE_PRIVATE);
//                params.put("action", "login");
                params.put("udid", spf.getString(SharedPref.PHONE_ID, null));
                params.put("email", ed_emailId.getText().toString());
                params.put("password", ed_password.getText().toString());
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

//    public void getAddress() {
//        dialog.setContentView(R.layout.progress_bar_layout);
//        dialog.setCancelable(true);
//        dialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_ADDRESS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("resnull", response);
//                if (response != null) {
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        dataBaseHandler.deleteFromAddressTable();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putBoolean(SharedPref.HAS_ADDRESS, true);
//                            editor.commit();
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            addressModel.setKey(Integer.parseInt(jsonObject.optString("address_id")));
//                            addressModel.setName(jsonObject.optString("user_name"));
//                            String val = jsonObject.optString("phone_number");
//                            addressModel.setStd_code(val.substring(0, val.indexOf(" ")));
//                            addressModel.setMobile(val.substring(val.indexOf(" ") + 1));
//                            Log.d("value1", val.substring(0, val.indexOf(" ")));
//                            Log.d("value2", val.substring(val.indexOf(" ")));
//                            addressModel.setPin_code(jsonObject.optString("pincode"));
//                            addressModel.setBuilding(jsonObject.optString("building"));
//                            addressModel.setTown(jsonObject.optString("town"));
//                            addressModel.setDistrict(jsonObject.optString("district"));
//                            addressModel.setState(jsonObject.optString("state"));
//                            addressModel.setCountry(jsonObject.optString("country"));
//                            addressModel.setAddress_type(jsonObject.optString("address_type"));
//                            dataBaseHandler.addAddress(addressModel);
//                            finish();
//                            dialog.cancel();
//                            startActivity(new Intent(LoginActivity.this, ShippingDetailsActivity.class));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    dialog.cancel();
//                    startActivity(new Intent(LoginActivity.this, AddressActivity.class));
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                startActivity(new Intent(LoginActivity.this, AddressActivity.class));
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                Log.d("paramsLog", String.valueOf(params));
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//        queue.add(stringRequest);
//    }

//    public void getCart() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_CART, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("res2", response);
//
//                try {
//                    if (response.startsWith("{")) {
//                        JSONObject jsonObject = new JSONObject(response);
//                        if (jsonObject.optString("message").equals("No data found")) {
//                            for (int i = 0; i < db.getCartTable().size(); i++) {
//                                pos = i;
//                            }
//
//                        }
//                        Log.d("abc", "if part");
//
//                    } else {
//                        addCart();
//                        Log.d("abc", "else part");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                Log.d("paramsLog", String.valueOf(params));
//                return params;
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//        queue.add(stringRequest);
//    }

//    public void addCart() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_TO_CART, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("res3", response);
//                pd.cancel();
//                getAddress();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("action", "add");
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                params.put("product_id", String.valueOf(db.getCartTable().get(pos).getProd_cart_id()));
//                params.put("quantity", String.valueOf(db.getCartTable().get(pos).getProd_qty()));
//                Log.d("paramsLog", String.valueOf(params));
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//        queue.add(stringRequest);
//    }

    public void snackBar(String text) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = View.inflate(LoginActivity.this, R.layout.my_snackbar, null);
        // Configure the view

        TextView textViewTop = (TextView) snackView.findViewById(R.id.textViewTop);
        textViewTop.setText(text);
        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        snackbar.show();
    }

    public void intentVal() {
        Log.d("intentVal", preferences.getString(SharedPref.INTENT_VALUE, null));
        switch (preferences.getString(SharedPref.INTENT_VALUE, null)) {
            case "Account":
                finish();
                startActivity(new Intent(LoginActivity.this, AccountDetailsActivity.class));
                break;
            case "Cart":
                finish();
                startActivity(new Intent(LoginActivity.this, CartActivity.class));
                break;
            case "Order":
                finish();
                startActivity(new Intent(LoginActivity.this, OrderActivity.class));
                break;
            case "WishList":
                finish();
                startActivity(new Intent(LoginActivity.this, WishListActivity.class));
                break;

//
        }
    }


}
