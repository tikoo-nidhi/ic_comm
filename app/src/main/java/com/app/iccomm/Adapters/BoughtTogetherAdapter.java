package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.R;

public class BoughtTogetherAdapter extends RecyclerView.Adapter<BoughtTogetherAdapter.BoughtTogetherViewHolder> {

    Context mContext;

    public BoughtTogetherAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BoughtTogetherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.products_card_view,viewGroup,false);
        return new BoughtTogetherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoughtTogetherViewHolder holder, int i) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetails.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class BoughtTogetherViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public BoughtTogetherViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
