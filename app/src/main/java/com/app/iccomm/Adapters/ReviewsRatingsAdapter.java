package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRatingsAdapter extends RecyclerView.Adapter<ReviewsRatingsAdapter.ReviewsRatingsViewHolder> {

    Context mContext;
    List<ProductsModel> mList = new ArrayList<>();

    public ReviewsRatingsAdapter(Context mContext, List<ProductsModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ReviewsRatingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reviews_and_ratings, viewGroup, false);
        return new ReviewsRatingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRatingsViewHolder holder, int i) {
        holder.tv_reviewHeading.setText(mList.get(i).getReview_heading());
        holder.tv_reviewRating.setText(mList.get(i).getReview_rating());
        holder.tv_reviewText.setText(mList.get(i).getReview_text());
        holder.tv_reviewerName.setText(mList.get(i).getUser_name());
        holder.tv_reviewDate.setText(mList.get(i).getReview_date());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ReviewsRatingsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_reviewHeading, tv_reviewRating, tv_reviewText, tv_reviewerName, tv_reviewDate;

        public ReviewsRatingsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_reviewHeading = itemView.findViewById(R.id.tv_reviewHeading);
            tv_reviewRating = itemView.findViewById(R.id.tv_reviewRating);
            tv_reviewText = itemView.findViewById(R.id.tv_reviewText);
            tv_reviewerName = itemView.findViewById(R.id.tv_reviewerName);
            tv_reviewDate = itemView.findViewById(R.id.tv_reviewDate);
        }

    }
}
