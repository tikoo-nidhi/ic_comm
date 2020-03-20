package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.iccomm.R;

public class WriteReviewAdapter extends RecyclerView.Adapter<WriteReviewAdapter.WriteReviewViewHolder> {

    Context mContext;

    public WriteReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WriteReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.write_review_view,viewGroup,false);
        return new WriteReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteReviewViewHolder writeReviewViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class WriteReviewViewHolder extends RecyclerView.ViewHolder{

        public WriteReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
