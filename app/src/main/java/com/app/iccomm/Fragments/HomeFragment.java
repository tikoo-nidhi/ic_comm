package com.app.iccomm.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Activity.LoginActivity;
import com.app.iccomm.Activity.MainActivity;
import com.app.iccomm.Activity.ProductsActivity;
import com.app.iccomm.Activity.SearchActivity;
import com.app.iccomm.Activity.SignUpActivity;
import com.app.iccomm.Adapters.HomeAdapter;
import com.app.iccomm.Adapters.HomeBannerHorizontal;
import com.app.iccomm.Adapters.HomeBrandAdapter;
import com.app.iccomm.Adapters.HomeLayoutsAdapter;
import com.app.iccomm.Adapters.HomeOffersForYouAdapter;
import com.app.iccomm.Adapters.HomeProductsHorizontalAdapter;
import com.app.iccomm.Adapters.HomeProductsVerticalAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.NoNetwork;
import com.app.iccomm.R;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    SliderLayout homeImgSlider;
    ArrayList<String> img = new ArrayList<>();
    RecyclerView homeRecyclerCategories, rv_homeBestOffers, rv_homeHotDeals, rv_homeNewProducts, rv_homeOffersForYou, rv_homeFeaturedProducts, rv_homeAccessories, rv_homeBrands, rv_recentlyViewed;
    LinearLayout ll_categories, ll_bestOffers, ll_hotDeals, ll_newProducts, ll_offers_for_you, ll_featured_products, ll_accessories, ll_brands, ll_recentlyViewed;
    FrameLayout fl_lottie;
    String currency;
    TextView tv_search, tv_hotDeals_viewAll, tv_newProd_viewAll, tv_featuredProd_viewAll;
    DataBaseHandler db;
    NoNetwork noNetwork = new NoNetwork();
    Intent mIntent;


    List<HomePageModel> mList;
    List<CategoryModel> listCategories = new ArrayList<>();
    List<HomePageModel> listBestOffers = new ArrayList<>();
    List<HomePageModel> listHotDeals = new ArrayList<>();
    List<HomePageModel> listNewProducts = new ArrayList<>();
    List<HomePageModel> listOffersForYou = new ArrayList<>();
    List<HomePageModel> listFeaturedProducts = new ArrayList<>();
    List<HomePageModel> listAccessories = new ArrayList<>();
    List<HomePageModel> listBrands = new ArrayList<>();
    List<HomePageModel> listRecentlyViewed = new ArrayList<>();
    List<CategoryModel> listSubCat;
    List<CategoryModel> listChild;
//    List<HomePageModel> sortList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        homeImgSlider = view.findViewById(R.id.homeImgSlider);
        ll_categories = view.findViewById(R.id.ll_categories);
        homeRecyclerCategories = view.findViewById(R.id.homeRecyclerCategories);
        ll_bestOffers = view.findViewById(R.id.ll_bestOffers);
        rv_homeBestOffers = view.findViewById(R.id.rv_homeBestOffers);
        ll_hotDeals = view.findViewById(R.id.ll_hotDeals);
        rv_homeHotDeals = view.findViewById(R.id.rv_homeHotDeals);
        ll_newProducts = view.findViewById(R.id.ll_newProducts);
        rv_homeNewProducts = view.findViewById(R.id.rv_homeNewProducts);
        ll_offers_for_you = view.findViewById(R.id.ll_offers_for_you);
        rv_homeOffersForYou = view.findViewById(R.id.rv_homeOffersForYou);
        ll_featured_products = view.findViewById(R.id.ll_featured_products);
        rv_homeFeaturedProducts = view.findViewById(R.id.rv_homeFeaturedProducts);
        ll_accessories = view.findViewById(R.id.ll_accessories);
        rv_homeAccessories = view.findViewById(R.id.rv_homeAccessories);
        ll_brands = view.findViewById(R.id.ll_brands);
        rv_homeBrands = view.findViewById(R.id.rv_homeBrands);
        ll_recentlyViewed = view.findViewById(R.id.ll_recentlyViewed);
        rv_recentlyViewed = view.findViewById(R.id.rv_recentlyViewed);
        fl_lottie = view.findViewById(R.id.fl_lottie);
        tv_search = view.findViewById(R.id.tv_search);
        tv_hotDeals_viewAll = view.findViewById(R.id.tv_hotDeals_viewAll);
        tv_newProd_viewAll = view.findViewById(R.id.tv_newProd_viewAll);
        tv_featuredProd_viewAll = view.findViewById(R.id.tv_featuredProd_viewAll);

        mIntent = new Intent(getContext(), ProductsActivity.class);

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
                ((MainActivity) getActivity()).overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        homeImgSlider.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        homeImgSlider.setScrollTimeInSec(4);
