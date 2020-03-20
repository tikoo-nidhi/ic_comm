package com.app.iccomm.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.app.iccomm.Activity.FilterActivity;
import com.app.iccomm.Activity.ShippingDetailsActivity;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDetailsAdapter extends RecyclerView.Adapter<AddressDetailsAdapter.AddressDetailsViewHolder> {

    private Context mContext;
    private List<AddressModel> addressModels = new ArrayList<>();
    private int lastSelectedPosition = -1;
    private AddressModel addressModel = new AddressModel();

    public AddressDetailsAdapter(Context mContext, List<AddressModel> addressModels) {
        this.mContext = mContext;
        this.addressModels = addressModels;
    }

    @NonNull
    @Override
    public AddressDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_details_view, viewGroup, false);
        return new AddressDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressDetailsViewHolder holder, final int position) {
        Log.d("position", String.valueOf(position));
//        final DataBaseHandler dataBaseHandler = new DataBaseHandler(mContext);


        holder.radioButton_name.setChecked(position == lastSelectedPosition);

        holder.radioButton_name.setText(addressModels.get(position).getName());
        holder.tv_addressType.setText(addressModels.get(position).getAddress_type());
        holder.tv_halfAddBuilding.setText(addressModels.get(position).getBuilding());
        holder.tv_fullAddBuilding.setText(addressModels.get(position).getBuilding());
        holder.tv_halfAddTown.setText(addressModels.get(position).getTown());
        holder.tv_fullAddTown.setText(addressModels.get(position).getTown());
        holder.tv_halfAddDistrict.setText(addressModels.get(position).getDistrict());
        holder.tv_fullAddDistrict.setText(addressModels.get(position).getDistrict());
        holder.tv_halfAddState.setText(addressModels.get(position).getState());
        holder.tv_fullAddState.setText(addressModels.get(position).getState());
        holder.tv_fullAddCountry.setText(addressModels.get(position).getCountry());
        holder.tv_halfAddPinCode.setText(addressModels.get(position).getPin_code());
        holder.tv_fullAddPinCode.setText(addressModels.get(position).getPin_code());
        holder.tv_fullAddNumber.setText(addressModels.get(position).getMobile());
//        if (dataBaseHandler.getAddressTable().size() == 1) {
//            holder.radioButton_name.setChecked(true);
//        }

        holder.imgView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, AddressActivity.class);
                mIntent.putExtra("key", String.valueOf(addressModels.get(position).getKey()));
                mIntent.putExtra("name", addressModels.get(position).getName());
                mIntent.putExtra("std_code", addressModels.get(position).getStd_code());
                mIntent.putExtra("number", addressModels.get(position).getMobile());
                mIntent.putExtra("pinCode", addressModels.get(position).getPin_code());
                mIntent.putExtra("building", addressModels.get(position).getBuilding());
                mIntent.putExtra("town", addressModels.get(position).getTown());
                mIntent.putExtra("district", addressModels.get(position).getDistrict());
                mIntent.putExtra("state", addressModels.get(position).getState());
                mIntent.putExtra("country", addressModels.get(position).getCountry());
                mIntent.putExtra("addressType", addressModels.get(position).getAddress_type());
                mIntent.putExtra("val", "Edit");
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
                deleteAddress(String.valueOf(addressModels.get(position).getKey()));
                Toast.makeText(mContext, "Address Removed", Toast.LENGTH_SHORT).show();

            }
        });

        if (holder.radioButton_name.isChecked()) {
            holder.fullAddress.setVisibility(View.VISIBLE);
            holder.halfAddress.setVisibility(View.GONE);
            holder.view_edit_remove.setVisibility(View.VISIBLE);
            holder.edit_remove.setVisibility(View.VISIBLE);
            addressModels.get(position).setSelectAddress(true);
            ((ShippingDetailsActivity) mContext).display(addressModels);
        } else {
            holder.fullAddress.setVisibility(View.GONE);
            holder.halfAddress.setVisibility(View.VISIBLE);
            holder.view_edit_remove.setVisibility(View.GONE);
            holder.edit_remove.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class AddressDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_halfAddBuilding, tv_halfAddTown, tv_halfAddDistrict, tv_halfAddState, tv_halfAddPinCode, tv_fullAddBuilding,
                tv_fullAddTown, tv_fullAddDistrict, tv_fullAddState, tv_fullAddCountry, tv_fullAddPinCode, tv_fullAddNumber, tv_addressType;
        RadioButton radioButton_name;
        LinearLayout halfAddress, fullAddress, edit_remove;
        View view_edit_remove;
        ImageView imgView_edit, imgView_remove;
        CardView cv_address;

        public AddressDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton_name = itemView.findViewById(R.id.radioButton_name);
            halfAddress = itemView.findViewById(R.id.halfAddress);
            fullAddress = itemView.findViewById(R.id.fullAddress);
            edit_remove = itemView.findViewById(R.id.edit_remove);
            view_edit_remove = itemView.findViewById(R.id.view_edit_remove);
            tv_halfAddBuilding = itemView.findViewById(R.id.tv_halfAddBuilding);
            tv_halfAddTown = itemView.findViewById(R.id.tv_halfAddTown);
            tv_halfAddDistrict = itemView.findViewById(R.id.tv_halfAddDistrict);
            tv_halfAddState = itemView.findViewById(R.id.tv_halfAddState);
            tv_halfAddPinCode = itemView.findViewById(R.id.tv_halfAddPinCode);
            tv_fullAddTown = itemView.findViewById(R.id.tv_fullAddTown);
            tv_fullAddDistrict = itemView.findViewById(R.id.tv_fullAddDistrict);
            tv_fullAddState = itemView.findViewById(R.id.tv_fullAddState);
            tv_fullAddCountry = itemView.findViewById(R.id.tv_fullAddCountry);
            tv_fullAddPinCode = itemView.findViewById(R.id.tv_fullAddPinCode);
            tv_fullAddBuilding = itemView.findViewById(R.id.tv_fullAddBuilding);
            tv_fullAddNumber = itemView.findViewById(R.id.tv_fullAddNumber);
            tv_addressType = itemView.findViewById(R.id.tv_addressType);
            imgView_edit = itemView.findViewById(R.id.imgView_edit);
            imgView_remove = itemView.findViewById(R.id.imgView_remove);
            cv_address = itemView.findViewById(R.id.cv_address);

            radioButton_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    fullAddress.setVisibility(View.VISIBLE);
                    halfAddress.setVisibility(View.GONE);
                    view_edit_remove.setVisibility(View.VISIBLE);
                    edit_remove.setVisibility(View.VISIBLE);
                }
            });


        }
    }

    private void deleteAddress(final String key){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")){
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String,String> params = new HashMap<>();
                params.put("id",key);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

}
