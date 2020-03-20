package com.app.iccomm.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.app.iccomm.Activity.AddressActivity;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAddressAdapter extends RecyclerView.Adapter<ShowAddressAdapter.ShowAddressViewHolder> {
    Context mContext;
    List<AddressModel> mList = new ArrayList<>();
    SharedPreferences preferences;

    public ShowAddressAdapter(Context mContext, List<AddressModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public ShowAddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_address_recycler_view, viewGroup, false);
        return new ShowAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowAddressViewHolder holder, final int i) {
        preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        holder.idAddress.setText(String.valueOf(mList.get(i).getKey()));
        holder.tv_name.setText(mList.get(i).getName());
        holder.tv_addressType.setText(mList.get(i).getAddress_type());
        holder.tv_fullAddBuilding.setText(mList.get(i).getBuilding());
        holder.tv_fullAddTown.setText(mList.get(i).getTown());
        holder.tv_fullAddDistrict.setText(mList.get(i).getDistrict());
        holder.tv_fullAddState.setText(mList.get(i).getState());
        holder.tv_fullAddCountry.setText(mList.get(i).getCountry());
        holder.tv_fullAddPinCode.setText(mList.get(i).getPin_code());
        holder.tv_fullAddNumber.setText(mList.get(i).getMobile());

        Log.d("keyAddress", String.valueOf(mList.get(i).getKey()));

        holder.imgView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, AddressActivity.class);
                mIntent.putExtra("key", String.valueOf(mList.get(i).getKey()));
                mIntent.putExtra("name", mList.get(i).getName());
                mIntent.putExtra("std_code", mList.get(i).getStd_code());
                mIntent.putExtra("number", mList.get(i).getMobile());
                mIntent.putExtra("pinCode", mList.get(i).getPin_code());
                mIntent.putExtra("building", mList.get(i).getBuilding());
                mIntent.putExtra("town", mList.get(i).getTown());
                mIntent.putExtra("district", mList.get(i).getDistrict());
                mIntent.putExtra("state", mList.get(i).getState());
                mIntent.putExtra("country", mList.get(i).getCountry());
                mIntent.putExtra("addressType", mList.get(i).getAddress_type());
                mIntent.putExtra("val", "Edit");
                mIntent.putExtra("Activity","ShowAddress");
                ((Activity)mContext).finish();
                mContext.startActivity(mIntent);
            }
        });

        holder.imgView_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation right_exit = AnimationUtils.loadAnimation(mContext, R.anim.right_exit);
                holder.cv_address.startAnimation(right_exit);
                final Dialog dialog = new Dialog(mContext);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.cv_address.setVisibility(View.GONE);
                        dialog.cancel();
                    }
                }, 500);
                dialog.setContentView(R.layout.progress_bar_layout);
                dialog.setCancelable(true);
                dialog.show();
                deleteAddress(String.valueOf(mList.get(i).getKey()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ShowAddressViewHolder extends RecyclerView.ViewHolder {
        TextView idAddress, tv_name, tv_fullAddBuilding, tv_fullAddTown, tv_fullAddDistrict, tv_fullAddState, tv_fullAddCountry, tv_fullAddPinCode, tv_fullAddNumber, tv_addressType;
        CardView cv_address;
        ImageView imgView_edit, imgView_remove;

        public ShowAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            idAddress = itemView.findViewById(R.id.idAddress);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_fullAddTown = itemView.findViewById(R.id.tv_fullAddTown);
            tv_fullAddDistrict = itemView.findViewById(R.id.tv_fullAddDistrict);
            tv_fullAddState = itemView.findViewById(R.id.tv_fullAddState);
            tv_fullAddCountry = itemView.findViewById(R.id.tv_fullAddCountry);
            tv_fullAddPinCode = itemView.findViewById(R.id.tv_fullAddPinCode);
            tv_fullAddBuilding = itemView.findViewById(R.id.tv_fullAddBuilding);
            tv_fullAddNumber = itemView.findViewById(R.id.tv_fullAddNumber);
            tv_addressType = itemView.findViewById(R.id.tv_addressType);
            cv_address = itemView.findViewById(R.id.cv_address);
            imgView_edit = itemView.findViewById(R.id.imgView_edit);
            imgView_remove = itemView.findViewById(R.id.imgView_remove);

        }
    }


    private void deleteAddress(final String key) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseDelete",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(mContext, "Address Deleted", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("id", key);
                Log.d("paramLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }


}
