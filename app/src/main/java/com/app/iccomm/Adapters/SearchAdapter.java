package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Activity.SearchActivity;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context mContext;
    private List<String> mList = new ArrayList<>();

    public SearchAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_view,viewGroup,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, int i) {
        holder.tv_searchResult.setText(mList.get(i));
        holder.tv_searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ProductsActivity.class);
                mIntent.putExtra("search",true);
                mIntent.putExtra("searchResult",holder.tv_searchResult.getText());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("search_size", String.valueOf(mList.size()));
        return mList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tv_searchResult;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_searchResult = itemView.findViewById(R.id.tv_searchResult);
        }
    }
}
