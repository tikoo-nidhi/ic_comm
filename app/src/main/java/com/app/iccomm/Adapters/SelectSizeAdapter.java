package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductDetails;
import com.app.iccomm.Activity.ShippingDetailsActivity;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class SelectSizeAdapter extends RecyclerView.Adapter<SelectSizeAdapter.SelectSizeViewHolder> {

    Context mContext;
    List<VariationModel> mList = new ArrayList<>();

    int index;

    public SelectSizeAdapter(Context mContext, List<VariationModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public SelectSizeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_size_view, viewGroup, false);
        return new SelectSizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectSizeViewHolder holder, final int i) {
        holder.bt_selectSize.setText(mList.get(i).getVariation_data_value());

        if (index == i){
            holder.bt_selectSize.setBackground(mContext.getResources().getDrawable(R.drawable.round_button_after_click));
            holder.bt_selectSize.setTextColor(mContext.getResources().getColor(R.color.colorLogoBlue));
            ((ProductDetails) mContext).displaySize(mList.get(i).getVariation_data_value(),mList.get(i).getvPrice());

        }
        else{
            holder.bt_selectSize.setBackground(mContext.getResources().getDrawable(R.drawable.round_button));
            holder.bt_selectSize.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }

        holder.bt_selectSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                notifyDataSetChanged();
                holder.tv_size.setText(holder.bt_selectSize.getText());
                Log.d("tv",holder.bt_selectSize.getText().toString());
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SelectSizeViewHolder extends RecyclerView.ViewHolder {

        Button bt_selectSize;
        TextView tv_size;

        public SelectSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_selectSize = itemView.findViewById(R.id.bt_selectSize);
            tv_size = itemView.findViewById(R.id.tv_size);
        }
    }
}
