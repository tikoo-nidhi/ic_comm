package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iccomm.Activity.BrandActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeBrandAdapter extends RecyclerView.Adapter<HomeBrandAdapter.HomeBrandViewHolder> {

    Context mContext;
    List<HomePageModel> modelList = new ArrayList<>();

    public HomeBrandAdapter(Context mContext, List<HomePageModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeBrandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_brand, viewGroup, false);
        return new HomeBrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBrandViewHolder holder, final int i) {
        holder.setIsRecyclable(false);
        if (modelList.get(i).getBrand_img().equals("null")) {
            holder.iv_brandImg.setVisibility(View.GONE);
            holder.viewAll.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mContext).load(modelList.get(i).getBrand_img()).into(holder.iv_brandImg);
        }

        holder.brandName.setText(modelList.get(i).getBrand_name());
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelList.get(i).getBrand_name().equals("View All")) {
                    mContext.startActivity(new Intent(mContext, BrandActivity.class));
                } else {
                    Intent mIntent = new Intent(mContext, ProductsActivity.class);
                    mIntent.putExtra("brand_id", modelList.get(i).getBrand_id());
                    mIntent.putExtra("Brand", "Brand");
                    mContext.startActivity(mIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class HomeBrandViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_brandImg,viewAll;
        TextView brandName;
        LinearLayout wholeLayout;

        public HomeBrandViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_brandImg = itemView.findViewById(R.id.iv_brandImg);
            brandName = itemView.findViewById(R.id.brandName);
            viewAll = itemView.findViewById(R.id.viewAll);
            wholeLayout = itemView.findViewById(R.id.wholeLayout);
        }
    }
}
