package com.app.iccomm.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.iccomm.Fragments.CategoryNewDesignFragment;
import com.app.iccomm.Fragments.MenFashionFragment;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context mContext;
    List<CategoryModel> modelList = new ArrayList<>();
//    ArrayList<String> img = new ArrayList<>();


    public HomeAdapter(Context mContext, List<CategoryModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_recycler_view, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int i) {
        Glide.with(mContext).load(modelList.get(i).getCat_img()).into(holder.homeImgCardView);
        holder.tv_categoryName.setText(modelList.get(i).getCat_name());
        holder.homeImgCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",i);
                bundle.putString("titleTag",modelList.get(i).getCat_name());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new CategoryNewDesignFragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,fragment).commit();


            }
        });
//        Log.d("categoryName",modelList.get(i).getSub_cat_name());
//        Log.d("categoryName",modelList.get(i).getChild_name());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        CircleImageView homeImgCardView;
        TextView tv_categoryName;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeImgCardView = itemView.findViewById(R.id.homeImgCardView);
            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);
        }
    }
}
