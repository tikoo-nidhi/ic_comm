package com.app.iccomm.Data;
/*
    CREATED BY NIDHI 07/05/2019
*/

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.iccomm.Model.AddressModel;
import com.app.iccomm.Model.CartModel;
import com.app.iccomm.Model.HomePageModel;
import com.app.iccomm.Model.OrderModel;
import com.app.iccomm.Model.ProfileModel;
import com.app.iccomm.Model.ReviewModel;
import com.app.iccomm.Model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    //ALL STATIC VARIABLES
    //DATABASE NAME
    private static final String DATABASE_NAME = "IccommDatabase";

    //DATABASE VERSION
    private static final int DATABASE_VERSION = 2;

    //TABLE NAME
    private static final String ADDRESS_TABLE = "address_table";
    private static final String WISHLIST_TABLE = "wishlist_table";
    private static final String CART_TABLE = "cart_table";
    //    private static final String PRODUCTS_TABLE = "products_table";
    private static final String ORDER_TABLE = "order_table";
    private static final String PROFILE_DATA_TABLE = "profile_data_table";
    private static final String REVIEW_TABLE = "review_table";
    private static final String SORT_TABLE = "sort_table";

    //COLUMN NAME SORT TABLE

    private static final String SORT_ID = "sort_id";
    private static final String SORT_NAME = "sort_name";

    //COLUMN NAME ADDRESS TABLE
    private static final String KEY_PK = "pk";
    private static final String ADDRESS_NAME = "address_name";
    private static final String ADDRESS_STD_CODE = "address_std_code";
    private static final String ADDRESS_MOBILE = "address_mobile";
    private static final String ADDRESS_PIN_CODE = "address_pin_code";
    private static final String ADDRESS_BUILDING = "address_building";
    private static final String ADDRESS_TOWN = "address_town";
    private static final String ADDRESS_DISTRICT = "address_district";
    private static final String ADDRESS_STATE = "address_state";
    private static final String ADDRESS_COUNTRY = "address_country";
    private static final String ADDRESS_TYPE = "address_type";

    //COLUMN NAME WISHLIST TABLE
    private static final String KEY_WISH_PROD_ID = "key_prod_id";
    private static final String WISH_PROD_IMG = "wish_prod_img";
    private static final String WISH_PROD_NAME = "wish_prod_name";
    private static final String WISH_PROD_PRICE = "wish_prod_price";

    //COLUMN NAME CART TABLE
    private static final String KEY_CART_PROD_ID = "key_cart_prod_id";
    private static final String PROD_CART_IMG = "prod_cart_img";
    private static final String PROD_CART_PRICE = "prod_cart_price";
    private static final String PROD_CART_NAME = "prod_cart_name";
    private static final String PROD_CART_SOLD_BY = "prod_cart_sold_by";
    private static final String PROD_CART_QTY = "prod_cart_qty";

//    //COLUMN NAME PRODUCTS TABLE
//    private static final String KEY_PROD_ID = "key_prod_id";
//    private static final String PROD_IMG = "prod_img";
//    private static final String PROD_SOLD_BY = "prod_sold_by";
//    private static final String PROD_NAME = "prod_name";
//    private static final String PROD_PRICE = "prod_price";
//    private static final String PROD_STARS = "prod_stars";
//    private static final String PROD_WISH = "prod_wish";

    //COLUMN NAME ORDER TABLE
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_IMG = "order_img";
    private static final String ORDER_NAME = "order_name";
    private static final String ORDER_STATUS = "order_status";

    //COLUMN NAME PROFILE_DATA_TABLE
    private static final String PROFILE_ID = "profile_id";
    private static final String PROFILE_IMG = "profile_img";
    private static final String PROFILE_NAME = "profile_name";
    private static final String PROFILE_NUMBER = "profile_number";
    private static final String PROFILE_MAIL_ID = "profile_mail_id";
    private static final String PROFILE_PASSWORD = "profile_password";

    //COLUMN NAME REVIEW TABLE
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_HEADING = "review_heading";
    private static final String REVIEW_TEXT = "review_text";
    private static final String REVIEW_NAME = "review_name";
    private static final String REVIEW_DATE = "review_date";
    private static final String REVIEW_STARS = "review_stars";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREATE TABLE SORT TABLE
        String CREATE_SORT_TABLE = "CREATE TABLE " +
                SORT_TABLE + "(" +
                SORT_ID + " TEXT," +
                SORT_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_SORT_TABLE);


        //CREATE ADDRESS TABLE
        String CREATE_ADDRESS_TABLE = "CREATE TABLE " +
                ADDRESS_TABLE + "(" +
                KEY_PK + " INTEGER," +
                ADDRESS_NAME + " TEXT," +
                ADDRESS_STD_CODE + " TEXT," +
                ADDRESS_MOBILE + " TEXT," +
                ADDRESS_PIN_CODE + " TEXT," +
                ADDRESS_BUILDING + " TEXT," +
                ADDRESS_TOWN + " TEXT," +
                ADDRESS_DISTRICT + " TEXT," +
                ADDRESS_STATE + " TEXT," +
                ADDRESS_COUNTRY + " TEXT," +
                ADDRESS_TYPE + " TEXT" +
                ")";
        db.execSQL(CREATE_ADDRESS_TABLE);

        //CREATE WISHLIST TABLE
        String CREATE_WISHLIST_TABLE = "CREATE TABLE " +
                WISHLIST_TABLE + "(" +
                KEY_WISH_PROD_ID + " INTEGER," +
                WISH_PROD_IMG + " TEXT," +
                WISH_PROD_NAME + " TEXT," +
                WISH_PROD_PRICE + " TEXT " +
                ")";
        db.execSQL(CREATE_WISHLIST_TABLE);

        //CREATE CART TABLE
        String CREATE_CART_TABLE = "CREATE TABLE " +
                CART_TABLE + "(" +
                KEY_CART_PROD_ID + " INTEGER," +
                PROD_CART_IMG + " TEXT," +
                PROD_CART_PRICE + " TEXT," +
                PROD_CART_NAME + " TEXT," +
                PROD_CART_SOLD_BY + " TEXT," +
                PROD_CART_QTY + " INTEGER" +
                ")";
        db.execSQL(CREATE_CART_TABLE);

