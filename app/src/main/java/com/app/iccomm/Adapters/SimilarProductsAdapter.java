package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SimilarProductsAdapter extends RecyclerView.Adapter<SimilarProductsAdapter.SimilarProductsViewHolder> {
    Context mContext;
    List<ProductsModel> modelList = new ArrayList<>();

    public SimilarProductsAdapter(Context mContext, List<ProductsModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public SimilarProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.products_card_view, viewGroup, false);
        return new SimilarProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarProductsViewHolder holder, final int i) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductDetails.class);
                mIntent.putExtra("prodId",modelList.get(i).getSimilar_prod_id());
                mContext.startActivity(mIntent);
            }
        });
        Glide.with(mContext).load(modelList.get(i).getSimilar_prod_img()).into(holder.iv_similarProd);

        if (modelList.get(i).getSimilar_prod_discount().equals("\u20B90")||modelList.get(i).getSimilar_prod_discount().startsWith("0")){
            holder.tv_discount_percentage.setVisibility(View.GONE);
            holder.tv_actual_price.setVisibility(View.GONE);
            holder.tv_discounted_price.setText(modelList.get(i).getSimilar_prod_discounted_price());
        }else {
            holder.tv_actual_price.setPaintFlags(holder.tv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_discount_percentage.setText(modelList.get(i).getSimilar_prod_discount().concat("\noff"));
            holder.tv_discounted_price.setText(modelList.get(i).getSimilar_prod_discounted_price());
            holder.tv_actual_price.setText(modelList.get(i).getSimilar_prod_price());
        }
//        if (modelList.get(i).getSimilar_prod_discounted_price().equals(modelList.get(i).getSimilar_prod_price())){
//            holder.tv_discount_percentage.setVisibility(View.GONE);
//            holder.tv_actual_price.setVisibility(View.GONE);
//        }

        holder.tv_similarProdName.setText(modelList.get(i).getSimilar_prod_name());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class SimilarProductsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv_discount_percentage, tv_discounted_price, tv_actual_price, tv_similarProdName;
        ImageView iv_similarProd;

        public SimilarProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tv_discount_percentage = itemView.findViewById(R.id.tv_discount_percentage);
            iv_similarProd = itemView.findViewById(R.id.iv_similarProd);
            tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
            tv_actual_price = itemView.findViewById(R.id.tv_actual_price);
            tv_similarProdName = itemView.findViewById(R.id.tv_similarProdName);
        }
    }
}
