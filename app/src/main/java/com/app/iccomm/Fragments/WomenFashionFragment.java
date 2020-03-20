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

public class WomenFashionFragment extends Fragment implements View.OnClickListener {
    ExpandableRelativeLayout expandableLayout_wf_1, expandableLayout_wf_2, expandableLayout_wf_3, expandableLayout_wf_4;
    Intent myIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.women_fashion_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Women");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

        view.findViewById(R.id.expandableButton_wf_1).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_wf_2).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_wf_3).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_wf_4).setOnClickListener(this);
        view.findViewById(R.id.bt_wf_handbags).setOnClickListener(this);
        view.findViewById(R.id.bt_wf_jewellery).setOnClickListener(this);
        view.findViewById(R.id.bt_wf_sunglasses).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_5).setOnClickListener(this);
        view.findViewById(R.id.el_wf_1_tv_6).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_5).setOnClickListener(this);
        view.findViewById(R.id.el_wf_2_tv_6).setOnClickListener(this);
        view.findViewById(R.id.el_wf_3_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_wf_3_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_wf_3_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_wf_3_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_wf_4_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_wf_4_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_wf_4_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_wf_4_tv_4).setOnClickListener(this);

        expandableLayout_wf_1 = view.findViewById(R.id.expandableLayout_wf_1);
        expandableLayout_wf_2 = view.findViewById(R.id.expandableLayout_wf_2);
        expandableLayout_wf_3 = view.findViewById(R.id.expandableLayout_wf_3);
        expandableLayout_wf_4 = view.findViewById(R.id.expandableLayout_wf_4);

        expandableLayout_wf_1.collapse();
        expandableLayout_wf_2.collapse();
        expandableLayout_wf_3.collapse();
        expandableLayout_wf_4.collapse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expandableButton_wf_1:
                expandableLayout_wf_1.toggle();
                expandableLayout_wf_2.collapse();
                expandableLayout_wf_3.collapse();
                expandableLayout_wf_4.collapse();
                break;
            case R.id.expandableButton_wf_2:
                expandableLayout_wf_1.collapse();
                expandableLayout_wf_2.toggle();
                expandableLayout_wf_3.collapse();
                expandableLayout_wf_4.collapse();
                break;
            case R.id.expandableButton_wf_3:
                expandableLayout_wf_1.collapse();
                expandableLayout_wf_2.collapse();
                expandableLayout_wf_3.toggle();
                expandableLayout_wf_4.collapse();
                break;
            case R.id.expandableButton_wf_4:
                expandableLayout_wf_1.collapse();
                expandableLayout_wf_2.collapse();
                expandableLayout_wf_3.collapse();
                expandableLayout_wf_4.toggle();
                break;
            case R.id.bt_wf_handbags:
                startActivity(myIntent);
                break;

            case R.id.bt_wf_jewellery:
                startActivity(myIntent);
                break;

            case R.id.bt_wf_sunglasses:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_3:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_4:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_5:
                startActivity(myIntent);
                break;

            case R.id.el_wf_1_tv_6:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_3:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_4:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_5:
                startActivity(myIntent);
                break;

            case R.id.el_wf_2_tv_6:
                startActivity(myIntent);
                break;

            case R.id.el_wf_3_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_wf_3_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_wf_3_tv_3:
                startActivity(myIntent);
                break;

            case R.id.el_wf_3_tv_4:
                startActivity(myIntent);
                break;

            case R.id.el_wf_4_tv_1:
                startActivity(myIntent);
                break;

            case R.id.el_wf_4_tv_2:
                startActivity(myIntent);
                break;

            case R.id.el_wf_4_tv_3:
                startActivity(myIntent);
                break;

            case R.id.el_wf_4_tv_4:
                startActivity(myIntent);
                break;

        }
    }
}
