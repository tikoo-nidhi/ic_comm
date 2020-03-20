package com.app.iccomm.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.iccomm.Activity.MainActivity;
import com.app.iccomm.Adapters.SubCategoryNameNewAdapter;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryNewDesignFragment extends Fragment {
    RecyclerView rv_categories;
    Intent myIntent;
    //    LinearLayout expandableLinearLayout;
    List<CategoryModel> modelList = new ArrayList<>();
    List<CategoryModel> modelListSub = new ArrayList<>();
    List<CategoryModel> modelListChild;
    int pos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_new_design_fragment,container,false);
        rv_categories = view.findViewById(R.id.rv_categories);
        pos = getArguments().getInt("position");
        Log.d("posVAl", String.valueOf(getArguments().getInt("position")));
//        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString("titleTag"));
        ((MainActivity) getActivity()).getModelList();
        if (((MainActivity) getActivity()).getModelList().get(pos).getData().size()==0){
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }

        Log.d("sizeOfArray", String.valueOf(((MainActivity) getActivity()).getModelList().size()));

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCat_id(((MainActivity) getActivity()).getModelList().get(pos).getCat_id());
        categoryModel.setCat_name(((MainActivity) getActivity()).getModelList().get(pos).getCat_name());

        Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().size()));
        Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getCat_name()));

        for (int k = 0; k < ((MainActivity) getActivity()).getModelList().get(pos).getData().size(); k++) {

            CategoryModel categoryModel1 = new CategoryModel();
            categoryModel1.setSub_cat_id(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getSub_cat_id());
            categoryModel1.setSub_cat_name(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getSub_cat_name());

            Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().size()));
            Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getSub_cat_name()));

            modelListChild = new ArrayList<>();

            for (int l = 0; l < ((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().size(); l++) {

                CategoryModel categoryModel2 = new CategoryModel();
                categoryModel2.setChild_id(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_id());
                categoryModel2.setChild_name(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_name());
                categoryModel2.setChild_img(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_img());

                Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().size()));
                Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_name()));
                Log.d("childImg3", String.valueOf((((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_img())));

                modelListChild.add(categoryModel2);
                categoryModel1.setDataChild(modelListChild);
            }

            modelListSub.add(categoryModel1);
            categoryModel.setData(modelListSub);
            initRecyclerView();
        }

        modelList.add(categoryModel);
    }


    public void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        SubCategoryNameNewAdapter mAdapter = new SubCategoryNameNewAdapter(getContext(),modelListSub);
        rv_categories.setLayoutManager(linearLayoutManager);
        rv_categories.setAdapter(mAdapter);
    }
}
