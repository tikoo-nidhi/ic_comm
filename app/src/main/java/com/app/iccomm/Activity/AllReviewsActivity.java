package com.app.iccomm.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.iccomm.Adapters.ReviewsRatingsAdapter;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class AllReviewsActivity extends AppCompatActivity {
    RecyclerView allReviewRecyclerView;
    List<ProductsModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);
        allReviewRecyclerView = findViewById(R.id.allReviewRecyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllReviewsActivity.this, LinearLayoutManager.VERTICAL, false);
        ReviewsRatingsAdapter mAdapter = new ReviewsRatingsAdapter(AllReviewsActivity.this,modelList);
        allReviewRecyclerView.setLayoutManager(layoutManager);
        allReviewRecyclerView.setAdapter(mAdapter);
    }
}