//        //CREATE PRODUCTS TABLE
//        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
//                PRODUCTS_TABLE + "(" +
//                KEY_PROD_ID + " INTEGER," +
//                PROD_IMG + " TEXT," +
//                PROD_SOLD_BY + " TEXT," +
//                PROD_NAME + " TEXT," +
//                PROD_PRICE + " TEXT," +
//                PROD_STARS + " TEXT," +
//                PROD_WISH + " TEXT" +
//                ")";
//        db.execSQL(CREATE_PRODUCTS_TABLE);

        //CREATE  ORDER TABLE
        String CREATE_ORDER_TABLE = "CREATE TABLE " +
                ORDER_TABLE + "(" +
                ORDER_ID + " INTEGER," +
                ORDER_IMG + " TEXT," +
                ORDER_NAME + " TEXT," +
                ORDER_STATUS + " TEXT" +
                ")";
        db.execSQL(CREATE_ORDER_TABLE);

        //CREATE PROFILE DATA TABLE
        String CREATE_PROFILE_DATA_TABLE = "CREATE TABLE " +
                PROFILE_DATA_TABLE + "(" +
                PROFILE_ID + " INTEGER," +
                PROFILE_IMG + " BLOB," +
                PROFILE_NAME + " TEXT," +
                PROFILE_NUMBER + " TEXT," +
                PROFILE_MAIL_ID + " TEXT," +
                PROFILE_PASSWORD + " TEXT" +
                ")";
        db.execSQL(CREATE_PROFILE_DATA_TABLE);

        //CREATE PROFILE REVIEW TABLE
        String CREATE_REVIEW_TABLE = "CREATE TABLE " +
                REVIEW_TABLE + "(" +
                REVIEW_ID + " INTEGER," +
                REVIEW_HEADING + " TEXT," +
                REVIEW_TEXT + " TEXT," +
                REVIEW_NAME + " TEXT," +
                REVIEW_DATE + " TEXT," +
                REVIEW_STARS + " TEXT" +
                ")";
        db.execSQL(CREATE_REVIEW_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
//            db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PROFILE_DATA_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SORT_TABLE);
            onCreate(db);
        }
    }

    //DROP ALL TABLES
    public void dropTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
