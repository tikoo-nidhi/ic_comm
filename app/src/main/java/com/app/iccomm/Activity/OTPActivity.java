package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPActivity extends AppCompatActivity {
    EditText ed_otp;
    Button bt_submitOTP;
    TextView tv_resendOTP;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        ed_otp = findViewById(R.id.ed_otp);
        bt_submitOTP = findViewById(R.id.bt_submitOTP);
        tv_resendOTP = findViewById(R.id.tv_resendOTP);

        dialog = new Dialog(OTPActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(false);


        tv_resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
                dialog.show();
            }
        });


        bt_submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed_otp.getText())){
                    ed_otp.setError("Enter OTP");
                    return;
                }
                verifyOTP();
                dialog.show();
            }
        });

    }

    public void verifyOTP(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_VERIFY_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseVerify",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")&&jsonObject.optString("verified").equals("true")){
                        Toast.makeText(OTPActivity.this, "Verified Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(OTPActivity.this,LoginActivity.class));
                    }else {
                        Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        ed_otp.setError("Invalid OTP");
                    }
                    dialog.cancel();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OTPActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OTPActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",getIntent().getStringExtra("emailId"));
                params.put("otp",ed_otp.getText().toString());

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(OTPActivity.this);
        queue.add(stringRequest);
    }

    public void resendOTP(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_RESEND_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseResend",response);
                dialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Log.d("errorOTP", String.valueOf(error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",getIntent().getStringExtra("emailId"));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(OTPActivity.this);
        queue.add(stringRequest);
    }
}
