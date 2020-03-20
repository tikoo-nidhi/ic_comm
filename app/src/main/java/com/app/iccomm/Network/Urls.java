package com.app.iccomm.Network;

import com.android.volley.toolbox.StringRequest;

public class Urls {

    private static final String BASE_URL = "https://showmeproject.com/iccomm/";
//    public static final String POST_SIGN_UP = BASE_URL + "sign-up"; //working
//    public static final String POST_LOG_IN = BASE_URL + "login"; //working

    //USER DATA
    public static final String POST_REGISTER = BASE_URL + "userRegister";
    public static final String POST_LOGIN = BASE_URL + "userLogin";
    public static final String POST_USER_DATA = BASE_URL + "user";
    public static final String POST_WISH_LIST = BASE_URL + "wishlist";

    //HOME PAGE
    public static final String GET_HOME_PAGE = BASE_URL + "homepage";
    public static final String POST_HOME_PAGE_VIEW_ALL = BASE_URL + "featurelisting";
    public static final String POST_HOME_BRAND = BASE_URL + "brand_product";

    //PRODUCTS
    public static final String POST_PRODUCT_LIST = BASE_URL + "products";
    public static final String POST_PRODUCT_DETAIL = BASE_URL + "productDetails";

    //CART
//    public static final String POST_CART = BASE_URL + "getCartItems";
    public static final String POST_ADD_TO_CART = BASE_URL + "addToCart";
    public static final String POST_GET_CART = BASE_URL + "getCartItems";
    public static final String POST_DELETE_CART_ITEM = BASE_URL + "removeCartItem";

    //ADDRESS
//  public static final String POST_GET_ADDRESS = BASE_URL + "getuser-address";
    public static final String POST_GET_ADDRESS = BASE_URL + "getAddress";
    public static final String POST_ADD_ADDRESS = BASE_URL + "addAddress";
    public static final String POST_DELETE_ADDRESS = BASE_URL + "deleteAddress";
    public static final String POST_UPDATE_ADDRESS = BASE_URL + "updateAddress";
    public static final String POST_ADDRESS = BASE_URL + "user_address";

    //WISH LIST
    public static final String POST_ADD_WISH_LIST = BASE_URL + "addWishList";
    public static final String POST_GET_WISH_LIST = BASE_URL + "getWishList";
    public static final String POST_DELETE_WISH = BASE_URL + "delWishList";

    //USER PROFILE
    public static final String POST_UPDATE_PROFILE = BASE_URL + "updateUserDetails";
    public static final String POST_FORGOT_PASSWORD = BASE_URL + "forgotPassword";
    public static final String POST_GET_USER_DETAILS = BASE_URL + "getUserDetails";
    public static final String POST_CHANGE_PASSWORD = BASE_URL + "changePassword";

    //ORDER
    public static final String POST_PLACE_ORDER = BASE_URL + "placeOrder";
    public static final String POST_ORDER_LIST = BASE_URL + "getOrderDetails";
    public static final String POST_ORDER_ITEM_DETAILS = BASE_URL + "getOrderDetail";

    //REVIEW
    public static final String POST_REVIEW = BASE_URL + "add-review";

    //SEARCH
    public static final String POST_SEARCH = BASE_URL + "searchResults";
    public static final String POST_SEARCH_RESULT = BASE_URL + "searchProductFilter";

    //OTP Verification
    public static final String POST_VERIFY_USER = BASE_URL + "verifyUser";
    public static final String POST_RESEND_OTP = BASE_URL + "resendOTP";




}