//            db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_DATA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SORT_TABLE);

        onCreate(db);
    }

    //ADDRESS TABLE START
    //INSERT INTO ADDRESS TABLE
    public long addAddress(AddressModel addressModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PK, addressModel.getKey());
        values.put(ADDRESS_NAME, addressModel.getName());
        values.put(ADDRESS_STD_CODE, addressModel.getStd_code());
        values.put(ADDRESS_MOBILE, addressModel.getMobile());
        values.put(ADDRESS_PIN_CODE, addressModel.getPin_code());
        values.put(ADDRESS_BUILDING, addressModel.getBuilding());
        values.put(ADDRESS_TOWN, addressModel.getTown());
        values.put(ADDRESS_DISTRICT, addressModel.getDistrict());
        values.put(ADDRESS_STATE, addressModel.getState());
        values.put(ADDRESS_COUNTRY, addressModel.getCountry());
        values.put(ADDRESS_TYPE, addressModel.getAddress_type());

        long id = db.insert(ADDRESS_TABLE, null, values);

        db.close();
        return id;
    }

    //GET VALUES FROM ADDRESS TABLE
    public List<AddressModel> getAddressTable() {
        List<AddressModel> addressModelsList = new ArrayList<>();
        String query = "SELECT * FROM " + ADDRESS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    AddressModel addressModel = new AddressModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
                    addressModelsList.add(addressModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return addressModelsList;
    }


    //DELETE ADDRESS TABLE
    public void deleteFromAddressTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM  " + ADDRESS_TABLE);
    }

    //ADDRESS TABLE ENDS

    //WISH LIST TABLE STARTS
    //INSERT INTO WISH LIST TABLE
//    public long addToWishList(WishListModel wishListModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_WISH_PROD_ID, wishListModel.getProd_id());
//        values.put(WISH_PROD_IMG, wishListModel.getProd_img());
//        values.put(WISH_PROD_NAME, wishListModel.getProdName());
//        values.put(WISH_PROD_PRICE, wishListModel.getProdPrice());
//
//        long id = db.insert(WISHLIST_TABLE, null, values);
//
//        db.close();
//        return id;
//    }

    //GET VALUES FROM WISH LIST TABLE
//    public List<WishListModel> getWishListTable() {
//        List<WishListModel> wishListModelsList = new ArrayList<>();
//        String query = "SELECT * FROM " + WISHLIST_TABLE;
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    WishListModel wishListModel = new WishListModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//                    wishListModelsList.add(wishListModel);
//                } while (cursor.moveToNext());
//            }
//        }
//        db.close();
//        return wishListModelsList;
//    }

    //DELETE FROM ENTRY WISH LIST TABLE
    public void deleteWisListEntry(int prodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + WISHLIST_TABLE + " WHERE " + KEY_WISH_PROD_ID + " = " + "\"" + prodId + "\"");
        db.close();
    }

    //DELETE WISH LIST TABLE
    public void deleteFromWishListTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM  " + WISHLIST_TABLE);
    }

    //WISH LIST TABLE ENDS


    //CART TABLE STARTS
    //INSERT INTO CART TABLE
    public long addToCart(CartModel cartModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CART_PROD_ID, cartModel.getProd_cart_id());
        values.put(PROD_CART_IMG, cartModel.getProd_cart_img());
        values.put(PROD_CART_PRICE, cartModel.getProd_cart_price());
        values.put(PROD_CART_NAME, cartModel.getProd_cart_name());
        values.put(PROD_CART_SOLD_BY, cartModel.getProd_cart_sold_by());
        values.put(PROD_CART_QTY, cartModel.getProd_qty());

        long id = db.insert(CART_TABLE, null, values);

        db.close();
        return id;
    }

    //GET VALUES FROM CART LIST TABLE
    public List<CartModel> getCartTable() {
        List<CartModel> cartModelsList = new ArrayList<>();
        String query = "SELECT * FROM " + CART_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    CartModel cartModel = new CartModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    cartModelsList.add(cartModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return cartModelsList;
    }

    //UPDATE CART VALUES
    public void updateCartTable(int prodId, int qty) {
        String selectQuery = "SELECT " + PROD_CART_QTY + " FROM " + CART_TABLE + " WHERE " + KEY_CART_PROD_ID + " = " + "\"" + prodId + "\"";
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if ((cursor != null)) {
            if (cursor.moveToFirst()) {
                do {
                    values.put(PROD_CART_QTY, qty);
                    db.update(CART_TABLE, values, KEY_CART_PROD_ID + " = " + "\"" + prodId + "\"", null);

                } while (cursor.moveToNext());

            }
        }
        db.close();
    }

    public void deleteValuesFromCart(int prod_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + CART_TABLE + " WHERE " + KEY_CART_PROD_ID + " = " + "\"" + prod_id + "\"");
        db.close();
    }


    //CART TABLE ENDS

