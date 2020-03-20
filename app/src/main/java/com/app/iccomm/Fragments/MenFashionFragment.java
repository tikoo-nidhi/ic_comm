package com.app.iccomm.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.app.iccomm.Activity.MainActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Adapters.HomeAdapter;
import com.app.iccomm.Adapters.SubCategoriesAdapter;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MenFashionFragment extends Fragment implements View.OnClickListener {

    //    ExpandableRelativeLayout expandableLayout_mf_1, expandableLayout_mf_2, expandableLayout_mf_3;
    RecyclerView recyclerView;
    Intent myIntent;
    //    LinearLayout expandableLinearLayout;
    List<CategoryModel> modelList = new ArrayList<>();
    List<CategoryModel> modelListSub = new ArrayList<>();
    List<CategoryModel> modelListChild;
    int pos;
//    Button[] top_cat;
//    ExpandableRelativeLayout[] expandableRelativeLayout;
//    TextView[] child_cat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.men_fashion_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        pos = getArguments().getInt("position");
        Log.d("posVAl", String.valueOf(getArguments().getInt("position")));
//        expandableLinearLayout = view.findViewById(R.id.expandableLinearLayout);

//        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Men");
//        ((MainActivity) getActivity()).getModelList();
//
//        CategoryModel categoryModel = new CategoryModel();
//        categoryModel.setCat_id(((MainActivity) getActivity()).getModelList().get(0).getCat_id());
//        categoryModel.setCat_name(((MainActivity) getActivity()).getModelList().get(0).getCat_name());
//
//        Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().size()));
//        Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().get(0).getCat_name()));
//
////        top_cat = new Button[((MainActivity) getActivity()).getModelList().get(0).getData().size()];
//
//        for (int k = 0; k < ((MainActivity) getActivity()).getModelList().get(0).getData().size(); k++) {
//
////            top_cat[k] = new Button(getActivity());
////            top_cat[k].setId(1000 + k);
////            top_cat[k].setText(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getSub_cat_name());
//
//            Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getSub_cat_id()));
//            Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getSub_cat_name()));
//
////            expandableRelativeLayout = new ExpandableRelativeLayout[((MainActivity) getActivity()).getModelList().get(0).getData().size()];
////            child_cat = new TextView[((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getDataChild().size()];
//
////            expandableRelativeLayout[k] = new ExpandableRelativeLayout(getActivity());
//            ExpandableRelativeLayout.LayoutParams params = new ExpandableRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            for (int l = 0; l < ((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getDataChild().size(); l++) {
//
////                child_cat[l] = new TextView(getActivity());
////                child_cat[l].setId(200 + l);
//
////                if (l>1){
////                    params.addRule(ExpandableRelativeLayout.ABOVE, child_cat[l].getId());
////                    child_cat[l].setLayoutParams(params);
////                }
////                child_cat[l].setText(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getDataChild().get(l).getChild_name());
//                Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getDataChild().size()));
//                Log.d("sub_cat_main", String.valueOf(((MainActivity) getActivity()).getModelList().get(0).getData().get(k).getDataChild().get(l).getChild_name()));
//
////                expandableRelativeLayout[k].addView(child_cat[l],params);
//            }
//
////            expandableLinearLayout.addView(top_cat[k]);
////            expandableLinearLayout.addView(expandableRelativeLayout[k]);
//        }


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

                Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().size()));
                Log.d("sub_cat_main1", String.valueOf(((MainActivity) getActivity()).getModelList().get(pos).getData().get(k).getDataChild().get(l).getChild_name()));

                modelListChild.add(categoryModel2);
                categoryModel1.setDataChild(modelListChild);
            }

            modelListSub.add(categoryModel1);
            categoryModel.setData(modelListSub);
            initRecyclerView();
        }

        modelList.add(categoryModel);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

//        view.findViewById(R.id.expandableButton_mf_1).setOnClickListener(this);
//        view.findViewById(R.id.expandableButton_mf_2).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_sportsWear).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_sleepWear).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_indianWear).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_suits).setOnClickListener(this);
//        view.findViewById(R.id.expandableButton_mf_3).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_watches).setOnClickListener(this);
//        view.findViewById(R.id.bt_mf_sunglasses).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_1_tv_1).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_1_tv_2).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_1_tv_3).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_1_tv_4).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_1_tv_5).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_2_tv_1).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_2_tv_2).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_2_tv_3).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_2_tv_4).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_3_tv_1).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_3_tv_2).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_3_tv_3).setOnClickListener(this);
//        view.findViewById(R.id.el_mf_3_tv_4).setOnClickListener(this);


//
//        expandableLayout_mf_1 = view.findViewById(R.id.expandableLayout_mf_1);
//        expandableLayout_mf_2 = view.findViewById(R.id.expandableLayout_mf_2);
//        expandableLayout_mf_3 = view.findViewById(R.id.expandableLayout_mf_3);
//        expandableLayout_mf_3.setVisibility(View.GONE);
//        expandableLayout_mf_1.collapse();
//        expandableLayout_mf_2.collapse();
//        expandableLayout_mf_3.collapse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case top_cat:
//                expandableRelativeLayout.toggle();
//                expandableRelativeLayout.collapse();
////                expandableLayout_mf_3.collapse();
//                break;
//
//            case R.id.expandableButton_mf_2:
//                expandableLayout_mf_1.collapse();
//                expandableLayout_mf_2.toggle();
//                expandableLayout_mf_3.collapse();
//                break;
//
//            case R.id.bt_mf_sportsWear:
//                startActivity(myIntent);
//                break;
//
//            case R.id.bt_mf_sleepWear:
//                startActivity(myIntent);
//                break;
//
//            case R.id.bt_mf_indianWear:
//                startActivity(myIntent);
//                break;
//
//            case R.id.bt_mf_suits:
//                startActivity(myIntent);
//                break;
//
//            case R.id.expandableButton_mf_3:
//                expandableLayout_mf_1.collapse();
//                expandableLayout_mf_2.collapse();
//                expandableLayout_mf_3.toggle();
//                break;
//
//            case R.id.bt_mf_watches:
//                startActivity(myIntent);
//                break;
//
//            case R.id.bt_mf_sunglasses:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_1_tv_1:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_1_tv_2:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_1_tv_3:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_1_tv_4:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_1_tv_5:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_2_tv_1:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_2_tv_2:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_2_tv_3:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_2_tv_4:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_3_tv_1:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_3_tv_2:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_3_tv_3:
//                startActivity(myIntent);
//                break;
//
//            case R.id.el_mf_3_tv_4:
//                startActivity(myIntent);
//                break;
//
        }
    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SubCategoriesAdapter mAdapter = new SubCategoriesAdapter(getContext(), modelListSub);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
