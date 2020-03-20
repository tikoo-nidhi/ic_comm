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

import com.app.iccomm.Activity.BrandActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BrandViewAllAdapter extends RecyclerView.Adapter<BrandViewAllAdapter.BrandViewAllHolder> {
    Context mContext;
    List<HomePageModel> modelList = new ArrayList<>();

    public BrandViewAllAdapter(Context mContext, List<HomePageModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public BrandViewAllHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brand_view_all, viewGroup, false);
        return new BrandViewAllHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewAllHolder holder, final int i) {
        holder.setIsRecyclable(false);

        Glide.with(mContext).load(modelList.get(i).getBrand_img()).into(holder.iv_brandImg);

        holder.brandName.setText(modelList.get(i).getBrand_name());
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, ProductsActivity.class);
                mIntent.putExtra("brand_id", modelList.get(i).getBrand_id());
                mIntent.putExtra("Brand", "Brand");
                mContext.startActivity(mIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class BrandViewAllHolder extends RecyclerView.ViewHolder {
        ImageView iv_brandImg, viewAll;
        TextView brandName;
        LinearLayout wholeLayout;

        public BrandViewAllHolder(@NonNull View itemView) {
            super(itemView);
            iv_brandImg = itemView.findViewById(R.id.iv_brandImg);
            brandName = itemView.findViewById(R.id.brandName);
            viewAll = itemView.findViewById(R.id.viewAll);
            wholeLayout = itemView.findViewById(R.id.wholeLayout);
        }
    }
}
