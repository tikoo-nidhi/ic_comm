package com.app.iccomm.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Adapters.HomeAdapter;
import com.app.iccomm.Adapters.SearchAdapter;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    //    TextInputEditText textInputLay;
    SearchView searchText;
    //    ImageView iv_search;
    RecyclerView rv_search;
    List<String> listSearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((SearchActivity.this)).getSupportActionBar().setTitle("");
//        textInputLay = findViewById(R.id.textInputLay);
        searchText = findViewById(R.id.searchText);
//        iv_search = findViewById(R.id.iv_search);
        rv_search = findViewById(R.id.rv_search);

        searchText.setFocusable(true);

        listSearch.clear();


        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent mIntent = new Intent(SearchActivity.this, ProductsActivity.class);
                mIntent.putExtra("search", true);
                mIntent.putExtra("searchResult", query);
                startActivity(mIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listSearch.clear();
                Log.d("newText", newText);
                getSearch(newText);
                return false;
            }
        });


    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        SearchAdapter mAdapter = new SearchAdapter(SearchActivity.this, listSearch);
        rv_search.setLayoutManager(mLayoutManager);
        rv_search.setAdapter(mAdapter);
    }

    public void getSearch(final String key) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_SEARCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("searchResult", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject products = jsonObject.getJSONObject("products");
                    JSONArray data = products.getJSONArray("data");
                    listSearch.clear();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        listSearch.add(object.optString("name"));
                        initRecyclerView();
                    }
                    Log.d("search_size1", String.valueOf(listSearch.size()));

                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, "Something Went Wrong Come Back Later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Something Went Wrong Come Back Later", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("key",key);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        queue.add(stringRequest);

    }

}
