package com.app.iccomm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.R;

public class LoginSignUpPageActivity extends AppCompatActivity {
    TextView tv_logIn, tv_signUp, tv_guestCheckOut, title;
    ImageView left_arrow_appBar, imgView_shoppingCart;
    Boolean isLogged;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_page);
        tv_logIn = findViewById(R.id.tv_logIn);
        tv_signUp = findViewById(R.id.tv_signUp);
        tv_guestCheckOut = findViewById(R.id.tv_guestCheckOut);
        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        title = findViewById(R.id.title);

        preferences = getSharedPreferences("data",MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED,false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgView_shoppingCart.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        ((LoginSignUpPageActivity.this)).getSupportActionBar().setTitle("");

        Log.d("intentValLSA",preferences.getString(SharedPref.INTENT_VALUE, null));

        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginSignUpPageActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

            }
        });

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginSignUpPageActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        tv_guestCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignUpPageActivity.this, AddressActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(LoginSignUpPageActivity.this,MainActivity.class));
    }
}
