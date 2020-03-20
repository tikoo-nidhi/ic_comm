package com.app.iccomm.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText ed_firstName, ed_lastName, ed_mailId, ed_password, ed_mobileNumber;
    RadioGroup radioGroupGender;
    RadioButton rb;
    Button bt_createAccount;
    TextView tv_gender;
    ImageView errorImg;
    int selectedId;
    String selected_radiobutton;
    SharedPreferences sharedPreferences;
    Boolean isLogged;
    LinearLayout parentLayout;
    String snackText, countryCode;
    CountryCodePicker ccp_signUp;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ed_firstName = findViewById(R.id.ed_firstName);
        ed_lastName = findViewById(R.id.ed_lastName);
        ed_mailId = findViewById(R.id.ed_mailId);
        ed_password = findViewById(R.id.ed_password);
        ed_mobileNumber = findViewById(R.id.ed_mobileNumber);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        bt_createAccount = findViewById(R.id.bt_createAccount);
        tv_gender = findViewById(R.id.tv_gender);
        errorImg = findViewById(R.id.errorImg);
        parentLayout = findViewById(R.id.parentLayout);
        ccp_signUp = findViewById(R.id.ccp_signUp);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(SharedPref.IS_LOGGED, false);
        countryCode = ccp_signUp.getSelectedCountryCode();

        ccp_signUp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                countryCode = ccp_signUp.getSelectedCountryCode();
                Log.d("countryCode", countryCode);
            }
        });
        Log.d("countryCode", countryCode);


        ((SignUpActivity.this)).getSupportActionBar().setTitle("Sign Up");
        bt_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                if (TextUtils.isEmpty(ed_firstName.getText())) {
                    ed_firstName.setError("Enter First Name");
                    ed_firstName.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_lastName.getText())) {
                    ed_lastName.setError("Enter Last Name");
                    ed_lastName.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_mailId.getText())) {
                    ed_mailId.setError("Enter Mail Id");
                    ed_mailId.requestFocus();
                    shakeIt();
                    return;
                }
                if (!emailValidator(ed_mailId.getText().toString())) {
                    ed_mailId.setError("Enter Valid Mail Id");
                    ed_mailId.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_password.getText())) {
                    ed_password.setError("Enter Password");
                    ed_password.requestFocus();
                    shakeIt();
                    return;
                }
                if (TextUtils.isEmpty(ed_mobileNumber.getText())) {
                    ed_mobileNumber.setError("Enter Number");
                    ed_mobileNumber.requestFocus();
                    shakeIt();
                    return;
                }
                if (ed_mobileNumber.length() < 10) {
                    ed_mobileNumber.setError("Enter a Valid Number");
                    ed_mobileNumber.requestFocus();
                    shakeIt();
                    return;
                }
                if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    tv_gender.requestFocus();
                    errorImg.setVisibility(View.VISIBLE);
                    Animation shakeAnim = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
                    tv_gender.startAnimation(shakeAnim);
                    shakeIt();
                    return;
                } else {
                    errorImg.setVisibility(View.GONE);
                    selectedId = radioGroupGender.getCheckedRadioButtonId();
                    rb = findViewById(selectedId);
                    selected_radiobutton = rb.getText().toString();
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    pd = new ProgressDialog(SignUpActivity.this);
                    pd.setMessage("Creating Account");
                    pd.setCancelable(false);
                    pd.show();
                    signUp();
                } else {
                    snackText = "No Network Connection";
                    snackBar(snackText);
                }
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
        startActivity(new Intent(SignUpActivity.this, LoginSignUpPageActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    public void signUp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseSignUp", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(SignUpActivity.this, "Please Verify Your Email" , Toast.LENGTH_SHORT).show();
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(SharedPref.USERNAME, ed_fullName.getText().toString());
//                        editor.putString(SharedPref.PASSWORD, ed_password.getText().toString());
//                        editor.putString(SharedPref.USER_ID, String.valueOf(jsonObject.optInt("userid")));
//                        editor.putString(SharedPref.USER_EMAIL_ID, ed_mailId.getText().toString());
//                        editor.putString(SharedPref.USER_MOBILE, ed_mobileNumber.getText().toString());
//                        editor.putString(SharedPref.USER_GENDER, selected_radiobutton);
//                        editor.putBoolean(SharedPref.IS_LOGGED, true);
//                        editor.commit();
                        pd.cancel();
                        finish();
                        startActivity(new Intent(SignUpActivity.this, OTPActivity.class).putExtra("emailId",ed_mailId.getText().toString()));
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                    } else {
                        pd.cancel();
                        snackText = "Account Already Exists";
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
                snackText = "Technical Issue Come Back Later";
                Log.e("VolleyError", String.valueOf(error));
                snackBar(snackText);

            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "register");
                params.put("fname", ed_firstName.getText().toString());
                params.put("lname", ed_lastName.getText().toString());
                params.put("password", ed_password.getText().toString());
                params.put("email", ed_mailId.getText().toString());
                params.put("mobile", "+" + countryCode + " " + ed_mobileNumber.getText().toString());
                params.put("gender", selected_radiobutton);
                Log.d("params", String.valueOf(params));
                return params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void snackBar(String text) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = View.inflate(SignUpActivity.this, R.layout.my_snackbar, null);
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
}