//    //PRODUCTS TABLE START
//    //INSERT INTO ADDRESS TABLE
//    public long addProducts(ProductsModel productsModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_PROD_ID, productsModel.getProdId());
//        values.put(PROD_IMG, productsModel.getProdImg());
//        values.put(PROD_SOLD_BY, productsModel.getProdSoldBy());
//        values.put(PROD_NAME, productsModel.getProdName());
//        values.put(PROD_PRICE, productsModel.getProdPrice());
//        values.put(PROD_STARS, productsModel.getProdStars());
//        values.put(PROD_WISH, productsModel.getProdWish());
//
//        long id = db.insert(ADDRESS_TABLE, null, values);
//
//        db.close();
//        return id;
//    }
//
//    //GET VALUES FROM PRODUCTS TABLE
//    public List<ProductsModel> getProductsTable() {
//        List<ProductsModel> productsModelsList = new ArrayList<>();
//        String query = "SELECT * FROM " + PRODUCTS_TABLE;
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    ProductsModel productsModel = new ProductsModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
//                    productsModelsList.add(productsModel);
//                } while (cursor.moveToNext());
//            }
//        }
//        db.close();
//        return productsModelsList;
//    }
//
//    //PRODUCTS TABLE ENDS

    //ORDER TABLE STARTS
    // INSERT INTO ORDER TABLE
    public long addOrder(OrderModel orderModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_ID, orderModel.getOrderId());
        values.put(ORDER_IMG, orderModel.getOrderImg());
        values.put(ORDER_NAME, orderModel.getOrderName());
        values.put(ORDER_STATUS, orderModel.getOrderStatus());

        long id = db.insert(ORDER_TABLE, null, values);

        db.close();
        return id;
    }

    //GET VALUES FROM ORDER TABLE
    public List<OrderModel> getOrders() {
        List<OrderModel> orderModelsList = new ArrayList<>();
        String query = "SELECT * FROM " + ORDER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    OrderModel orderModel = new OrderModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                    orderModelsList.add(orderModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        ;
        return orderModelsList;
    }
    //ORDER TABLE ENDS


    //PROFILE DATA TABLE STARTS
    // INSERT INTO PROFILE DATA TABLE
    public long addProfile(ProfileModel profileModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, profileModel.getProfileId());
        values.put(PROFILE_IMG, profileModel.getProfileImg());
        values.put(PROFILE_NAME, profileModel.getProfileName());
        values.put(PROFILE_NUMBER, profileModel.getProfileNumber());
        values.put(PROFILE_PASSWORD, profileModel.getProfilePassword());

        long id = db.insert(ORDER_TABLE, null, values);

        db.close();
        return id;
    }

    //GET VALUES FROM PROFILE DATA TABLE
    public List<ProfileModel> getProfile() {
        List<ProfileModel> profileModelsList = new ArrayList<>();
        String query = "SELECT * FROM " + ORDER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ProfileModel profileModel = new ProfileModel(cursor.getInt(0), cursor.getBlob(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getColumnName(5));
                    profileModelsList.add(profileModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return profileModelsList;
    }

    //DELETE VALUES FROM PROFILE DATA TABLE
    public void deleteValueProfileData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + PROFILE_DATA_TABLE);
    }
    //PROFILE DATA TABLE ENDS

    //REVIEW TABLE STARS
    //INSERT INTO REVIEW TABLE
    public long addReview(ReviewModel reviewModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REVIEW_ID, reviewModel.getReviewId());
        values.put(REVIEW_HEADING, reviewModel.getReviewHeading());
        values.put(REVIEW_TEXT, reviewModel.getReviewText());
        values.put(REVIEW_NAME, reviewModel.getReviewName());
        values.put(REVIEW_DATE, reviewModel.getReviewDate());
        values.put(REVIEW_STARS, reviewModel.getReviewStars());

        long id = db.insert(REVIEW_TABLE, null, values);
        db.close();
        return id;
    }

    public List<ReviewModel> getReviews() {
        List<ReviewModel> reviewModelList = new ArrayList<>();
        String query = "SELECT * FROM " + REVIEW_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ReviewModel reviewModel = new ReviewModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    reviewModelList.add(reviewModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return reviewModelList;

    }

    //REVIEW TABLE ENDS

    //ADD INTO SORT TABLE

    public long addSort(HomePageModel homePageModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SORT_ID, homePageModel.getSort_id());
        values.put(SORT_NAME, homePageModel.getSort_name());

        long id = db.insert(SORT_TABLE, null, values);

        db.close();
        return id;
    }

    //GET DATA FROM SORT TABLE
    public List<HomePageModel> getSortingData() {
        List<HomePageModel> homePageModelList = new ArrayList<>();
        String query = "SELECT * FROM " + SORT_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HomePageModel homePageModel = new HomePageModel(cursor.getString(0), cursor.getString(1));
                    homePageModelList.add(homePageModel);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return homePageModelList;

    }

    //DELETE SORTING TABLE DATA
    public void deleteSortingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + SORT_TABLE);
    }

}
