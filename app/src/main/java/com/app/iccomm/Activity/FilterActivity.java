package com.app.iccomm.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Adapters.FilterAdapter;
import com.app.iccomm.Adapters.FilterNameAdapter;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {
    RecyclerView rv_filterName, rv_filterValue;
    RangeSeekBar rangeSeekBar;
    TextView tv_cancelFilter, tv_applyFilter, tv_minRange, tv_maxRange;
    LinearLayout linear_rangeLayout;
    Intent mIntent;
    List<VariationModel> filterNameList = new ArrayList<>();
    List<VariationModel> filterDataList;
    ArrayList<VariationModel> variationModels = new ArrayList<>();
    String minPrice, maxPrice;
    ArrayList<String> brandArray = new ArrayList<>();
    ArrayList<String> filterArray = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        rv_filterName = findViewById(R.id.rv_filterName);
        rv_filterValue = findViewById(R.id.rv_filterValue);
        tv_minRange = findViewById(R.id.tv_minRange);
        tv_maxRange = findViewById(R.id.tv_maxRange);
        rangeSeekBar = findViewById(R.id.rangeSeekBar);
        linear_rangeLayout = findViewById(R.id.linear_rangeLayout);
        tv_cancelFilter = findViewById(R.id.tv_cancelFilter);
        tv_applyFilter = findViewById(R.id.tv_applyFilter);

        mIntent = getIntent();

        variationModels = getIntent().getParcelableArrayListExtra("filterArray");

        for (int i = 0; i < variationModels.size(); i++) {
            VariationModel variationModel = new VariationModel();
            variationModel.setVariation_id(variationModels.get(i).getVariation_id());
            variationModel.setVariation_name(variationModels.get(i).getVariation_name());

            filterDataList = new ArrayList<>();
            for (int j = 0; j < variationModels.get(i).getVariation_data().size(); j++) {
                VariationModel variationDataModel = new VariationModel();
                variationDataModel.setVariation_data_id(variationModels.get(i).getVariation_data().get(j).getVariation_data_id());
                variationDataModel.setVariation_data_value(variationModels.get(i).getVariation_data().get(j).getVariation_data_value());

                filterDataList.add(variationDataModel);
                variationModel.setVariation_data(filterDataList);
//                initRecyclerViewColorFilter();
            }

            filterNameList.add(variationModel);
            initRecyclerViewFilterName();
        }

        tv_cancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Filter Applied", Snackbar.LENGTH_LONG).show();
                Intent product = new Intent(FilterActivity.this, ProductsActivity.class);
                product.putExtra("Filter", "true");
                product.putExtra("catId", mIntent.getStringExtra("catId"));
                product.putExtra("min", minPrice);
                product.putExtra("max", maxPrice);
                product.putStringArrayListExtra("brandArray", brandArray);
                product.putStringArrayListExtra("filterArray", filterArray);

                finish();
                startActivity(product);
//                onBackPressed();
            }
        });
//        tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorWhite));


//        tv_priceFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                filter_recyclerView.setVisibility(View.GONE);
//                linear_rangeLayout.setVisibility(View.VISIBLE);
//
//                rangeSeekBar.setIndicatorTextDecimalFormat("100");
//                rangeSeekBar.setValue(100f, 1200f);
//                rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
//                    @Override
//                    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
//
//                    }
//                });
//            }
//        });

//        tv_brandFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                filter_recyclerView.setVisibility(View.VISIBLE);
//                linear_rangeLayout.setVisibility(View.GONE);
//                initRecyclerViewFilter();
//            }
//        });

//        tv_sizeFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                filter_recyclerView.setVisibility(View.VISIBLE);
//                linear_rangeLayout.setVisibility(View.GONE);
//                initRecyclerViewFilter();
//            }
//        });
//        tv_colorFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                filter_recyclerView.setVisibility(View.VISIBLE);
//                linear_rangeLayout.setVisibility(View.GONE);
//                initRecyclerViewColorFilter();
//            }
//        });
//        tv_fabricFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                filter_recyclerView.setVisibility(View.VISIBLE);
//                linear_rangeLayout.setVisibility(View.GONE);
//                initRecyclerViewFilter();
//            }
//        });
//        tv_patternFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_priceFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_brandFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_sizeFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_colorFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_fabricFilter.setBackground(getResources().getDrawable(R.color.colorButton));
//                tv_patternFilter.setBackground(getResources().getDrawable(R.color.colorWhite));
//                filter_recyclerView.setVisibility(View.VISIBLE);
//                linear_rangeLayout.setVisibility(View.GONE);
//                initRecyclerViewFilter();
//            }
//        });

