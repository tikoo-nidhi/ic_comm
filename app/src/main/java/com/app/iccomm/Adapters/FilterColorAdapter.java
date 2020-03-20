package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.iccomm.R;

public class FilterColorAdapter extends RecyclerView.Adapter<FilterColorAdapter.FilterColorViewHolder> {

    Context mContext;

    public FilterColorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FilterColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_view, viewGroup, false);
        return new FilterColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterColorViewHolder holder, int i) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FilterColorViewHolder extends RecyclerView.ViewHolder {
        TextView tv_color, tv_filterSelected, tv_filterItems;
        CheckBox cb_filter;

        public FilterColorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_filterSelected = itemView.findViewById(R.id.tv_filterSelected);
            tv_filterItems = itemView.findViewById(R.id.tv_filterItems);
            cb_filter = itemView.findViewById(R.id.cb_filter);
        }
    }
}
