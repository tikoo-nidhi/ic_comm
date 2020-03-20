package com.app.iccomm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iccomm.Adapters.ExpandableListAdapter;
import com.app.iccomm.Data.DataBaseHandler;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Fragments.BeautyAndCareFragment;
import com.app.iccomm.Fragments.CategoryNewDesignFragment;
import com.app.iccomm.Fragments.ElectronicsFragment;
import com.app.iccomm.Fragments.GroceryFragment;
import com.app.iccomm.Fragments.HomeFragment;
import com.app.iccomm.Fragments.HomeLivingFragment;
import com.app.iccomm.Fragments.KidsFragment;
import com.app.iccomm.Fragments.MenFashionFragment;
import com.app.iccomm.Fragments.WomenFashionFragment;
import com.app.iccomm.Model.CategoryModel;
import com.app.iccomm.Model.MenuModel;
import com.app.iccomm.Model.WishListModel;
import com.app.iccomm.NoNetwork;
import com.app.iccomm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.app.iccomm.Data.Constants.BEAUTY_CARE;
import static com.app.iccomm.Data.Constants.ELECTRONICS;
import static com.app.iccomm.Data.Constants.GROCERY;
import static com.app.iccomm.Data.Constants.HOME;
import static com.app.iccomm.Data.Constants.HOME_LIVING;
import static com.app.iccomm.Data.Constants.KIDS;
import static com.app.iccomm.Data.Constants.MEN_FASHION;
import static com.app.iccomm.Data.Constants.WOMEN_FASHION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int i;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList;
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ImageView left_arrow_appBar, imgView_shoppingCart, imgView_dot;
    WishListModel wishListModel = new WishListModel();
    List<WishListModel> mList = new ArrayList<>();
    DataBaseHandler db = new DataBaseHandler(MainActivity.this);
    TextView title, userName;
    SharedPreferences preferences;
    Boolean isLogged;
    List<CategoryModel> models1;
    List<CategoryModel> modelsSub;
    List<CategoryModel> modelsChild;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        expandableListView = findViewById(R.id.expandableListView);
        title = findViewById(R.id.title);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        preferences = getSharedPreferences("data", MODE_PRIVATE);
        isLogged = preferences.getBoolean(SharedPref.IS_LOGGED, false);

//        NoNetwork noNetwork = new NoNetwork();
//        noNetwork.noNet(MainActivity.this);


        left_arrow_appBar.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        imgView_shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
