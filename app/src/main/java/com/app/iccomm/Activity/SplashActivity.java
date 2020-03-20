package com.app.iccomm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.R;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("androidId", id);

        SharedPreferences preferences = getSharedPreferences("udid", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedPref.PHONE_ID, id);
        editor.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }
}
