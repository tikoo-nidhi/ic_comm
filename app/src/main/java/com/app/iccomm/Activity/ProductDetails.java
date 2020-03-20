package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.app.iccomm.Adapters.BoughtTogetherAdapter;
import com.app.iccomm.Adapters.ImageAdapter;
import com.app.iccomm.Adapters.ReviewsRatingsAdapter;
import com.app.iccomm.Adapters.SelectColorAdapter;
import com.app.iccomm.Adapters.SelectSizeAdapter;
import com.app.iccomm.Adapters.SimilarProductsAdapter;
import com.app.iccomm.Data.Constants;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.CartModel;
import com.app.iccomm.Model.ProductsModel;
import com.app.iccomm.Model.VariationModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.NoNetwork;
import com.app.iccomm.R;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {
    SliderLayout slider_prodDetailsImg;
    LinearLayout linear_prod_wish, linear_Prod_addToCart, wholeLayout, parentLayout;
    TextView tv_discountedPercentage, tv_discountedPrice, tv_prod_name, tv_prod_price, tv_prod_sizeChart, tv_prod_size, tv_prod_desc, tv_prod_material, tv_prod_viewAll_frequentProd,
            tv_write_review, tv_prod_moreReviews, tv_viewAll_similarProd, tv_wishList, tv_cart, title, tv_prodRating_1, tv_prod_details;
    RecyclerView boughtTogetherView, similarProductsView, reviewsRatingsView, colorRecyclerView, selectSizeRecyclerView;
    ImageView back_arrow, imgView_wishList, imgView_shoppingCart, imgView_zoomImg;
    int wish = 0;
    int cart = 0;
    ViewPager viewPager_zoomImg;
    private ArrayList<String> mImageIds = new ArrayList<>();
    ImageAdapter adapter;
    FrameLayout fl_zoomImg;
    Intent intent;
    Dialog dialog;
    String snackText;
    List<CartModel> mList = new ArrayList<>();
    CartModel cartModel = new CartModel();
    DataBaseHandler db;
    String url;
    SharedPreferences preferences;
    Boolean isLogged;
    String img_url_color;
    List<ProductsModel> productsModelList = new ArrayList<>();
    List<VariationModel> variationModelList;
    CardView cv_review, cv_similarProd;
    LinearLayout colorLay, sizeLay;
    String variantSize = "";
    String variantColor = "";

    int priceProd;
//    Dialog dialog;
//    List<ProductsModel> productsModelListSize = new ArrayList<>();
//    List<ProductsModel> productsModelListReview = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        back_arrow = findViewById(R.id.left_arrow_appBar);
        slider_prodDetailsImg = findViewById(R.id.slider_prodDetailsImg);
        linear_prod_wish = findViewById(R.id.linear_prod_wish);
        linear_Prod_addToCart = findViewById(R.id.linear_Prod_addToCart);
        tv_discountedPrice = findViewById(R.id.tv_discountedPrice);
        tv_discountedPercentage = findViewById(R.id.tv_discountedPercentage);
        tv_prod_name = findViewById(R.id.tv_prod_name);
        tv_prod_price = findViewById(R.id.tv_prod_price);
        tv_prod_sizeChart = findViewById(R.id.tv_prod_sizeChart);
        tv_prod_size = findViewById(R.id.tv_prod_size);
        tv_prod_desc = findViewById(R.id.tv_prod_desc);
        tv_prod_material = findViewById(R.id.tv_prod_material);
        tv_prod_viewAll_frequentProd = findViewById(R.id.tv_prod_viewAll_frequentProd);
        tv_write_review = findViewById(R.id.tv_write_review);
        tv_prod_moreReviews = findViewById(R.id.tv_prod_moreReviews);
        tv_viewAll_similarProd = findViewById(R.id.tv_viewAll_similarProd);
        boughtTogetherView = findViewById(R.id.boughtTogetherView);
        similarProductsView = findViewById(R.id.similarProductsView);
        reviewsRatingsView = findViewById(R.id.reviewsRatingsView);
        colorRecyclerView = findViewById(R.id.colorRecyclerView);
        selectSizeRecyclerView = findViewById(R.id.selectSizeRecyclerView);
        imgView_wishList = findViewById(R.id.imgView_wishList);
        tv_wishList = findViewById(R.id.tv_wishList);
        tv_cart = findViewById(R.id.tv_cart);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        viewPager_zoomImg = findViewById(R.id.viewPager_zoomImg);
        fl_zoomImg = findViewById(R.id.fl_zoomImg);
        imgView_zoomImg = findViewById(R.id.imgView_zoomImg);
        title = findViewById(R.id.title);
        wholeLayout = findViewById(R.id.wholeLayout);
        parentLayout = findViewById(R.id.parentLayout);
        tv_prodRating_1 = findViewById(R.id.tv_prodRating_1);
        tv_prod_details = findViewById(R.id.tv_prod_details);
        cv_review = findViewById(R.id.cv_review);
        cv_similarProd = findViewById(R.id.cv_similarProd);
        colorLay = findViewById(R.id.colorLay);
        sizeLay = findViewById(R.id.sizeLay);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED, false);
        dialog = new Dialog(ProductDetails.this);
        dialog.setContentView(R.layout.progress_bar_layout);


        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ProductDetails.this)).getSupportActionBar().setTitle("");
        title.setVisibility(View.GONE);

        db = new DataBaseHandler(ProductDetails.this);

        getProdDetails();


        tv_prod_price.setPaintFlags(tv_prod_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        adapter = new ImageAdapter(ProductDetails.this, mImageIds);

        slider_prodDetailsImg.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        slider_prodDetailsImg.setScrollTimeInSec(100000);
//        slideView();

        imgView_zoomImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom_out = AnimationUtils.loadAnimation(ProductDetails.this, R.anim.zoom_out);
                fl_zoomImg.startAnimation(zoom_out);
                fl_zoomImg.setVisibility(View.INVISIBLE);
            }
        });

        imgView_shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, CartActivity.class));
            }
        });

        tv_prod_moreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, AllReviewsActivity.class));
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, WriteReviewActivity.class));
            }
        });

        linear_prod_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wish == 0) {
                    if (isLogged) {
                        addToWishList();
                        imgView_wishList.setImageResource(R.drawable.ic_wishlist_fill);
                        tv_wishList.setTextColor(getResources().getColor(R.color.colorLogoBlue));
                        linear_prod_wish.setBackground(getResources().getDrawable(R.drawable.back));

                        tv_wishList.setText("Wishlisted");
                        wish = 1;
                        shakeIt();
//                        Snackbar.make(v, "Added to wishlist", 2000).setAction("Action", null).show();
                    } else {
                        snackBar("Go to Accounts and Log In First", false);
                    }

                } else {
                    startActivity(new Intent(ProductDetails.this, WishListActivity.class));
                }

            }
        });

        linear_Prod_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cart == 0) {
                    addToCart();
                    tv_cart.setText("Go To Cart");
                    cart = 1;
                    shakeIt();
//                    Snackbar.make(v, "Added to Cart", 2000).setAction("Action", null).show();
                } else {
                    startActivity(new Intent(ProductDetails.this, CartActivity.class));
                }

            }
        });

        tv_prod_viewAll_frequentProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, ProductsActivity.class));
            }
        });

        tv_viewAll_similarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, ProductsActivity.class));
            }
        });
        initRecyclerViewBoughtTogether();
        initRecyclerViewSimilarProducts();
        initRecyclerViewRatings();


    }

    public void shakeIt() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    public void addToCart() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("responseCart", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
//                        Toast.makeText(ProductDetails.this, "Item Added To Cart", Toast.LENGTH_SHORT).show();
                        snackBar("Item Added To Cart", false);

                    } else {
                        snackBar("Item Not Added To Cart", true);
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    snackBar("Something Went Wrong Come Back Later", true);
                    dialog.dismiss();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackBar("Something Went Wrong Come Back Later", true);
                dialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences spf = getSharedPreferences("udid", MODE_PRIVATE);
//                params.put("action", "addToCart");
                if (isLogged) {
                    params.put("uid", preferences.getString(SharedPref.USER_ID, null));
                } else {
                    params.put("udid", spf.getString(SharedPref.PHONE_ID, null));
                }
                params.put("id", intent.getStringExtra("prodId"));
                params.put("qty", "1");
                if (variantSize.equals("") | variantColor.equals("")) {
                    if (variantSize.equals("") && variantColor.equals("")) {
                        params.put("variant", "");
                    } else {
                        params.put("variant", "Size:" + variantSize + "," + "Color:" + variantColor);
                    }
                } else {
                    params.put("variant", "Size:" + variantSize + "," + "Color:" + variantColor);
                }
                Log.d("paramsLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProductDetails.this);
        queue.add(stringRequest);
    }

//    public void addToWishList() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_WISH_LIST, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("wishResponse", response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.optString("status").equals("true")) {
//                        snackBar("Item Added To Wish List", false);
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                snackBar("Something Went Wrong Come Back Later", true);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("action", "add");
//                params.put("user_id", preferences.getString(SharedPref.USER_ID, null));
//                params.put("product_id", intent.getStringExtra("prodId"));
//                return params;
//            }
//
//
//        };
//        RequestQueue queue = Volley.newRequestQueue(ProductDetails.this);
//        queue.add(stringRequest);
//    }

//    private void slideView() {
//        for (int i = 0; i <= 3; i++) {
//
//            SliderView sliderView = new SliderView(ProductDetails.this);
//
//            switch (i) {
//                case 0:
//                    sliderView.setImageDrawable(R.drawable.img_1);
//                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
//                        @Override
//                        public void onSliderClick(SliderView sliderView) {
//
//                            Animation zoom_in = AnimationUtils.loadAnimation(ProductDetails.this, R.anim.zoom_in);
//                            fl_zoomImg.setVisibility(View.VISIBLE);
//                            viewPager_zoomImg.setAdapter(adapter);
//                            fl_zoomImg.startAnimation(zoom_in);
//                        }
//                    });
//                    break;
//                case 1:
//                    sliderView.setImageDrawable(R.drawable.img_2);
//                    break;
//                case 2:
//                    sliderView.setImageDrawable(R.drawable.img_3);
//                    break;
//                case 3:
//                    sliderView.setImageDrawable(R.drawable.img_4);
//                    break;
//            }
//            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
//            slider_prodDetailsImg.addSliderView(sliderView);
//            if (i == 3)
//                break;
//        }
//    }

    public void getProdDetails() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_PRODUCT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("detailResponse", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(ProductDetails.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject images = jsonObject.getJSONObject("images");
                        JSONArray images_array = images.getJSONArray("data");
                        Log.d("detailResponse", String.valueOf(images_array));

                        for (int i = 0; i < images_array.length(); i++) {
                            SliderView sliderView = new SliderView(ProductDetails.this);
                            JSONObject object = images_array.getJSONObject(i);
                            sliderView.setImageUrl(object.optString("image"));
                            slider_prodDetailsImg.addSliderView(sliderView);
                            mImageIds.add(object.optString("image"));
                            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(SliderView sliderView) {

                                    Animation zoom_in = AnimationUtils.loadAnimation(ProductDetails.this, R.anim.zoom_in);
                                    fl_zoomImg.setVisibility(View.VISIBLE);
                                    viewPager_zoomImg.setAdapter(adapter);
                                    fl_zoomImg.startAnimation(zoom_in);
                                }
                            });
                        }

                        JSONObject product = jsonObject.getJSONObject("product");
                        Log.d("ratingProd", product.optString("rating"));
                        if (product.optString("rating").equals("null")) {
                            tv_prodRating_1.setVisibility(View.GONE);
                        } else {
                            tv_prodRating_1.setText(product.optString("rating").substring(0, 3));
                        }
                        Log.d("isWishListed", product.optString("wishlist"));
                        if (product.optString("wishlist").equals("true")) {
                            imgView_wishList.setImageResource(R.drawable.ic_wishlist_fill);
                            tv_wishList.setTextColor(getResources().getColor(R.color.colorLogoBlue));
                            linear_prod_wish.setBackground(getResources().getDrawable(R.drawable.back));
                            tv_wishList.setText("Wishlisted");
                            wish = 1;
                            shakeIt();
                        }
                        tv_prod_name.setText(product.optString("name"));
                        if (product.optString("discount").startsWith("0")) {
                            tv_discountedPercentage.setVisibility(View.GONE);
                            tv_prod_price.setVisibility(View.GONE);
                            priceProd = product.optInt("discounted_price");
                            tv_discountedPrice.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("price"));
                        } else {
                            if (product.optString("discount_type").equals("Flat")) {
                                tv_discountedPrice.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("discounted_price"));
                                tv_prod_price.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("price"));
                                priceProd = product.optInt("discounted_price");
                                tv_discountedPercentage.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("discount").concat(" OFF"));
                            } else {
                                tv_discountedPrice.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("discounted_price"));
                                tv_prod_price.setText(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + product.optString("price"));
                                priceProd = product.optInt("discounted_price");
                                tv_discountedPercentage.setText(product.optString("discount").concat("% OFF"));
                            }
                        }

                        JSONObject variations = jsonObject.getJSONObject("variation");
                        JSONArray data = variations.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            VariationModel variationModel = new VariationModel();
                            variationModel.setVariation_id(object.optInt("variation_id"));
                            variationModel.setVariation_name(object.optString("variation_name"));
                            if (object.optString("variation_name").equals("size")) {
                                sizeLay.setVisibility(View.VISIBLE);
                                variationModelList = new ArrayList<>();
                                JSONArray dataArray = object.getJSONArray("data");
                                for (int j = 0; j < dataArray.length(); j++) {
                                    JSONObject object1 = dataArray.getJSONObject(j);
                                    VariationModel variationModel1 = new VariationModel();
                                    variationModel1.setVariation_data_value(object1.optString("value"));
                                    variationModel1.setvPrice(object1.optString("vprice"));
                                    variationModelList.add(variationModel1);
                                    initRecyclerViewSelectSize();
                                }

                            } else if (object.optString("variation_name").equals("color")) {
                                colorLay.setVisibility(View.VISIBLE);
                                variationModelList = new ArrayList<>();
                                JSONArray dataArray = object.getJSONArray("data");
                                for (int j = 0; j < dataArray.length(); j++) {
                                    JSONObject object1 = dataArray.getJSONObject(j);
                                    VariationModel variationModel1 = new VariationModel();
                                    variationModel1.setVariation_data_value(object1.optString("value"));
                                    variationModel1.setVariationColorName(object1.optString("colorname"));
                                    variationModel1.setvPrice(object1.optString("vprice"));

                                    variationModelList.add(variationModel1);
                                    initRecyclerViewSelectColor();
                                }

                            }
