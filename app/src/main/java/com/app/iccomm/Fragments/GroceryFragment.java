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

public class GroceryFragment extends Fragment implements View.OnClickListener{
    Intent myIntent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grocery_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Grocery");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myIntent = new Intent(getContext(), ProductsActivity.class);
        view.findViewById(R.id.bt_grocery_1).setOnClickListener(this);
        view.findViewById(R.id.bt_grocery_2).setOnClickListener(this);
        view.findViewById(R.id.bt_grocery_3).setOnClickListener(this);
        view.findViewById(R.id.bt_grocery_4).setOnClickListener(this);
        view.findViewById(R.id.bt_grocery_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_grocery_1:
                startActivity(myIntent);
                break;
            case R.id.bt_grocery_2:
                startActivity(myIntent);
                break;
            case R.id.bt_grocery_3:
                startActivity(myIntent);
                break;
            case R.id.bt_grocery_4:
                startActivity(myIntent);
                break;
            case R.id.bt_grocery_5:
                startActivity(myIntent);
                break;
        }

    }
}
