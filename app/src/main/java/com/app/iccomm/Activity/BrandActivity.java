package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Adapters.BrandViewAllAdapter;
import com.app.iccomm.Adapters.HomeBrandAdapter;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrandActivity extends AppCompatActivity {
    RecyclerView brand_rv;
    List<HomePageModel> modelList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        brand_rv = findViewById(R.id.brand_rv);

        dialog = new Dialog(BrandActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);
        ((BrandActivity.this)).getSupportActionBar().setTitle("Brands");


        getBrands();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(BrandActivity.this, 2);
        BrandViewAllAdapter brandAdapter = new BrandViewAllAdapter(BrandActivity.this, modelList);
        brand_rv.setLayoutManager(linearLayoutManager);
        brand_rv.setAdapter(brandAdapter);

    }

    private void getBrands() {
        dialog.show();
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_HOME_PAGE_VIEW_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseBrand", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject brands = jsonObject.getJSONObject("brands");
                    JSONArray data = brands.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        HomePageModel homePageModel = new HomePageModel();
                        homePageModel.setBrand_id(object.optString("brand_id"));
                        homePageModel.setBrand_img(object.optString("brand_img"));
                        homePageModel.setBrand_name(object.optString("brand_name"));
                        modelList.add(homePageModel);
                        initRecyclerView();
                    }
                    dialog.cancel();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(BrandActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    fileList();
                    startActivity(new Intent(BrandActivity.this, MainActivity.class));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BrandActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                fileList();
                startActivity(new Intent(BrandActivity.this, MainActivity.class));

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("brands", "1");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(BrandActivity.this);
        queue.add(stringRequest);

    }
}
