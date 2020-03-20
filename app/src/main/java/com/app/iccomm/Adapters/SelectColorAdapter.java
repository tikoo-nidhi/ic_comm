package com.app.iccomm.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class SelectColorAdapter extends RecyclerView.Adapter<SelectColorAdapter.SelectColorViewHolder> {
    Context mContext;
    List<VariationModel> mList = new ArrayList<>();

    int index = 0;

    public SelectColorAdapter(Context mContext, List<VariationModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public SelectColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_color_view, viewGroup, false);
        return new SelectColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectColorViewHolder holder, final int i) {
        holder.imgView_color.setBackgroundColor(Color.parseColor(mList.get(i).getVariation_data_value()));


        if (index == i){
            holder.tv_backColor.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
            ((ProductDetails) mContext).displayColor(mList.get(i).getVariation_data_value(),mList.get(i).getvPrice(),mList.get(i).getVariationColorName());

//            Toast.makeText(mContext, ""+index, Toast.LENGTH_SHORT).show();
        }
        else{
            holder.tv_backColor.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
//            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SelectColorViewHolder extends RecyclerView.ViewHolder {
        TextView tv_backColor;
        ImageView imgView_color;

        public SelectColorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_backColor = itemView.findViewById(R.id.tv_backColor);
            imgView_color = itemView.findViewById(R.id.imgView_color);


        }
    }
}
