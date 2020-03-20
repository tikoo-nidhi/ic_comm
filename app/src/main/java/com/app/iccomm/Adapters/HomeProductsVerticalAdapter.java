package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeProductsVerticalAdapter extends RecyclerView.Adapter<HomeProductsVerticalAdapter.HomeProductsVerticalViewHolder> {
    Context mContext;
    private List<HomePageModel> modelList = new ArrayList<>();


    public HomeProductsVerticalAdapter(Context mContext, List<HomePageModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeProductsVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_screen_products_vertical, viewGroup, false);
        return new HomeProductsVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductsVerticalViewHolder holder, final int i) {
        holder.tv_name_homeProducts_vertical.setText(modelList.get(i).getHot_featured_name());
        holder.tv_actual_price_homeProducts_vertical.setText(modelList.get(i).getHot_featured_price());
        if (modelList.get(i).getHot_featured_discount().startsWith("\u20B90")||modelList.get(i).getHot_featured_discount().startsWith("0")){
            holder.tv_discount_percentage.setVisibility(View.GONE);
            holder.tv_actual_price_homeProducts_vertical.setVisibility(View.GONE);
        }
        holder.tv_actual_price_homeProducts_vertical.setPaintFlags(holder.tv_actual_price_homeProducts_vertical.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_discount_percentage.setText(modelList.get(i).getHot_featured_discount().concat("\noff"));
        holder.tv_discounted_price_homeProducts_vertical.setText(modelList.get(i).getHot_featured_discounted_price());
        Glide.with(mContext).load(modelList.get(i).getHot_featured_img()).into(holder.iv_homeProduct_vertical);

        holder.whole_ll_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductDetails.class);
                mIntent.putExtra("prodId",modelList.get(i).getHot_featured_id());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class HomeProductsVerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_homeProduct_vertical;
        TextView tv_actual_price_homeProducts_vertical, tv_name_homeProducts_vertical, tv_discount_percentage, tv_discounted_price_homeProducts_vertical;
        CheckedTextView ctv_wish_homeProducts_vertical;
        LinearLayout whole_ll_lay;

        public HomeProductsVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_homeProduct_vertical = itemView.findViewById(R.id.iv_homeProduct_vertical);
            tv_actual_price_homeProducts_vertical = itemView.findViewById(R.id.tv_actual_price_homeProducts_vertical);
            tv_name_homeProducts_vertical = itemView.findViewById(R.id.tv_name_homeProducts_vertical);
            ctv_wish_homeProducts_vertical = itemView.findViewById(R.id.ctv_wish_homeProducts_vertical);
            tv_discount_percentage = itemView.findViewById(R.id.tv_discount_percentage);
            tv_discounted_price_homeProducts_vertical = itemView.findViewById(R.id.tv_discounted_price_homeProducts_vertical);
            whole_ll_lay = itemView.findViewById(R.id.whole_ll_lay);
        }
    }
}
