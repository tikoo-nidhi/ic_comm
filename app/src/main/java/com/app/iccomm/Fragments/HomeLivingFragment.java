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

public class HomeLivingFragment extends Fragment implements View.OnClickListener {
    ExpandableRelativeLayout expandableLayout_hl_1, expandableLayout_hl_2, expandableLayout_hl_3;
    Intent myIntent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_living_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home & Living");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

        view.findViewById(R.id.expandableButton_hl_1).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_hl_2).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_hl_3).setOnClickListener(this);
        view.findViewById(R.id.el_hl_1_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_hl_1_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_hl_2_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_hl_2_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_hl_2_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_hl_3_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_hl_3_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_hl_3_tv_3).setOnClickListener(this);

        expandableLayout_hl_1 = view.findViewById(R.id.expandableLayout_hl_1);
        expandableLayout_hl_2 = view.findViewById(R.id.expandableLayout_hl_2);
        expandableLayout_hl_3 = view.findViewById(R.id.expandableLayout_hl_3);

        expandableLayout_hl_1.collapse();
        expandableLayout_hl_2.collapse();
        expandableLayout_hl_3.collapse();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.expandableButton_hl_1:
                expandableLayout_hl_1.toggle();
                expandableLayout_hl_2.collapse();
                expandableLayout_hl_3.collapse();
                break;
            case R.id.expandableButton_hl_2:
                expandableLayout_hl_1.collapse();
                expandableLayout_hl_2.toggle();
                expandableLayout_hl_3.collapse();
                break;
            case R.id.expandableButton_hl_3:
                expandableLayout_hl_1.collapse();
                expandableLayout_hl_2.collapse();
                expandableLayout_hl_3.toggle();
                break;
            case R.id.el_hl_1_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_hl_1_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_hl_2_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_hl_2_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_hl_2_tv_3:
                startActivity(myIntent);
                break;

            case R.id.el_hl_3_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_hl_3_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_hl_3_tv_3:
                startActivity(myIntent);
                break;

        }

    }
}
