package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeBannerHorizontal extends RecyclerView.Adapter<HomeBannerHorizontal.HomeBannerHorizontalViewHolder> {

    Context mContext;
    List<HomePageModel> modelList = new ArrayList<>();

    public HomeBannerHorizontal(Context mContext, List<HomePageModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeBannerHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_banner_horizontal,viewGroup,false);
        return new HomeBannerHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBannerHorizontalViewHolder holder, final int i) {
        Glide.with(mContext).load(modelList.get(i).getOffers_img()).into(holder.iv_bestOffers_acc);
        holder.iv_bestOffers_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductsActivity.class);
                mIntent.putExtra("catId",modelList.get(i).getCat_id());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class HomeBannerHorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bestOffers_acc;
        public HomeBannerHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_bestOffers_acc = itemView.findViewById(R.id.iv_bestOffers_acc);
        }
    }
}