//        noNetwork.noNet(getActivity());
//        checkNetworkConnection();


        tv_hotDeals_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("featureListing", "hot_deals");
                startActivity(mIntent);
            }
        });

        tv_newProd_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("featureListing", "newproduct");
                startActivity(mIntent);
            }
        });

        tv_featuredProd_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("featureListing", "featured");
                startActivity(mIntent);
            }
        });
        getHomePage();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new DataBaseHandler(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
//        Toast.makeText(getActivity(), "oopsss!!!! "+noNetwork.noNet(getActivity()), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        homeImgSlider = view.findViewById(R.id.homeImgSlider);
//        setSlider();
    }

    public void setSlider() {

        for (int a = 0; a < mList.size(); a++) {
            SliderView sliderView = new SliderView(getActivity());
            sliderView.setImageUrl(mList.get(a).getBanner_img());
            final int finalA = a;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Intent mIntent = new Intent(getContext(), ProductsActivity.class);
                    mIntent.putExtra("catId", mList.get(finalA).getBanner_id());
                    startActivity(mIntent);
                }
            });
            homeImgSlider.addSliderView(sliderView);
        }
    }

    public void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeAdapter mAdapter = new HomeAdapter(getContext(), listCategories);
        homeRecyclerCategories.setLayoutManager(mLayoutManager);
        homeRecyclerCategories.setAdapter(mAdapter);
    }

    public void initRecyclerViewBestOffers() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeBannerHorizontal mAdapter = new HomeBannerHorizontal(getContext(), listBestOffers);
        rv_homeBestOffers.setLayoutManager(mLayoutManager);
        rv_homeBestOffers.setAdapter(mAdapter);
    }

    public void initRecyclerViewHotDeals() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        HomeProductsVerticalAdapter mAdapter = new HomeProductsVerticalAdapter(getContext(), listHotDeals);
        rv_homeHotDeals.setLayoutManager(mLayoutManager);
        rv_homeHotDeals.setAdapter(mAdapter);
    }

    public void initRecyclerViewNewProducts() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeProductsHorizontalAdapter mAdapter = new HomeProductsHorizontalAdapter(getContext(), listNewProducts);
        rv_homeNewProducts.setLayoutManager(mLayoutManager);
        rv_homeNewProducts.setAdapter(mAdapter);
    }

    public void initRecyclerViewOffersForYou() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        HomeOffersForYouAdapter mAdapter = new HomeOffersForYouAdapter(getContext(), listOffersForYou);
        rv_homeOffersForYou.setLayoutManager(mLayoutManager);
        rv_homeOffersForYou.setAdapter(mAdapter);
    }

    public void initRecyclerViewFeaturedProducts() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        HomeProductsVerticalAdapter mAdapter = new HomeProductsVerticalAdapter(getContext(), listFeaturedProducts);
        rv_homeFeaturedProducts.setLayoutManager(mLayoutManager);
        rv_homeFeaturedProducts.setAdapter(mAdapter);
    }

    public void initRecyclerViewAccessories() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeBannerHorizontal mAdapter = new HomeBannerHorizontal(getContext(), listAccessories);
        rv_homeAccessories.setLayoutManager(mLayoutManager);
        rv_homeAccessories.setAdapter(mAdapter);
    }

    public void initRecyclerViewBrands() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeBrandAdapter mAdapter = new HomeBrandAdapter(getContext(), listBrands);
        rv_homeBrands.setLayoutManager(mLayoutManager);
        rv_homeBrands.setAdapter(mAdapter);
    }

    public void initRecyclerViewRecentlyViewed() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HomeProductsHorizontalAdapter mAdapter = new HomeProductsHorizontalAdapter(getContext(), listRecentlyViewed);
        rv_recentlyViewed.setLayoutManager(mLayoutManager);
        rv_recentlyViewed.setAdapter(mAdapter);
    }


    public void getHomePage() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_HOME_PAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("homeResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("true")) {
                        currency = jsonObject.optString("currency");
                        SharedPreferences preferences = getContext().getSharedPreferences("cur", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SharedPref.CURRENCY, currency);
                        editor.apply();

                        //BANNER DATA
                        JSONObject banner = jsonObject.getJSONObject("banner");
                        JSONArray banner_data = banner.getJSONArray("data");

                        if (banner_data.length() == 0) {
                            homeImgSlider.setVisibility(View.GONE);
                        }
                        mList = new ArrayList<>();
                        for (int i = 0; i < banner_data.length(); i++) {
                            HomePageModel homePageModel = new HomePageModel();
                            JSONObject object = banner_data.getJSONObject(i);
                            homePageModel.setBanner_id(object.optString("banner_id"));
                            homePageModel.setBanner_img(object.optString("banner_image"));
                            mList.add(homePageModel);
                        }
                        setSlider();

                        Log.d("size1", String.valueOf(mList.size()));

                        //CATEGORY DATA
                        JSONObject categories = jsonObject.getJSONObject("categories");
                        JSONArray categories_data = categories.getJSONArray("data");
                        if (categories_data.length() == 0) {
                            ll_categories.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < categories_data.length(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            JSONObject object = categories_data.getJSONObject(i);
                            categoryModel.setCat_id(object.optString("id"));
                            categoryModel.setCat_name(object.optString("name"));
                            categoryModel.setCat_img(object.optString("cat_img"));

                            listSubCat = new ArrayList<>();
                            JSONArray sub_cat_array = object.getJSONArray("children_data");
                            for (int j = 0; j < sub_cat_array.length(); j++) {
                                JSONObject sub_cat = sub_cat_array.getJSONObject(j);
                                CategoryModel categoryModel1 = new CategoryModel();
                                categoryModel1.setSub_cat_id(sub_cat.optString("id"));
                                categoryModel1.setSub_cat_name(sub_cat.optString("name"));
                                categoryModel1.setSub_cat_img(sub_cat.optString("cat_img"));
//                                Log.d("sub_cat_home",categoryModel.getSub_cat_name());

                                listChild = new ArrayList<>();
                                JSONArray child_array = sub_cat.getJSONArray("children_data");
                                for (int k = 0; k < child_array.length(); k++) {
                                    JSONObject child = child_array.getJSONObject(k);
                                    CategoryModel categoryModel2 = new CategoryModel();
                                    categoryModel2.setChild_id(child.optString("id"));
                                    categoryModel2.setChild_name(child.optString("name"));
                                    categoryModel2.setChild_img(child.optString("cat_img"));
                                    Log.d("childImg",child.optString("cat_img"));

                                    listChild.add(categoryModel2);
                                    categoryModel1.setDataChild(listChild);
                                }
                                listSubCat.add(categoryModel1);
                                categoryModel.setData(listSubCat);
                            }
                            listCategories.add(categoryModel);
//                            Log.d("sub_cat_home",listCategories.get(i).getSub_cat_name());

                            initRecyclerView();
                        }
                        Log.d("size2", String.valueOf(listCategories.size()));
                        Log.d("size100", String.valueOf(listSubCat.size()));

                        ((MainActivity) getActivity()).getList(listCategories);

                        //BEST OFFERS DATA
                        JSONObject best_offers = jsonObject.getJSONObject("best_offers");
                        JSONArray best_offers_array = best_offers.getJSONArray("data");
                        if (best_offers_array.length() == 0) {
                            ll_bestOffers.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < best_offers_array.length(); i++) {
                            HomePageModel homePageModel = new HomePageModel();
                            JSONObject object = best_offers_array.getJSONObject(i);
                            homePageModel.setOffers_id(object.optString("offer_id"));
                            homePageModel.setOffers_name(object.optString("offer_name"));
                            homePageModel.setOffers_img(object.optString("offer_image"));
                            homePageModel.setCat_id(object.optString("cat_id"));
                            listBestOffers.add(homePageModel);
                            initRecyclerViewBestOffers();
                        }
                        Log.d("size3", String.valueOf(listBestOffers.size()));

                        //HOT DEALS DATA
                        JSONObject hot_deals = jsonObject.getJSONObject("hot_deals");
                        JSONArray hot_deals_array = hot_deals.getJSONArray("data");
                        if (hot_deals_array.length() == 0) {
                            ll_hotDeals.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < hot_deals_array.length(); i++) {
                            JSONObject object = hot_deals_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setHot_featured_id(object.optString("id"));
                            homePageModel.setHot_featured_name(object.optString("name"));
                            homePageModel.setHot_featured_price(currency.concat(object.optString("price")));
                            if (object.optString("discount_type").equals("Flat")) {
                                homePageModel.setHot_featured_discount(getContext().getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                            } else {
                                homePageModel.setHot_featured_discount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            homePageModel.setHot_featured_discounted_price(currency.concat(object.optString("discounted_price")));
                            homePageModel.setHot_featured_img(object.optString("image"));
                            listHotDeals.add(homePageModel);
                            initRecyclerViewHotDeals();

                        }
                        Log.d("size4", String.valueOf(listHotDeals.size()));

                        //NEW PRODUCTS DATA
                        JSONObject new_prod = jsonObject.getJSONObject("new_prod");
                        JSONArray new_prod_array = new_prod.getJSONArray("data");
                        if (new_prod_array.length() == 0) {
                            ll_newProducts.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < new_prod_array.length(); i++) {
                            JSONObject object = new_prod_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setNewProd_recent_id(object.optString("id"));
                            homePageModel.setNewProd_recent_name(object.optString("name"));
                            homePageModel.setNewProd_recent_price(currency.concat(object.optString("price")));
                            if (object.optString("discount_type").equals("Flat")) {
                                homePageModel.setNewProd_recent_discount((getContext().getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount"))));
                            } else {
                                homePageModel.setNewProd_recent_discount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            homePageModel.setNewProd_recent_discounted_price(currency.concat(object.optString("discounted_price")));
                            homePageModel.setNewProd_recent_img(object.optString("image"));
                            listNewProducts.add(homePageModel);
                            initRecyclerViewNewProducts();
                        }
                        Log.d("size5", String.valueOf(listNewProducts.size()));

                        //OFFERS FOR YOU DATA
                        JSONObject offers_for_you = jsonObject.getJSONObject("offers_for_you");
                        JSONArray offers_for_you_array = offers_for_you.getJSONArray("data");
                        if (offers_for_you_array.length() == 0) {
                            ll_offers_for_you.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < offers_for_you_array.length(); i++) {
                            JSONObject object = offers_for_you_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setOffers_id(object.optString("offer_id"));
                            homePageModel.setOffers_name(object.optString("offer_name"));
                            homePageModel.setOffers_img(object.optString("offer_image"));
                            homePageModel.setCat_id(object.optString("cat_id"));
                            listOffersForYou.add(homePageModel);
                            initRecyclerViewOffersForYou();
                        }
                        Log.d("size6", String.valueOf(listOffersForYou.size()));

                        // FEATURED PRODUCTS
                        JSONObject featured_products = jsonObject.getJSONObject("featured_prod");
                        JSONArray featured_products_array = featured_products.getJSONArray("data");
                        if (featured_products_array.length() == 0) {
                            ll_featured_products.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < featured_products_array.length(); i++) {
                            JSONObject object = featured_products_array.getJSONObject(i);

                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setHot_featured_id(object.optString("id"));
                            homePageModel.setHot_featured_name(object.optString("name"));
                            homePageModel.setHot_featured_price(currency.concat(object.optString("price")));
                            if (object.optString("discount_type").equals("Flat")) {
                                homePageModel.setHot_featured_discount((getContext().getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount"))));
//                                    homePageModel.setHot_featured_discount(object.optString("discount").concat("%"));
                            } else {
                                homePageModel.setHot_featured_discount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            homePageModel.setHot_featured_discounted_price(currency.concat(object.optString("discounted_price")));
                            homePageModel.setHot_featured_img(object.optString("image"));
                            listFeaturedProducts.add(homePageModel);
                            initRecyclerViewFeaturedProducts();
                        }

                        Log.d("size7", String.valueOf(listFeaturedProducts.size()));

                        //ACCESSORIES DATA
                        JSONObject acc_data = jsonObject.getJSONObject("accessories");
                        JSONArray acc_data_array = acc_data.getJSONArray("data");
                        if (acc_data_array.length() == 0) {
                            ll_accessories.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < acc_data_array.length(); i++) {
                            JSONObject object = acc_data_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setOffers_id(object.optString("acc_id"));
                            homePageModel.setOffers_name(object.optString("acc_name"));
                            homePageModel.setOffers_img(object.optString("acc_img"));
                            listAccessories.add(homePageModel);
                            initRecyclerViewAccessories();
                        }
                        Log.d("size8", String.valueOf(listAccessories.size()));

                        //BRANDS DATA
                        JSONObject brands_data = jsonObject.getJSONObject("brands");
                        JSONArray brands_data_array = brands_data.getJSONArray("data");
                        if (brands_data_array.length() == 0) {
                            ll_brands.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < brands_data_array.length(); i++) {
                            JSONObject object = brands_data_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setBrand_id(object.optString("brand_id"));
                            homePageModel.setBrand_name(object.optString("brand_name"));
                            homePageModel.setBrand_img(object.optString("brand_img"));
                            homePageModel.setImgViewAll(null);
                            listBrands.add(homePageModel);
                            initRecyclerViewBrands();
                        }
                        HomePageModel homePageModel1 = new HomePageModel();
                        homePageModel1.setImgViewAll(getResources().getDrawable(R.drawable.right_arrow));
                        homePageModel1.setBrand_id("null");
                        homePageModel1.setBrand_name("View All");
                        homePageModel1.setBrand_img("null");
                        listBrands.add(homePageModel1);
                        initRecyclerViewBrands();
                        Log.d("size9", String.valueOf(listBrands.size()));

                        //RECENTLY VIEWED
                        JSONObject recently_viewed = jsonObject.getJSONObject("recently_viewed");
                        JSONArray recently_viewed_array = recently_viewed.getJSONArray("data");
                        if (recently_viewed_array.length() == 0) {
                            ll_recentlyViewed.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < recently_viewed_array.length(); i++) {
                            JSONObject object = recently_viewed_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setNewProd_recent_id(object.optString("id"));
                            homePageModel.setNewProd_recent_name(object.optString("name"));
                            homePageModel.setNewProd_recent_price(currency.concat(object.optString("price")));
                            if (object.optString("discount_type").equals("Flat")) {
                                homePageModel.setNewProd_recent_discount(getContext().getSharedPreferences("cur", Context.MODE_PRIVATE).getString(SharedPref.CURRENCY, "").concat(object.optString("discount")));
                            } else {
                                homePageModel.setNewProd_recent_discount(object.optString("discount").concat(object.optString("discount_type")));
                            }
                            homePageModel.setNewProd_recent_discounted_price(currency.concat(object.optString("discounted_price")));
                            homePageModel.setNewProd_recent_img(object.optString("image"));
                            listRecentlyViewed.add(homePageModel);
                            initRecyclerViewRecentlyViewed();
                        }
                        Log.d("size10", String.valueOf(listRecentlyViewed.size()));

                        //SORT DATA
                        db.deleteSortingData();
                        JSONObject sort = jsonObject.getJSONObject("sort_filter");
                        JSONArray sort_array = sort.getJSONArray("data");
                        for (int i = 0; i < sort_array.length(); i++) {
                            JSONObject object = sort_array.getJSONObject(i);
                            HomePageModel homePageModel = new HomePageModel();
                            homePageModel.setSort_id(object.optString("id"));
                            homePageModel.setSort_name(object.optString("name"));
                            db.addSort(homePageModel);
                        }

                        fl_lottie.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong " + e, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("erroeResponse", String.valueOf(error));
                if (String.valueOf(error).contains("NoConnectionError")){
                    Toast.makeText(getContext(), "No Internet Connection\nConnect to internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Something went wrong come back later", Toast.LENGTH_SHORT).show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fl_lottie.setVisibility(View.GONE);
                        ((MainActivity) getActivity()).finish();
                    }
                }, 2000);

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }


}
