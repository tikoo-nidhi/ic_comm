package com.app.iccomm.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.iccomm.Activity.MainActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class BeautyAndCareFragment extends Fragment implements View.OnClickListener {
    ExpandableRelativeLayout expandableLayout_bCare_1;
    Intent myIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beauty_care_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Beauty & Care");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

        view.findViewById(R.id.expandableButton_bCare_1).setOnClickListener(this);
        view.findViewById(R.id.bt_bCare_skin).setOnClickListener(this);
        view.findViewById(R.id.bt_bCare_hair).setOnClickListener(this);
        view.findViewById(R.id.el_bCare_1_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_bCare_1_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_bCare_1_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_bCare_1_tv_4).setOnClickListener(this);

        expandableLayout_bCare_1 = view.findViewById(R.id.expandableLayout_bCare_1);

        expandableLayout_bCare_1.collapse();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expandableButton_bCare_1:
                expandableLayout_bCare_1.toggle();
                break;
            case R.id.bt_bCare_skin:
                startActivity(myIntent);
                break;
            case R.id.bt_bCare_hair:
                startActivity(myIntent);
                break;
            case R.id.el_bCare_1_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_bCare_1_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_bCare_1_tv_3:
                startActivity(myIntent);
                break;
            case R.id.el_bCare_1_tv_4:
                startActivity(myIntent);
                break;
        }

    }
}
