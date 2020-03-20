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

public class HomeProductsHorizontalAdapter extends RecyclerView.Adapter<HomeProductsHorizontalAdapter.HomeProductsHorizontalViewHolder> {

    Context mContext;
    private List<HomePageModel> modelList = new ArrayList<>();

    public HomeProductsHorizontalAdapter(Context mContext, List<HomePageModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeProductsHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_products_horizontal, viewGroup, false);
        return new HomeProductsHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductsHorizontalViewHolder holder, final int i) {
        holder.tv_name_homeProducts_horizontal.setText(modelList.get(i).getNewProd_recent_name());
        holder.tv_actual_price_homeProducts_horizontal.setText(modelList.get(i).getNewProd_recent_price());
        if (modelList.get(i).getNewProd_recent_discount().startsWith("\u20B90")||modelList.get(i).getNewProd_recent_discount().startsWith("0")){
            holder.tv_discount_percentage.setVisibility(View.GONE);
            holder.tv_actual_price_homeProducts_horizontal.setVisibility(View.GONE);
        }
        holder.tv_actual_price_homeProducts_horizontal.setPaintFlags(holder.tv_actual_price_homeProducts_horizontal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_discount_percentage.setText(modelList.get(i).getNewProd_recent_discount().concat("\noff"));
        holder.tv_discounted_price_homeProducts_horizontal.setText(modelList.get(i).getNewProd_recent_discounted_price());
        Glide.with(mContext).load(modelList.get(i).getNewProd_recent_img()).into(holder.iv_homeProduct_horizontal);

        holder.whole_ll_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductDetails.class);
                mIntent.putExtra("prodId",modelList.get(i).getNewProd_recent_id());
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class HomeProductsHorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_homeProduct_horizontal;
        TextView tv_actual_price_homeProducts_horizontal, tv_name_homeProducts_horizontal, tv_discount_percentage, tv_discounted_price_homeProducts_horizontal;
        CheckedTextView ctv_wish_homeProducts_horizontal;
        LinearLayout whole_ll_lay;

        public HomeProductsHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            whole_ll_lay = itemView.findViewById(R.id.whole_ll_lay);
            iv_homeProduct_horizontal = itemView.findViewById(R.id.iv_homeProduct_horizontal);
            tv_actual_price_homeProducts_horizontal = itemView.findViewById(R.id.tv_actual_price_homeProducts_horizontal);
            tv_name_homeProducts_horizontal = itemView.findViewById(R.id.tv_name_homeProducts_horizontal);
            ctv_wish_homeProducts_horizontal = itemView.findViewById(R.id.ctv_wish_homeProducts_horizontal);
            tv_discount_percentage = itemView.findViewById(R.id.tv_discount_percentage);
            tv_discounted_price_homeProducts_horizontal = itemView.findViewById(R.id.tv_discounted_price_homeProducts_horizontal);
        }
    }
}
