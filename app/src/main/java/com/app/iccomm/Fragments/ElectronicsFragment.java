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

public class ElectronicsFragment extends Fragment implements View.OnClickListener {
    ExpandableRelativeLayout expandableLayout_ele_1, expandableLayout_ele_2;
    Intent myIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.electronics_fragment, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Electronics");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIntent = new Intent(getContext(), ProductsActivity.class);

        view.findViewById(R.id.bt_ele_laptop).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_mobiles).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_ele_1).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_tablet).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_television).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_speakers).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_smartWatches).setOnClickListener(this);
        view.findViewById(R.id.bt_ele_desktopPc).setOnClickListener(this);
        view.findViewById(R.id.expandableButton_ele_2).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ma_1).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ma_2).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ma_3).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ma_4).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ca_1).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ca_2).setOnClickListener(this);
        view.findViewById(R.id.tv_electronics_ca_3).setOnClickListener(this);

        expandableLayout_ele_1 = view.findViewById(R.id.expandableLayout_ele_1);
        expandableLayout_ele_2 = view.findViewById(R.id.expandableLayout_ele_2);
        expandableLayout_ele_1.collapse();
        expandableLayout_ele_2.collapse();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ele_laptop:
                startActivity(myIntent);
                break;
            case R.id.bt_ele_mobiles:
                startActivity(myIntent);
                break;
            case R.id.expandableButton_ele_1:
                expandableLayout_ele_1.toggle();
                expandableLayout_ele_2.collapse();
                break;
            case R.id.bt_ele_tablet:
                startActivity(myIntent);
                break;
            case R.id.bt_ele_television:
                startActivity(myIntent);
                break;
            case R.id.bt_ele_speakers:
                startActivity(myIntent);
                break;
            case R.id.bt_ele_smartWatches:
                startActivity(myIntent);
                break;
            case R.id.bt_ele_desktopPc:
                startActivity(myIntent);
                break;
            case R.id.expandableButton_ele_2:
                expandableLayout_ele_2.toggle();
                expandableLayout_ele_1.collapse();
                break;

            case R.id.tv_electronics_ma_1:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ma_2:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ma_3:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ma_4:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ca_1:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ca_2:
                startActivity(myIntent);
                break;
            case R.id.tv_electronics_ca_3:
                startActivity(myIntent);
                break;
        }
    }
}
