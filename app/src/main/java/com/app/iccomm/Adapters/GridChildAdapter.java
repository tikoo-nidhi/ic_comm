package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GridChildAdapter extends RecyclerView.Adapter<GridChildAdapter.GridChildViewHolder> {
    Context mContext;
    List<CategoryModel> mList = new ArrayList<>();

    public GridChildAdapter(Context mContext, List<CategoryModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public GridChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_child_new_view, viewGroup, false);
        return new GridChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridChildViewHolder holder, final int i) {
        Glide.with(mContext).load(mList.get(i).getChild_img()).into(holder.childCategory_img);
//        Log.d("imgChild",mList.get(i).getChild_img());
        holder.childCategory_name.setText(mList.get(i).getChild_name());

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductsActivity.class);
                mIntent.putExtra("catId", mList.get(i).getChild_id());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GridChildViewHolder extends RecyclerView.ViewHolder {
        ImageView childCategory_img;
        TextView childCategory_name;
        LinearLayout mainLay;

        public GridChildViewHolder(@NonNull View itemView) {
            super(itemView);
            childCategory_img = itemView.findViewById(R.id.childCategory_img);
            childCategory_name = itemView.findViewById(R.id.childCategory_name);
            mainLay = itemView.findViewById(R.id.mainLay);
        }
    }
}