//                            productsModel.setVariationPrice(object.optString("image"));
//                            productsModel.setVariationImg(object.optString("price"));
//                            productsModelList.add(productsModel);
//                            initRecyclerViewSelectColor();
                        }
//                        productsModelList = new ArrayList<>();
//                        JSONArray size_array = data.getJSONArray("Size");
//                        for (int i = 0; i < size_array.length(); i++) {
//                            JSONObject object = size_array.getJSONObject(i);
//                            ProductsModel productsModel = new ProductsModel();
//                            productsModel.setVariationId(object.optString("variation_id"));
//                            productsModel.setVariationValue(object.optString("variation_value"));
//                            productsModel.setVariationPrice(object.optString("image"));
//                            productsModel.setVariationImg(object.optString("price"));
//                            productsModelList.add(productsModel);
//                            initRecyclerViewSelectSize();
//                        }

//                        tv_prod_details.setText(product.optString("details"));
                        tv_prod_details.setVisibility(View.GONE);
                        // REMOVE HTML BRACES AND CODES
                        String html = product.optString("description");
                        html = html.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
                        html = html.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
                        html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                        html = html.replaceAll("&nbsp;", " ");
                        html = html.replaceAll("&amp;", "&");
                        tv_prod_desc.setText(html);

                        JSONObject review = jsonObject.getJSONObject("review");
                        JSONArray review_data = review.getJSONArray("data");
                        Log.d("sizeReview", String.valueOf(review_data.length()));

                        if (review_data.length() == 0) {
                            reviewsRatingsView.setVisibility(View.GONE);
                            cv_review.setVisibility(View.GONE);
                        } else {
                            productsModelList = new ArrayList<>();
                            for (int i = 0; i < review_data.length(); i++) {
                                JSONObject object = review_data.getJSONObject(i);
                                ProductsModel productsModel = new ProductsModel();
                                productsModel.setReview_id(object.optString("review_id"));
                                productsModel.setReview_heading(object.optString("sortreview"));
                                productsModel.setReview_rating(object.optString("rating"));
                                productsModel.setReview_text(object.optString("review"));
                                productsModel.setUser_name(object.optString("username"));
                                productsModel.setReview_date(object.optString("review_date").substring(0, 10));
                                productsModelList.add(productsModel);
                                initRecyclerViewRatings();
                            }
                        }

                        JSONObject similar = jsonObject.getJSONObject("similar");
                        JSONArray similar_array = similar.getJSONArray("data");
                        if (similar_array.length() == 0) {
                            similarProductsView.setVisibility(View.GONE);
                            cv_similarProd.setVisibility(View.GONE);
                        } else {
                            productsModelList = new ArrayList<>();
                            for (int i = 0; i < similar_array.length(); i++) {
                                JSONObject object = similar_array.getJSONObject(i);
                                ProductsModel productsModel = new ProductsModel();
                                productsModel.setSimilar_prod_id(object.optString("id"));
                                productsModel.setSimilar_prod_img(object.optString("image"));
                                if (object.optString("discount_type").equals("Flat")) {
                                    productsModel.setSimilar_prod_discount(getSharedPreferences("cur", MODE_PRIVATE).getString(SharedPref.CURRENCY, null) + object.optString("discount"));
                                } else {
                                    productsModel.setSimilar_prod_discount(object.optString("discount").concat(object.optString("discount_type")));
                                }
                                productsModel.setSimilar_prod_discounted_price(object.optString("discounted_price"));
                                productsModel.setSimilar_prod_price(object.optString("price"));
                                productsModel.setSimilar_prod_name(object.optString("name"));
                                productsModelList.add(productsModel);
                                initRecyclerViewSimilarProducts();
                            }

                        }


                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("errorCatch", String.valueOf(e));
                    snackBar("Something Went Wrong Come Back Later", true);
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errorCatch", String.valueOf(error));
                snackBar("Something Went Wrong Come Back Later", true);
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (isLogged) {
                    params.put("uid", preferences.getString(SharedPref.USER_ID, null));
                } else {
                    params.put("udid", getSharedPreferences("udid", MODE_PRIVATE).getString(SharedPref.PHONE_ID, null));
                }
                params.put("id", intent.getStringExtra("prodId"));
                Log.d("paramLog", String.valueOf(params));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProductDetails.this);
        queue.add(stringRequest);
    }

    public void addToWishList() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_ADD_WISH_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseWish", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        Toast.makeText(ProductDetails.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
