package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.app.iccomm.Adapters.ProductsAdapter;
import com.app.iccomm.Adapters.SortAdapter;
import com.app.iccomm.Adapters.SubCategoriesAdapter;
import com.app.iccomm.Data.Constants;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView productsRecyclerView;
    ImageView back_arrow, imgView_shoppingCart;
    LinearLayout sort_layout, filter_layout, tryAgain_layout, sortFilterLayout, recyclerViewLayout;
    RecyclerView sortProducts;
    List<ProductsModel> listModel = new ArrayList<>();
    TextView title;
    int i = 0;
    FrameLayout parentLayout;
    Button bt_tryAgain;
    Dialog dialog;
    String snackText;
    String currency;
    SharedPreferences preferences;
    Intent intent;
    DataBaseHandler db = new DataBaseHandler(ProductsActivity.this);
    List<HomePageModel> sortList = new ArrayList<>();
    String sortId = "";
    ArrayList<VariationModel> variationModelArrayList = new ArrayList<>();
    ArrayList<VariationModel> variationSubData;
    int count = 1;
    SwipyRefreshLayout bottomRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        sort_layout = findViewById(R.id.sortLayout);
        filter_layout = findViewById(R.id.filterLayout);
        sortProducts = findViewById(R.id.sortProducts);
        parentLayout = findViewById(R.id.parentLayout);
        tryAgain_layout = findViewById(R.id.tryAgain_layout);
        sortFilterLayout = findViewById(R.id.sortFilterLayout);
        recyclerViewLayout = findViewById(R.id.recyclerViewLayout);
        bt_tryAgain = findViewById(R.id.bt_tryAgain);
        bottomRefresh = findViewById(R.id.bottomRefresh);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((ProductsActivity.this)).getSupportActionBar().setTitle("");

        intent = getIntent();

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        currency = getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null);

        dialog = new Dialog(ProductsActivity.this);
        back_arrow = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        title = findViewById(R.id.title);

        title.setVisibility(View.GONE);

        if (intent.hasExtra("sortId")) {
            sortId = intent.getStringExtra("sortId");
        }


        checkConnectivity();



        imgView_shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductsActivity.this, CartActivity.class));
            }
        });

        bottomRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                dialog.setContentView(R.layout.progress_bar_layout);
                dialog.setCancelable(true);
                dialog.show();
                count = count + 1;
                bottomRefresh.setRefreshing(false);
                getProducts();
            }
        });

        sort_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    sortProducts.setVisibility(View.VISIBLE);
                    i = 1;
                } else {
                    sortProducts.setVisibility(View.GONE);
                    i = 0;
                }
            }
        });

        filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortProducts.setVisibility(View.GONE);
                finish();
                Intent mIntent = new Intent(ProductsActivity.this, FilterActivity.class);
                mIntent.putExtra("catId", intent.getStringExtra("catId"));
                mIntent.putParcelableArrayListExtra("filterArray", variationModelArrayList);
                startActivity(mIntent);
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
            }
        });

        List<HomePageModel> mList = db.getSortingData();
        for (int i = 0; i < mList.size(); i++) {
            HomePageModel model = mList.get(i);
            HomePageModel model1 = new HomePageModel();
            model1.setSort_id(model.getSort_id());
            model1.setSort_name(model.getSort_name());
            sortList.add(model1);
            initSortRecyclerView();
        }


    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ProductsActivity.this, 2);
        ProductsAdapter mAdapter = new ProductsAdapter(ProductsActivity.this, listModel);
        productsRecyclerView.setLayoutManager(mLayoutManager);
        productsRecyclerView.setAdapter(mAdapter);

    }

    public void initSortRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductsActivity.this, LinearLayoutManager.VERTICAL, false);
        SortAdapter mAdapter = new SortAdapter(ProductsActivity.this, sortList, intent.getStringExtra("catId"));
        sortProducts.setLayoutManager(mLayoutManager);
        sortProducts.setAdapter(mAdapter);
    }


    private void getProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_PRODUCT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseProductList", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(ProductsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject products = jsonObject.getJSONObject("products");
                        JSONArray data = products.getJSONArray("data");
                        if (data.length() == 0) {
                            Toast.makeText(ProductsActivity.this, "No Products", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
//                            finish();
//                            onBackPressed();
                        } else if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                ProductsModel productsModel = new ProductsModel();
                                productsModel.setProdId(object.optString("id"));
                                productsModel.setProdName(object.optString("name"));
                                productsModel.setProdActualPrice(currency.concat(object.optString("price")));
                                productsModel.setProdStars(object.optString("rating"));
                                if (object.optString("discount_type").equals("Flat")) {
                                    Log.d("discountType", getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                                    productsModel.setProdDiscount(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                                } else {
                                    productsModel.setProdDiscount(object.optString("discount").concat(object.optString("discount_type")));
                                }
                                productsModel.setProdDiscountedPrice(currency.concat(object.optString("discounted_price")));
                                productsModel.setProdImg(object.optString("image"));
                                listModel.add(productsModel);
                                initRecyclerView();
                            }
                        }
                        dialog.dismiss();

                        if (jsonObject.has("min") && jsonObject.has("max")) {
                            VariationModel variationModel = new VariationModel();
                            variationModel.setVariation_id(0);
                            variationModel.setVariation_name("Price");

                            variationSubData = new ArrayList<>();
                            VariationModel variationModel1 = new VariationModel();
                            variationModel1.setVariation_data_id(0);
                            variationModel1.setVariation_data_value(jsonObject.optString("min"));
                            variationSubData.add(variationModel1);
                            variationModel.setVariation_data(variationSubData);
//                                variationModelArrayList.add(variationModel);

                            VariationModel variationModel2 = new VariationModel();
                            variationModel2.setVariation_data_id(0);
                            variationModel2.setVariation_data_value(jsonObject.optString("max"));
                            variationSubData.add(variationModel2);
                            variationModel.setVariation_data(variationSubData);
                            variationModelArrayList.add(variationModel);

                        }

                        if (jsonObject.has("brands")) {
                            JSONObject brands = jsonObject.getJSONObject("brands");
                            JSONArray brandArray = brands.getJSONArray("data");
                            VariationModel variationModel = new VariationModel();
                            variationModel.setVariation_id(0);
                            variationModel.setVariation_name("Brand");

                            variationSubData = new ArrayList<>();
                            for (int i = 0; i < brandArray.length(); i++) {
                                JSONObject object = brandArray.getJSONObject(i);
                                VariationModel variationModel1 = new VariationModel();
                                variationModel1.setVariation_data_id(object.optInt("brand_id"));
                                variationModel1.setVariation_data_value(object.optString("brand_name"));
                                variationSubData.add(variationModel1);
                                variationModel.setVariation_data(variationSubData);
                            }
                            variationModelArrayList.add(variationModel);
                        }

                        JSONObject variation = jsonObject.getJSONObject("variation");
                        JSONArray variationDataArray = variation.getJSONArray("data");
                        for (int i = 0; i < variationDataArray.length(); i++) {
                            JSONObject object = variationDataArray.getJSONObject(i);
                            VariationModel variationModel = new VariationModel();
                            JSONArray variationData = object.getJSONArray("data");

                            if (variationData.length() > 0) {
                                variationModel.setVariation_id(object.optInt("variation_id"));
                                variationModel.setVariation_name(object.optString("variation_name"));

                                variationSubData = new ArrayList<>();

                                if (object.optString("variation_name").equals("color")) {
                                    for (int j = 0; j < variationData.length(); j++) {
                                        VariationModel variationModel1 = new VariationModel();
                                        JSONObject object1 = variationData.getJSONObject(j);
                                        variationModel1.setVariation_data_id(object1.optInt("id"));
                                        variationModel1.setVariation_data_value(object1.optString("colorname"));

                                        variationSubData.add(variationModel1);
                                        variationModel.setVariation_data(variationSubData);
                                        Log.d("variationArrayList", String.valueOf(variationSubData.size()));
                                    }
                                    variationModelArrayList.add(variationModel);
                                    Log.d("variationArrayList", String.valueOf(variationModelArrayList.size()));

                                } else {
                                    variationSubData = new ArrayList<>();

                                    for (int j = 0; j < variationData.length(); j++) {
                                        VariationModel variationModel1 = new VariationModel();
                                        JSONObject object1 = variationData.getJSONObject(j);
                                        variationModel1.setVariation_data_id(object1.optInt("id"));
                                        variationModel1.setVariation_data_value(object1.optString("value"));

                                        variationSubData.add(variationModel1);
                                        variationModel.setVariation_data(variationSubData);
                                        Log.d("variationArrayList", String.valueOf(variationSubData.size()));
                                    }
                                    variationModelArrayList.add(variationModel);
                                    Log.d("variationArrayList", String.valueOf(variationModelArrayList.size()));
                                }
                            }
                        }


                    } else {
                        Toast.makeText(ProductsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(ProductsActivity.this, "Something Went Wrong" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d("error", String.valueOf(error));
                dialog.dismiss();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", intent.getStringExtra("catId"));
                params.put("offset", String.valueOf(count));
                if (!sortId.equals("")) {
                    params.put("sort", sortId);
                }
                if (intent.hasExtra("Filter")) {
                    params.put("min", intent.getStringExtra("min"));
                    params.put("max", intent.getStringExtra("max"));

                    ArrayList<String> brandArray = new ArrayList<>();
                    brandArray = intent.getStringArrayListExtra("brandArray");

                    if (brandArray.size()>0){
                        Log.d("brandArrayActivity", String.valueOf(brandArray.size()));
                        for (int i = 0; i < brandArray.size(); i++) {
                            Log.d("brandArrayActivity", String.valueOf(brandArray.get(i)));
                        }
                        String data = new Gson().toJson(brandArray);
                        params.put("brand",data);
                    }

                    ArrayList<String> filterArray = new ArrayList<>();
                    filterArray = intent.getStringArrayListExtra("filterArray");
                    if (filterArray.size()>0){
                        Log.d("filterArrayActivity", String.valueOf(filterArray.size()));
                        for (int i = 0; i < filterArray.size(); i++) {
                            Log.d("filterArrayActivity", String.valueOf(filterArray.get(i)));
                        }
                        String data = new Gson().toJson(filterArray);
                        params.put("filter",data);
                    }


                }

                Log.d("paramsLog", String.valueOf(params));
                return params;
            }


        };
        RequestQueue queue = Volley.newRequestQueue(ProductsActivity.this);
        queue.add(stringRequest);

    }

    public void getSearchResult() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_SEARCH_RESULT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseProductList", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(ProductsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject products = jsonObject.getJSONObject("products");
                        JSONArray data = products.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setProdId(object.optString("id"));
                            productsModel.setProdName(object.optString("name"));
                            if (object.optString("rating").equals("null")) {
                                productsModel.setProdStars(object.optString("rating"));
                            } else {
                                productsModel.setProdStars("null");
                            }
                            productsModel.setProdActualPrice(currency.concat(object.optString("price")));
                            if (object.optString("discount_type").equals("Flat")) {
                                Log.d("discountType", getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                                productsModel.setProdDiscount(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                            } else {
                                productsModel.setProdDiscount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            productsModel.setProdDiscountedPrice(object.optString("discounted_price"));
                            productsModel.setProdImg(object.optString("image"));
                            listModel.add(productsModel);
                            initRecyclerView();
                        }
                        dialog.dismiss();

                    } else {
                        Toast.makeText(ProductsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(ProductsActivity.this, "Something Went Wrong" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(ProductsActivity.this, "Something Went Wrong Volley" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key", intent.getStringExtra("searchResult"));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProductsActivity.this);
        queue.add(stringRequest);
    }


    public void snackBar(String text) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = View.inflate(ProductsActivity.this, R.layout.my_snackbar, null);
        // Configure the view

        TextView textViewTop = (TextView) snackView.findViewById(R.id.textViewTop);
        textViewTop.setText(text);
        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        snackbar.show();
    }


    public void checkConnectivity() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    tryAgain_layout.setVisibility(View.GONE);
                    sortFilterLayout.setVisibility(View.VISIBLE);
                    recyclerViewLayout.setVisibility(View.VISIBLE);
                    if (intent.hasExtra("search")) {
                        getSearchResult();
                    } else if (intent.hasExtra("featureListing")) {
                        getFeatureListing(intent.getStringExtra("featureListing"));
                    } else if (intent.hasExtra("Brand")) {
                        getBrandProducts();
                    } else {
                        getProducts();
                    }
                } else {
                    dialog.cancel();
                    tryAgain_layout.setVisibility(View.VISIBLE);
                    sortFilterLayout.setVisibility(View.GONE);
                    recyclerViewLayout.setVisibility(View.GONE);
                }
            }
        }, 2000);


    }

    public void getFeatureListing(final String key) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_HOME_PAGE_VIEW_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseProductList", response);
                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.optString("status").equals("true")) {
//                        Toast.makeText(ProductsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject products = jsonObject.getJSONObject("products");
                    JSONArray data = products.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        ProductsModel productsModel = new ProductsModel();
                        productsModel.setProdId(object.optString("id"));
                        productsModel.setProdName(object.optString("name"));
                        productsModel.setProdActualPrice(currency.concat(object.optString("price")));
                        productsModel.setProdStars("null");
                        if (object.optString("discount_type").equals("Flat")) {
                            productsModel.setProdDiscount(object.optString("discount").concat("%"));
                        } else {
                            productsModel.setProdDiscount(object.optString("discount").concat(object.optString("discount_type")));
                        }
                        productsModel.setProdDiscountedPrice(object.optString("discounted_price"));
                        productsModel.setProdImg(object.optString("image"));
                        listModel.add(productsModel);
                        initRecyclerView();
                    }
                    dialog.dismiss();

