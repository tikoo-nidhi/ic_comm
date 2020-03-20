package com.app.iccomm.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPassword, newPassword, reEnterNewPassword;
    Button bt_changePass;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        reEnterNewPassword = findViewById(R.id.reEnterNewPassword);
        bt_changePass = findViewById(R.id.bt_changePass);

        dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(false);

        bt_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(oldPassword.getText())) {
                    oldPassword.setError("Field can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(newPassword.getText())) {
                    newPassword.setError("Field can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(reEnterNewPassword.getText())) {
                    reEnterNewPassword.setError("Field can't be empty");
                    return;
                }
                if (!newPassword.getText().toString().equals(reEnterNewPassword.getText().toString())){
                    reEnterNewPassword.setError("Password Mismatch");
                    return;
                }
                changePassword();
                dialog.show();
            }
        });

    }

    public void changePassword(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_CHANGE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("True")){
                        Toast.makeText(ChangePasswordActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.cancel();
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ChangePasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.USER_ID,null));
                params.put("current_pass",oldPassword.getText().toString());
                params.put("password",reEnterNewPassword.getText().toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
        queue.add(stringRequest);
    }
}
