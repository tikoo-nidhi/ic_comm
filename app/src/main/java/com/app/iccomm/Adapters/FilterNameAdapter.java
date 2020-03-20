package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iccomm.Activity.FilterActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Activity.ShippingDetailsActivity;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class FilterNameAdapter extends RecyclerView.Adapter<FilterNameAdapter.FilterViewHolder> {
    Context mContext;
    private List<VariationModel> mList = new ArrayList<>();
    List<VariationModel> modelList;
    int index = 0;

    public FilterNameAdapter(Context mContext, List<VariationModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_layout, viewGroup, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterViewHolder holder, final int i) {
        Log.d("filterName", mList.get(i).getVariation_name());
        holder.tv_filterName.setText(mList.get(i).getVariation_name());


        if (index == i) {
            holder.tv_filterName.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            if (mList.get(i).getVariation_name().equals("Price")){
                ((FilterActivity) mContext).price(mList.get(i).getVariation_data().get(0).getVariation_data_value(),mList.get(i).getVariation_data().get(1).getVariation_data_value());
            }else if (mList.get(i).getVariation_name().equals("Brand")){
                modelList = new ArrayList<>();
                for (int j = 0;j<mList.get(i).getVariation_data().size();j++){
                    VariationModel variationModel = new VariationModel();
                    variationModel.setVariation_data_id(mList.get(i).getVariation_data().get(j).getVariation_data_id());
                    variationModel.setVariation_data_value(mList.get(i).getVariation_data().get(j).getVariation_data_value());
                    modelList.add(variationModel);
                    ((FilterActivity) mContext).initRecyclerViewColorFilter(modelList,"Brand");
                }
            }else {
                modelList = new ArrayList<>();
                for (int j = 0;j<mList.get(i).getVariation_data().size();j++){
                    VariationModel variationModel = new VariationModel();
                    variationModel.setVariation_data_id(mList.get(i).getVariation_data().get(j).getVariation_data_id());
                    variationModel.setVariation_data_value(mList.get(i).getVariation_data().get(j).getVariation_data_value());
                    modelList.add(variationModel);
                    ((FilterActivity) mContext).initRecyclerViewColorFilter(modelList,"Filter");
                }
            }
        }else{
            holder.tv_filterName.setBackgroundColor(0);
        }

        holder.tv_filterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                notifyDataSetChanged();

//                index = i;
//                mList.get(i).setFilterSelected(true);
//
//                for (int j = 0; j < i; j++) {
//                    mList.get(j).setFilterSelected(false);
//                }
//
//                for (int j = (mList.size() - 1); j > i; j--) {
//                    mList.get(j).setFilterSelected(false);
//                }
//                notifyDataSetChanged();
//
//                if (mContext instanceof FilterActivity) {
//                    ((FilterActivity) mContext).display(mList);
//                }


            }
        });

//        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.checkedTextView.isChecked()) {
//                    Log.d("isChecked", "true");
//                    holder.checkedTextView.setCheckMarkDrawable(null);
//                    holder.checkedTextView.setChecked(false);
//                    mList.get(i).setFilterSelected(false);
//
//                    if (mContext instanceof FilterActivity) {
//                        ((FilterActivity) mContext).display(mList);
//                    }
//                } else  {
//                    Log.d("isChecked", "false");
//                    holder.checkedTextView.setCheckMarkDrawable(R.drawable.checked_text_view);
//                    holder.checkedTextView.setChecked(true);
//                    mList.get(i).setFilterSelected(true);
//
//                    if (mContext instanceof FilterActivity) {
//                        ((FilterActivity) mContext).display(mList);
//                    }
//                }
//                Log.d("arrayListIndex", String.valueOf(i));
//                Log.d("arrayList", String.valueOf(mList.get(i).getFilterSelected()));
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_filterName;
        CheckedTextView checkedTextView;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_filterName = itemView.findViewById(R.id.tv_filterName);
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
        }
    }
}
