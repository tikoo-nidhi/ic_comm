package com.app.iccomm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.iccomm.Activity.MainActivity;

public class NoNetwork {


    private Dialog dialog;
    private TextView retry;
    private View view;
    private LottieAnimationView lottie_anim;
    Context mContext;

    public void noNet(final Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.no_internet);
        retry = dialog.findViewById(R.id.retry);
        final TextView tv_no_connectionFound = dialog.findViewById(R.id.tv_no_connectionFound);
        final ImageView iv_no_wifi = dialog.findViewById(R.id.iv_no_wifi);
        lottie_anim = dialog.findViewById(R.id.lottie_anim);
        view = dialog.findViewById(R.id.view);
        dialog.setCancelable(false);
//        checkConnectivity(activity);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                tv_no_connectionFound.setVisibility(View.GONE);
                iv_no_wifi.setVisibility(View.GONE);
                lottie_anim.playAnimation();
                lottie_anim.setVisibility(View.VISIBLE);
                checkConnectivity(activity);
            }
        });
//        Log.d("returnVal", String.valueOf(checkConnectivity(activity)));

    }

    public void checkConnectivity(final Activity activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            },5000);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lottie_anim.cancelAnimation();
                    view.setVisibility(View.VISIBLE);
                    retry.setVisibility(View.VISIBLE);
                }
            },10000);
            dialog.show();


        }


    }


}
