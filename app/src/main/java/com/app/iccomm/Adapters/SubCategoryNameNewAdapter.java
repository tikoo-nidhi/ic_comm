package com.app.iccomm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryNameNewAdapter extends RecyclerView.Adapter<SubCategoryNameNewAdapter.SubCategoryNameNewViewHolder> {
    Context mContext;
    private List<CategoryModel> modelList = new ArrayList<>();
    private List<CategoryModel> childData = new ArrayList<>();

    private SparseBooleanArray expandState = new SparseBooleanArray();
    RecyclerView recyclerView;


    public SubCategoryNameNewAdapter(Context mContext, List<CategoryModel> modelList) {

        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public SubCategoryNameNewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_category_name_view, viewGroup, false);
        return new SubCategoryNameNewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SubCategoryNameNewViewHolder holder, final int i) {

        recyclerView = new RecyclerView(mContext);

        if (modelList.get(i).getDataChild().size() == 0) {
            TextView text = new TextView(mContext);
            text.setId(100 + i);
            text.setPadding(50, 40, 0, 40);
            text.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            text.setTextSize(20);
            text.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            text.setText(modelList.get(i).getSub_cat_name());
            holder.linearLay.addView(text);
            holder.bt_cat_name.setVisibility(View.GONE);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, ProductsActivity.class);
                    mIntent.putExtra("catId", modelList.get(i).getSub_cat_id());
                    mContext.startActivity(mIntent);
                }
            });
        } else {


            holder.bt_cat_name.setText(modelList.get(i).getSub_cat_name());

            final ExpandableLinearLayout[] expandableLinearLayouts = new ExpandableLinearLayout[modelList.size()];
            expandableLinearLayouts[i] = new ExpandableLinearLayout(mContext);
            expandableLinearLayouts[i].setId(1000 + i);
            ExpandableLinearLayout.LayoutParams params = new ExpandableLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            expandableLinearLayouts[i].setOrientation(LinearLayout.VERTICAL);
            expandableLinearLayouts[i].setLayoutParams(params);
            expandableLinearLayouts[i].setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            expandableLinearLayouts[i].setDuration(200);
            expandableLinearLayouts[i].setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR));
            expandableLinearLayouts[i].setExpanded(false);

            recyclerView.setId(5000 + i);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setPadding(20, 20, 20, 20);
            childData = new ArrayList<>();
//            TextView[] textViews = new TextView[modelList.get(i).getDataChild().size()];
//
            for (int j = 0; j < modelList.get(i).getDataChild().size(); j++) {

                Log.d("subCatChild", String.valueOf(modelList.get(i).getDataChild().size()));

                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setChild_id(modelList.get(i).getDataChild().get(j).getChild_id());
                categoryModel.setChild_img(modelList.get(i).getDataChild().get(j).getChild_img());
                categoryModel.setChild_name(modelList.get(i).getDataChild().get(j).getChild_name());
                childData.add(categoryModel);
                initRecyclerView();

//                textViews[j] = new TextView(mContext);
//                textViews[j].setId(2000 + j);
//                textViews[j].setPadding(200, 40, 0, 40);
//                textViews[j].setTextColor(mContext.getResources().getColor(R.color.colorBlack));
//                textViews[j].setTextSize(15);
//
//                textViews[j].setText(modelList.get(i).getDataChild().get(j).getChild_name());
//
//                Log.d("subCatChild", modelList.get(i).getDataChild().get(j).getChild_name());
//
//                expandableLinearLayouts[i].addView(textViews[j]);
//
//                final int finalJ = j;
//                textViews[j].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent mIntent = new Intent(mContext, ProductsActivity.class);
//                        mIntent.putExtra("catId", modelList.get(i).getDataChild().get(finalJ).getChild_id());
//                        mContext.startActivity(mIntent);
//                    }
//                });

            }

            expandableLinearLayouts[i].addView(recyclerView);
            holder.linearLay.addView(expandableLinearLayouts[i]);

            holder.bt_cat_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandableLinearLayouts[i].toggle();
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class SubCategoryNameNewViewHolder extends RecyclerView.ViewHolder {

        //        RecyclerView rv_childCategory;
        Button bt_cat_name;
        //        ExpandableLinearLayout expandableLayout;
        LinearLayout linearLay;

        public SubCategoryNameNewViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_cat_name = itemView.findViewById(R.id.bt_cat_name);
            linearLay = itemView.findViewById(R.id.linearLay);

//            expandableView_childCategory.initLayout();


        }
    }

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 4);
        GridChildAdapter mAdapter = new GridChildAdapter(mContext,childData);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }
}
