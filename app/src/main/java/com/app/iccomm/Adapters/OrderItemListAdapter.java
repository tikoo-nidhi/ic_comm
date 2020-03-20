package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.OrderModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.OrderItemListViewHolder> {
    Context mContext;
    List<OrderModel> modelList = new ArrayList<>();

    public OrderItemListAdapter(Context mContext, List<OrderModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public OrderItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_details_recycler_view, viewGroup, false);
        return new OrderItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListViewHolder holder, int i) {
        Glide.with(mContext).load(modelList.get(i).getOrderImg()).into(holder.order_item_img);
        holder.order_item_name.setText(modelList.get(i).getOrderName());
        holder.order_item_price.setText(mContext.getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + modelList.get(i).getOrderPrice());
        holder.order_item_qty.setText(modelList.get(i).getOrderQty());
        holder.order_item_sub_total.setText(mContext.getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + modelList.get(i).getOrderSubTotal());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class OrderItemListViewHolder extends RecyclerView.ViewHolder {

        ImageView order_item_img;
        TextView order_item_name, order_item_price, order_item_qty, order_item_sub_total;

        public OrderItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            order_item_img = itemView.findViewById(R.id.order_item_img);
            order_item_name = itemView.findViewById(R.id.order_item_name);
            order_item_price = itemView.findViewById(R.id.order_item_price);
            order_item_qty = itemView.findViewById(R.id.order_item_qty);
            order_item_sub_total = itemView.findViewById(R.id.order_item_sub_total);
        }
    }
}
