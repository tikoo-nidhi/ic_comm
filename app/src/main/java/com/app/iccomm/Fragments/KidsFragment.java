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

public class KidsFragment extends Fragment implements View.OnClickListener {

    ExpandableRelativeLayout expandableLayout_kids_1, expandableLayout_kids_2, expandableLayout_kids_3, expandableLayout_kids_4, expandableLayout_kids_5;
    Intent myIntent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kids_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Kids");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

        view.findViewById(R.id.expandableButton_kids_1).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_kids_2).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_kids_3).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_kids_4).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_kids_5).setOnClickListener(this);
        view.findViewById(R.id.bt_kids_toys).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_5).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_6).setOnClickListener(this);
        view.findViewById(R.id.el_kids_1_tv_7).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_5).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_6).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_7).setOnClickListener(this);
        view.findViewById(R.id.el_kids_2_tv_8).setOnClickListener(this);
        view.findViewById(R.id.el_kids_3_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_kids_3_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_kids_3_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_kids_4_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_kids_4_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_kids_4_tv_3).setOnClickListener(this);
        view.findViewById(R.id.el_kids_4_tv_4).setOnClickListener(this);
        view.findViewById(R.id.el_kids_5_tv_1).setOnClickListener(this);
        view.findViewById(R.id.el_kids_5_tv_2).setOnClickListener(this);
        view.findViewById(R.id.el_kids_5_tv_3).setOnClickListener(this);

        expandableLayout_kids_1 = view.findViewById(R.id.expandableLayout_kids_1);
        expandableLayout_kids_2 = view.findViewById(R.id.expandableLayout_kids_2);
        expandableLayout_kids_3 = view.findViewById(R.id.expandableLayout_kids_3);
        expandableLayout_kids_4 = view.findViewById(R.id.expandableLayout_kids_4);
        expandableLayout_kids_5 = view.findViewById(R.id.expandableLayout_kids_5);

        expandableLayout_kids_1.collapse();
        expandableLayout_kids_2.collapse();
        expandableLayout_kids_3.collapse();
        expandableLayout_kids_4.collapse();
        expandableLayout_kids_5.collapse();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.expandableButton_kids_1:
                expandableLayout_kids_1.toggle();
                expandableLayout_kids_2.collapse();
                expandableLayout_kids_3.collapse();
                expandableLayout_kids_4.collapse();
                expandableLayout_kids_5.collapse();
                break;
            case R.id.expandableButton_kids_2:
                expandableLayout_kids_1.collapse();
                expandableLayout_kids_2.toggle();
                expandableLayout_kids_3.collapse();
                expandableLayout_kids_4.collapse();
                expandableLayout_kids_5.collapse();
                break;
            case R.id.expandableButton_kids_3:
                expandableLayout_kids_1.collapse();
                expandableLayout_kids_2.collapse();
                expandableLayout_kids_3.toggle();
                expandableLayout_kids_4.collapse();
                expandableLayout_kids_5.collapse();
                break;
            case R.id.expandableButton_kids_4:
                expandableLayout_kids_1.collapse();
                expandableLayout_kids_2.collapse();
                expandableLayout_kids_3.collapse();
                expandableLayout_kids_4.toggle();
                expandableLayout_kids_5.collapse();
                break;
            case R.id.expandableButton_kids_5:
                expandableLayout_kids_1.collapse();
                expandableLayout_kids_2.collapse();
                expandableLayout_kids_3.collapse();
                expandableLayout_kids_4.collapse();
                expandableLayout_kids_5.toggle();
                break;
            case R.id.el_kids_1_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_3:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_4:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_5:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_6:
                startActivity(myIntent);
                break;
            case R.id.el_kids_1_tv_7:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_3:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_4:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_5:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_6:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_7:
                startActivity(myIntent);
                break;
            case R.id.el_kids_2_tv_8:
                startActivity(myIntent);
                break;
            case R.id.el_kids_3_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_kids_3_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_kids_3_tv_3:
                startActivity(myIntent);
                break;
            case R.id.el_kids_4_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_kids_4_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_kids_4_tv_3:
                startActivity(myIntent);
                break;
            case R.id.el_kids_4_tv_4:
                startActivity(myIntent);
                break;
            case R.id.el_kids_5_tv_1:
                startActivity(myIntent);
                break;
            case R.id.el_kids_5_tv_2:
                startActivity(myIntent);
                break;
            case R.id.el_kids_5_tv_3:
                startActivity(myIntent);
                break;
            case R.id.bt_kids_toys:
                startActivity(myIntent);
                break;

        }

    }
}
