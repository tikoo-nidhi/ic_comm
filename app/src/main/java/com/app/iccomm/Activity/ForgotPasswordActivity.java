package com.app.iccomm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView left_arrow_appBar, imgView_shoppingCart;
    TextView title;
    EditText ed_emailId;
    Button bt_resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        title = findViewById(R.id.title);
        ed_emailId = findViewById(R.id.ed_emailId);
        bt_resetPassword = findViewById(R.id.bt_resetPassword);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ForgotPasswordActivity.this)).getSupportActionBar().setTitle("");

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);

        imgView_shoppingCart.setVisibility(View.GONE);
        title.setText("Reset Password");
        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((ForgotPasswordActivity.this)).getSupportActionBar().setTitle("");

        bt_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed_emailId.getText())){
                    ed_emailId.setError("Enter Email id");
                    return;
                } if (!emailValidator(ed_emailId.getText().toString())) {
                    ed_emailId.setError("Enter Valid Email");
                    return;
                }
                forgotPassword();
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

    public void forgotPassword(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_FORGOT_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")){
                        Toast.makeText(ForgotPasswordActivity.this, "Password sent to registered email id", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                    }else {
                        Toast.makeText(ForgotPasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ForgotPasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
//                params.put("action","resetpassword");
//                params.put("user_id", "1");
                params.put("email",ed_emailId.getText().toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        queue.add(stringRequest);
    }
}
