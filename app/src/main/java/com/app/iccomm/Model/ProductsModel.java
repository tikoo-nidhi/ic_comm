package com.app.iccomm.Model;

import com.android.volley.toolbox.StringRequest;

public class ProductsModel {

    private String prodId;
    private String prodImg;
    private String prodName;
    private String prodActualPrice;
    private String prodDiscount;
    private String prodDiscountedPrice;
    private String prodStars;
    //    private String prodWish;
    private String variationId;
    private String variationValue;
    private String variationPrice;
    private String variationImg;

    private String review_id;
    private String review_rating;
    private String review_heading;
    private String review_text;
    private String review_date;
    private String user_name;

    private String similar_prod_id;
    private String similar_prod_price;
    private String similar_prod_name;
    private String similar_prod_discount;
    private String similar_prod_discounted_price;
    private String similar_prod_img;

    private String filter_name_id;
    private String filter_name;
    private Boolean isFilterSelected = false;

    private String filterDataId;
    private String filterDataValue;
    private String filterDataColorValue;


    public ProductsModel() {
    }

    public ProductsModel(String prodId, String prodImg, String prodName, String prodActualPrice, String prodDiscount, String prodDiscountedPrice, String prodStars) {
        this.prodId = prodId;
        this.prodImg = prodImg;
        this.prodName = prodName;
        this.prodActualPrice = prodActualPrice;
        this.prodDiscount = prodDiscount;
        this.prodDiscountedPrice = prodDiscountedPrice;
        this.prodStars = prodStars;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

//    public String getProdPrice() {
//        return prodPrice;
//    }
//
//    public void setProdPrice(String prodPrice) {
//        this.prodPrice = prodPrice;
//    }

    public String getProdStars() {
        return prodStars;
    }

    public void setProdStars(String prodStars) {
        this.prodStars = prodStars;
    }

    public String getProdDiscount() {
        return prodDiscount;
    }

    public void setProdDiscount(String prodDiscount) {
        this.prodDiscount = prodDiscount;
    }

    public String getProdDiscountedPrice() {
        return prodDiscountedPrice;
    }

    public void setProdDiscountedPrice(String prodDiscountedPrice) {
        this.prodDiscountedPrice = prodDiscountedPrice;
    }

    public String getProdActualPrice() {
        return prodActualPrice;
    }

    public void setProdActualPrice(String prodActualPrice) {
        this.prodActualPrice = prodActualPrice;
    }

    //VARIATIONS GETTER SETTER

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getVariationValue() {
        return variationValue;
    }

    public void setVariationValue(String variationValue) {
        this.variationValue = variationValue;
    }

    public String getVariationPrice() {
        return variationPrice;
    }

    public void setVariationPrice(String variationPrice) {
        this.variationPrice = variationPrice;
    }

    public String getVariationImg() {
        return variationImg;
    }

    public void setVariationImg(String variationImg) {
        this.variationImg = variationImg;
    }

    //    public String getProdWish() {
//        return prodWish;
//    }
//
//    public void setProdWish(String prodWish) {
//        this.prodWish = prodWish;
//    }

    //REVIEW GETTER SETTER

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(String review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_heading() {
        return review_heading;
    }

    public void setReview_heading(String review_heading) {
        this.review_heading = review_heading;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSimilar_prod_id() {
        return similar_prod_id;
    }

    public void setSimilar_prod_id(String similar_prod_id) {
        this.similar_prod_id = similar_prod_id;
    }

    public String getSimilar_prod_price() {
        return similar_prod_price;
    }

    public void setSimilar_prod_price(String similar_prod_price) {
        this.similar_prod_price = similar_prod_price;
    }

    public String getSimilar_prod_name() {
        return similar_prod_name;
    }

    public void setSimilar_prod_name(String similar_prod_name) {
        this.similar_prod_name = similar_prod_name;
    }

    public String getSimilar_prod_discount() {
        return similar_prod_discount;
    }

    public void setSimilar_prod_discount(String similar_prod_discount) {
        this.similar_prod_discount = similar_prod_discount;
    }

    public String getSimilar_prod_discounted_price() {
        return similar_prod_discounted_price;
    }

    public void setSimilar_prod_discounted_price(String similar_prod_discounted_price) {
        this.similar_prod_discounted_price = similar_prod_discounted_price;
    }

    public String getSimilar_prod_img() {
        return similar_prod_img;
    }

    public void setSimilar_prod_img(String similar_prod_img) {
        this.similar_prod_img = similar_prod_img;
    }

    public String getFilter_name_id() {
        return filter_name_id;
    }

    public void setFilter_name_id(String filter_name_id) {
        this.filter_name_id = filter_name_id;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    public Boolean getFilterSelected() {
        return isFilterSelected;
    }

    public void setFilterSelected(Boolean filterSelected) {
        isFilterSelected = filterSelected;
    }

    public String getFilterDataId() {
        return filterDataId;
    }

    public void setFilterDataId(String filterDataId) {
        this.filterDataId = filterDataId;
    }

    public String getFilterDataValue() {
        return filterDataValue;
    }

    public void setFilterDataValue(String filterDataValue) {
        this.filterDataValue = filterDataValue;
    }

    public String getFilterDataColorValue() {
        return filterDataColorValue;
    }

    public void setFilterDataColorValue(String filterDataColorValue) {
        this.filterDataColorValue = filterDataColorValue;
    }
}