//                        deleteCartItem(p_id, variant);
                    } else {
                        Toast.makeText(ProductDetails.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = ProductDetails.this.getSharedPreferences("data", Context.MODE_PRIVATE);
                params.put("user_id", sharedPreferences.getString(SharedPref.USER_ID, null));
                params.put("product_id", intent.getStringExtra("prodId"));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ProductDetails.this);
        queue.add(stringRequest);
    }


    public void snackBar(String text, Boolean error) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = View.inflate(ProductDetails.this, R.layout.my_snackbar, null);
        // Configure the view

        TextView textViewTop = (TextView) snackView.findViewById(R.id.textViewTop);
        textViewTop.setText(text);
        if (!error) {
            textViewTop.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        }
        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        snackbar.show();
    }


    public void initRecyclerViewBoughtTogether() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false);
        BoughtTogetherAdapter mAdapter = new BoughtTogetherAdapter(ProductDetails.this);
        boughtTogetherView.setLayoutManager(layoutManager);
        boughtTogetherView.setAdapter(mAdapter);
    }

    public void initRecyclerViewSimilarProducts() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false);
        SimilarProductsAdapter mAdapter = new SimilarProductsAdapter(ProductDetails.this, productsModelList);
        similarProductsView.setLayoutManager(layoutManager);
        similarProductsView.setAdapter(mAdapter);
    }

    public void initRecyclerViewRatings() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.VERTICAL, false);
        ReviewsRatingsAdapter mAdapter = new ReviewsRatingsAdapter(ProductDetails.this, productsModelList);
        reviewsRatingsView.setLayoutManager(layoutManager);
        reviewsRatingsView.setAdapter(mAdapter);
    }

    public void initRecyclerViewSelectColor() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false);
        SelectColorAdapter mAdapter = new SelectColorAdapter(ProductDetails.this, variationModelList);
        colorRecyclerView.setLayoutManager(layoutManager);
        colorRecyclerView.setAdapter(mAdapter);
    }

    public void initRecyclerViewSelectSize() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false);
        SelectSizeAdapter mAdapter = new SelectSizeAdapter(ProductDetails.this, variationModelList);
        selectSizeRecyclerView.setLayoutManager(layoutManager);
        selectSizeRecyclerView.setAdapter(mAdapter);
    }

    public void displaySize(String sizeValue, String sizeVPrice) {
        variantSize = sizeValue;
        tv_discountedPrice.setText(String.valueOf(priceProd + Integer.parseInt(sizeVPrice)));
        Log.d("vatiantValue", variantSize);
//        tv_discountedPrice
    }

    public void displayColor(String colorValue, String colorVPrice, String colorName) {
        variantColor = colorValue + ":" + colorName;
        tv_discountedPrice.setText(String.valueOf(priceProd + Integer.parseInt(colorVPrice)));
        Log.d("vatiantValue", variantColor);
//        tv_discountedPrice
    }
}
