package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.iccomm.Activity.FilterActivity;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    Context mContext;
    List<VariationModel> modelList = new ArrayList<>();
    Boolean filter;
    String variantType;

    public FilterAdapter(Context mContext, List<VariationModel> modelList, Boolean filter, String variantType) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.filter = filter;
        this.variantType = variantType;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_view, viewGroup, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterViewHolder holder, final int i) {
//        Log.d("dataValue",modelList.get(i).getVariation_data_value());
        holder.cb_filter.setText(modelList.get(i).getVariation_data_value());
        holder.cb_filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (variantType.equals("Brand")) {
                        ((FilterActivity) mContext).filterBrand(String.valueOf(modelList.get(i).getVariation_data_id()), "Add");
                        Log.d("checkedValue", String.valueOf(modelList.get(i).getVariation_data_id()));
                        Log.d("checkedValue", holder.cb_filter.getText().toString());

                    } else if (variantType.equals("Filter")) {
                        ((FilterActivity) mContext).filterVariant(String.valueOf(modelList.get(i).getVariation_data_id()), "Add");
                        Log.d("checkedValue", String.valueOf(modelList.get(i).getVariation_data_id()));
                        Log.d("checkedValue", holder.cb_filter.getText().toString());
                    }

                } else {
                    if (variantType.equals("Brand")) {
                        ((FilterActivity) mContext).filterBrand(String.valueOf(modelList.get(i).getVariation_data_id()), "Remove");
                        Log.d("checkedValue", String.valueOf(modelList.get(i).getVariation_data_id()));
                        Log.d("checkedValue", holder.cb_filter.getText().toString());

                    } else if (variantType.equals("Filter")) {
                        ((FilterActivity) mContext).filterVariant(String.valueOf(modelList.get(i).getVariation_data_id()), "Add");
                        Log.d("checkedValue", String.valueOf(modelList.get(i).getVariation_data_id()));
                        Log.d("checkedValue", holder.cb_filter.getText().toString());
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_color, tv_filterSelected, tv_filterItems;
        CheckBox cb_filter;
        LinearLayout wholeLay;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_filterSelected = itemView.findViewById(R.id.tv_filterSelected);
            tv_filterItems = itemView.findViewById(R.id.tv_filterItems);
            cb_filter = itemView.findViewById(R.id.cb_filter);
            wholeLay = itemView.findViewById(R.id.wholeLay);
        }
    }
}