//        initRecyclerViewFilter();
//        initRecyclerViewColorFilter();

    }

    public void price(String min, String max) {
        Log.d("minMax", min + " " + max);
        linear_rangeLayout.setVisibility(View.VISIBLE);
        rv_filterValue.setVisibility(View.GONE);
        rangeSeekBar.setIndicatorTextDecimalFormat("100");
        if (!min.equals(max)){
            rangeSeekBar.setRange(Float.parseFloat(min), Float.parseFloat(max));
            rangeSeekBar.setValue(Float.parseFloat(min));
        }else {
//            rangeSeekBar.setValue(Float.parseFloat(max));
        }
        tv_minRange.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + min);
        tv_maxRange.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + max);

        minPrice = min;
        maxPrice = max;

        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                minPrice = String.valueOf((int) (leftValue));
                maxPrice = String.valueOf((int) (rightValue));
                Log.d("valueRange", String.valueOf((int) (leftValue)));
                Log.d("valueRange", String.valueOf((int) (rightValue)));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

//        filterNameList = new ArrayList<>();
//        for (int a = 0; a < s.size(); a++) {
//            if (s.get(a).getFilterSelected()) {
////                ProductsModel productsModel = new ProductsModel();
//                selectedFrame = s.get(a).getFilter_name();
//                setLayout(selectedFrame);
//                Log.d("selectedFrame", selectedFrame);
//
//            }
//
//            Log.d("working", String.valueOf(s.get(a).getFilter_name_id()));
//            Log.d("working", String.valueOf(s.get(a).getFilter_name()));
//            Log.d("working", String.valueOf(s.get(a).getFilterSelected()));
//            Toast.makeText(this, "Please start working.....:(:(" + s.get(a).getFilterSelected(), Toast.LENGTH_SHORT).show();
//        }
    }

    public void setLayout(String selectedFrame) {
        if ("Price".equals(selectedFrame)) {
            rv_filterValue.setVisibility(View.GONE);
            linear_rangeLayout.setVisibility(View.VISIBLE);
        } else {
            rv_filterValue.setVisibility(View.VISIBLE);
            linear_rangeLayout.setVisibility(View.GONE);
        }
    }


    private void initRecyclerViewFilterName() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        FilterNameAdapter mAdapter = new FilterNameAdapter(FilterActivity.this, filterNameList);
        rv_filterName.setLayoutManager(layoutManager);
        rv_filterName.setAdapter(mAdapter);
    }

    public void initRecyclerViewColorFilter(List<VariationModel> dataList, String variantType) {
        linear_rangeLayout.setVisibility(View.GONE);
        rv_filterValue.setVisibility(View.VISIBLE);
        Log.d("listSize", String.valueOf(dataList.size()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        FilterAdapter mAdapter = new FilterAdapter(FilterActivity.this, dataList, true, variantType);
        rv_filterValue.setLayoutManager(layoutManager);
        rv_filterValue.setAdapter(mAdapter);
    }

    public void filterBrand(String brandId, String value) {
        if (value.equals("Add")) {
            brandArray.add(brandId);
        } else if (value.equals("Remove")) {
            brandArray.remove(brandId);
        }
        Log.d("brandArray", String.valueOf(brandArray.size()));
        for (int i = 0; i < brandArray.size(); i++) {
            Log.d("brandArray", String.valueOf(brandArray.get(i)));
        }


    }

    public void filterVariant(String variantId, String value) {
        if (value.equals("Add")) {
            filterArray.add(variantId);
        } else if (value.equals("Remove")) {
            filterArray.remove(variantId);
        }
        Log.d("filterArray", String.valueOf(filterArray.size()));
        for (int i = 0; i < filterArray.size(); i++) {
            Log.d("filterArray", String.valueOf(filterArray.get(i)));
        }


    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(FilterActivity.this,ProductsActivity.class).putExtra("catId",mIntent.getStringExtra("catId")));
    }
}
