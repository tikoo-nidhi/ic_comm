package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.iccomm.R;

public class HomeLayoutsAdapter extends RecyclerView.Adapter<HomeLayoutsAdapter.HomeLayoutsViewHolder> {
    Context mContext;

    public HomeLayoutsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HomeLayoutsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_layouts,viewGroup,false);
        return new HomeLayoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLayoutsViewHolder homeLayoutsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeLayoutsViewHolder extends RecyclerView.ViewHolder {
        public HomeLayoutsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
