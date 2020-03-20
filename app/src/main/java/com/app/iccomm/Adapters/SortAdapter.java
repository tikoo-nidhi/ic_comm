package com.app.iccomm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortViewHolder> {
    Context mContext;
    List<HomePageModel> modelList = new ArrayList<>();
    String catId;

    public SortAdapter(Context mContext, List<HomePageModel> modelList, String catId) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.catId = catId;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sort_products,viewGroup,false);
        return new SortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, final int i) {
        holder.sortText.setText(modelList.get(i).getSort_name());
        holder.sortText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
                Intent mIntent = new Intent(mContext, ProductsActivity.class);
                mIntent.putExtra("catId",catId);
                mIntent.putExtra("sortId",modelList.get(i).getSort_id());
                mContext.startActivity(mIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class SortViewHolder extends RecyclerView.ViewHolder {
        TextView sortText;
        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            sortText = itemView.findViewById(R.id.sortText);
        }
    }
}
