package com.app.iccomm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.iccomm.Activity.OrderDetailsActivity;
import com.app.iccomm.Model.OrderModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context mContext;
    List<OrderModel> mList = new ArrayList<>();


    public OrderAdapter(Context mContext, List<OrderModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_view, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int i) {
        holder.cardView_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, OrderDetailsActivity.class);
                mIntent.putExtra("orderId",mList.get(i).getOrderId());
                mContext.startActivity(mIntent);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        holder.orderId.setText(mList.get(i).getOrderId());
        holder.orderStatus.setText(mList.get(i).getOrderStatus());
        holder.orderPrice.setText(mList.get(i).getOrderPrice());
        holder.orderDate.setText(mList.get(i).getOrderDate());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        CardView cardView_orders;
        ImageView orderImg, imgArrow;
        TextView orderId, orderStatus, orderPrice, orderDate;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_orders = itemView.findViewById(R.id.cardView_orders);
            imgArrow = itemView.findViewById(R.id.imgArrow);
            orderImg = itemView.findViewById(R.id.orderImg);
            orderId = itemView.findViewById(R.id.orderId);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderDate = itemView.findViewById(R.id.orderDate);
        }
    }



}
