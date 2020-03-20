package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.app.iccomm.R;

public class PayPalPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment);

        final Dialog dialog = new Dialog(PayPalPaymentActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(PayPalPaymentActivity.this);
                dialog.setContentView(R.layout.order_placed_dialog);
                dialog.setCancelable(true);
                dialog.show();
                finish();
                startActivity(new Intent(PayPalPaymentActivity.this, MainActivity.class));
            }
        }, 500);

    }
}