//                    } else {
//                        Toast.makeText(ProductsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(ProductsActivity.this, "Something Went Wrong" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(ProductsActivity.this, "Something Went Wrong Volley" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key, "1");
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(ProductsActivity.this);
        queue.add(stringRequest);

    }

    public void getBrandProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_HOME_BRAND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseBrand", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(ProductsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject products = jsonObject.getJSONObject("products");
                        JSONArray data = products.getJSONArray("data");
                        if (data.length() == 0) {
                            Toast.makeText(ProductsActivity.this, "No Products Found", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setProdId(object.optString("id"));
                            productsModel.setProdName(object.optString("name"));
                            productsModel.setProdActualPrice(object.optString("price"));
                            if (object.optString("rating").equals("null")) {
                                productsModel.setProdStars("null");
                            } else {
                                productsModel.setProdStars(object.optString("rating"));

                            }
                            if (object.optString("discount_type").equals("Flat")) {
                                productsModel.setProdDiscount(object.optString("discount").concat("%"));
                            } else {
                                productsModel.setProdDiscount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            productsModel.setProdDiscountedPrice(object.optString("discounted_price"));
                            productsModel.setProdImg(object.optString("image"));
                            listModel.add(productsModel);
                            initRecyclerView();
                        }
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(ProductsActivity.this, "Something Went Wrong" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(ProductsActivity.this, "Something Went Wrong" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("offset", "1");
                params.put("brand_id", intent.getStringExtra("brand_id"));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProductsActivity.this);
        queue.add(stringRequest);
    }


}
