package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.OrderDetailsModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder> {
    Context mContext;
    List<OrderDetailsModel> mList = new ArrayList<>();

    public OrderDetailsAdapter(Context mContext, List<OrderDetailsModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item_view, viewGroup, false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int i) {
        SharedPreferences spf = mContext.getSharedPreferences("cur",Context.MODE_PRIVATE);
        Glide.with(mContext).load(mList.get(i).getOrderImg()).into(holder.orderImg);
        holder.tv_orderName.setText(mList.get(i).getOrderName());
        holder.tv_orderPrice.setText(spf.getString(SharedPref.CURRENCY,null)+mList.get(i).getOrderPrice());
        holder.tv_orderQty.setText("Qty: "+mList.get(i).getOrderQty());
        holder.tv_orderSubtotal.setText("Subtotal: "+spf.getString(SharedPref.CURRENCY,null)+mList.get(i).getOrderSubTotal());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImg;
        TextView tv_orderName, tv_orderPrice, tv_orderQty, tv_orderSubtotal;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImg = itemView.findViewById(R.id.orderImg);
            tv_orderName = itemView.findViewById(R.id.tv_orderName);
            tv_orderPrice = itemView.findViewById(R.id.tv_orderPrice);
            tv_orderQty = itemView.findViewById(R.id.tv_orderQty);
            tv_orderSubtotal = itemView.findViewById(R.id.tv_orderSubtotal);
        }
    }
}