//        prepareMenuData();
//        populateExpandableList();
        ((MainActivity.this)).getSupportActionBar().setTitle("");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        userName = header.findViewById(R.id.userName);
        userName.setText(preferences.getString(SharedPref.USERNAME, null));

        switchFragment(HOME);
    }

    private void switchFragment(int frag) {
        Fragment fragment = null;

        switch (frag) {
            case HOME:
                fragment = new HomeFragment();
                break;
            case MEN_FASHION:
                i = 2;
                fragment = new MenFashionFragment();
                break;
            case WOMEN_FASHION:
                i = 2;
                fragment = new WomenFashionFragment();
                break;
            case BEAUTY_CARE:
                i = 2;
                fragment = new BeautyAndCareFragment();
                break;
            case KIDS:
                i = 2;
                fragment = new KidsFragment();
                break;
            case ELECTRONICS:
                i = 2;
                fragment = new ElectronicsFragment();
                break;
            case HOME_LIVING:
                i = 2;
                fragment = new HomeLivingFragment();
                break;
            case GROCERY:
                i = 2;
                fragment = new GroceryFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frag_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (i == 1) {
            super.onBackPressed();
        } else if (i == 2) {
            i = 1;
            switchFragment(HOME);
        } else {
            super.onBackPressed();
        }
        Log.d("value of i", String.valueOf(i));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the HomeFragment/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            i = 1;
            switchFragment(HOME);

        }
//        else if (id == R.id.nav_slideshow) {
//            i=1;
//        } else if (id == R.id.nav_tools) {
//            i=1;
//        } else if (id == R.id.nav_share) {
//            i=1;
//        } else if (id == R.id.nav_send) {
//            i=1;
//        }
        Log.d("value of i drawer", String.valueOf(i));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getList(List<CategoryModel> models) {
        Log.d("models", String.valueOf(models.size()));
        models1 = new ArrayList<>();
        for (int j = 0; j < models.size(); j++) {
            Log.d("sub_cat_main", String.valueOf(j));

            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCat_id(models.get(j).getCat_id());
            categoryModel.setCat_name(models.get(j).getCat_name());
            Log.d("sub_cat_main", String.valueOf(models.size()));
            Log.d("sub_cat_main", String.valueOf(models.get(j).getCat_name()));

            modelsSub = new ArrayList<>();

            for (int k = 0; k < models.get(j).getData().size(); k++) {
                CategoryModel categoryModel1 = new CategoryModel();
                categoryModel1.setSub_cat_id(models.get(j).getData().get(k).getSub_cat_id());
                categoryModel1.setSub_cat_name(models.get(j).getData().get(k).getSub_cat_name());
                Log.d("sub_cat_main", String.valueOf(models.get(j).getData().size()));
                Log.d("sub_cat_main", String.valueOf(models.get(j).getData().get(k).getSub_cat_name()));

                modelsChild = new ArrayList<>();

                for (int l = 0; l < models.get(j).getData().get(k).getDataChild().size(); l++) {
                    CategoryModel categoryModel2 = new CategoryModel();
                    categoryModel2.setChild_id(models.get(j).getData().get(k).getDataChild().get(l).getChild_id());
                    categoryModel2.setChild_name(models.get(j).getData().get(k).getDataChild().get(l).getChild_name());
                    categoryModel2.setChild_img(models.get(j).getData().get(k).getDataChild().get(l).getChild_img());

                    Log.d("sub_cat_main", String.valueOf(models.get(j).getData().get(k).getDataChild().size()));
                    Log.d("sub_cat_main", String.valueOf(models.get(j).getData().get(k).getDataChild().get(l).getChild_name()));
                    Log.d("childImg2", String.valueOf(models.get(j).getData().get(k).getDataChild().get(l).getChild_img()));

                    modelsChild.add(categoryModel2);
                    categoryModel1.setDataChild(modelsChild);
                }
                modelsSub.add(categoryModel1);
                categoryModel.setData(modelsSub);
            }
            models1.add(categoryModel);
        }

        prepareMenuData();
        populateExpandableList();
    }

    public List<CategoryModel> getModelList() {
        return models1;
    }


    public void prepareMenuData() {
        headerList = new ArrayList<>();

        MenuModel menuModel = new MenuModel("Home", true, false, getResources().getDrawable(R.drawable.ic_home)); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Categories", true, true, getResources().getDrawable(R.drawable.ic_category));
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        //Menu of Categories start

        for (int j = 0; j < models1.size(); j++) {
            MenuModel childModel = new MenuModel(j, models1.get(j).getCat_id(), models1.get(j).getCat_name(), false, false, getResources().getDrawable(R.drawable.ic_dot));
            childModelsList.add(childModel);
        }
//        MenuModel childModel = new MenuModel("Men's Fashion", false, false, getResources().getDrawable(R.drawable.ic_men_fashion));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Women's Fashion", false, false, getResources().getDrawable(R.drawable.ic_women_fashion));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Beauty & Personal Care", false, false, getResources().getDrawable(R.drawable.ic_beauty));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Kids", false, false, getResources().getDrawable(R.drawable.ic_kids));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Electronics", false, false, getResources().getDrawable(R.drawable.ic_electronics));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Home & Living", false, false, getResources().getDrawable(R.drawable.ic_house));
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Grocery", false, false, getResources().getDrawable(R.drawable.ic_food));
//        childModelsList.add(childModel);

        //Menu of Categories end
        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Account", true, false, getResources().getDrawable(R.drawable.ic_account));
        headerList.add(menuModel);

        menuModel = new MenuModel("Wishlist", true, false, getResources().getDrawable(R.drawable.ic_wishlist_hollow));
        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Cart", true, false, getResources().getDrawable(R.drawable.ic_cart));
//        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Orders", true, false, getResources().getDrawable(R.drawable.ic_order));
//        headerList.add(menuModel);

//        menuModel = new MenuModel("Coupons", true, false, getResources().getDrawable(R.drawable.ic_coupon));
//        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Gift Cards", true, false, getResources().getDrawable(R.drawable.ic_gift_card));
//        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Contact Us", true, false, getResources().getDrawable(R.drawable.ic_contact_us));
//        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Rate Us", true, false, getResources().getDrawable(R.drawable.ic_rate_us));
//        headerList.add(menuModel);
//
//        menuModel = new MenuModel("Share", true, false, getResources().getDrawable(R.drawable.ic_share));
//        headerList.add(menuModel);
    }

    public void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        switch (headerList.get(groupPosition).menuName) {
                            case "Home":
                                switchFragment(HOME);
                                break;
                            case "Wishlist":
                                startActivity(new Intent(MainActivity.this, WishListActivity.class));
                                break;
                            case "Account":
                                if (preferences.getBoolean(SharedPref.IS_LOGGED, false)) {
                                    startActivity(new Intent(MainActivity.this, AccountDetailsActivity.class));
                                } else {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(SharedPref.INTENT_VALUE, "Account");
                                    editor.commit();
                                    Log.d("intentValMA", preferences.getString(SharedPref.INTENT_VALUE, null));
                                    startActivity(new Intent(MainActivity.this, LoginSignUpPageActivity.class));
                                }
                                break;
                            case "Orders":
                                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                                break;
                            case "Cart":
                                startActivity(new Intent(MainActivity.this, CartActivity.class));
                                break;
                        }
                        onBackPressed();
                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    Log.d("positionChild", String.valueOf(model.position));
//                    if (model.menuName.equals("Electronics")) {
//                        switchFragment(ELECTRONICS);
//                    } else if (model.menuName.equals("Mens")) {
                    i = 2;
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", model.position);
                    bundle.putString("titleTag", model.menuName);
                    Fragment fragment1 = new MenFashionFragment();
                    fragment1.setArguments(bundle);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.frag_container, fragment1);
                    transaction.commit();

//                    } else if (model.menuName.equals("Womens")) {
//                        switchFragment(WOMEN_FASHION);
//                    } else if (model.menuName.contains("Beauty")) {
//                        switchFragment(BEAUTY_CARE);
//                    } else if (model.menuName.equals("Kids")) {
//                        switchFragment(KIDS);
//                    } else if (model.menuName.equals("Home & Living")) {
//                        switchFragment(HOME_LIVING);
//                    } else if (model.menuName.equals("Grocery")) {
//                        switchFragment(GROCERY);
//                    }
                    onBackPressed();
                }
                return false;
            }
        });
    }


}
