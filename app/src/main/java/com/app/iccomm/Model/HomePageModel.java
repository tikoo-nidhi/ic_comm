package com.app.iccomm.Model;

import android.graphics.drawable.Drawable;

public class HomePageModel {

    // banner model
    private String banner_id, banner_img;

    // Best Offers, Offers For You & Accessories
    private String offers_id, offers_name, offers_img, cat_id;

    // Hot Deals & Featured Products
    private String hot_featured_id, hot_featured_name, hot_featured_price, hot_featured_discount, hot_featured_discounted_price, hot_featured_img;

    // New Products & Recently Viewed
    private String newProd_recent_id, newProd_recent_name, newProd_recent_price, newProd_recent_discount, newProd_recent_discounted_price, newProd_recent_img;

    //SORT
    private String sort_id,sort_name;

    // Brands
    private String brand_id, brand_name, brand_img;
    private Drawable imgViewAll;

    //Empty Constructor
    public HomePageModel() {
    }


    //SORT Constructor
    public HomePageModel(String sort_id, String sort_name) {
        this.sort_id = sort_id;
        this.sort_name = sort_name;
    }

    //Best Offers_Constructor
    public HomePageModel(String offers_id, String offers_name, String offers_img, String cat_id) {
        this.offers_id = offers_id;
        this.offers_name = offers_name;
        this.offers_img = offers_img;
        this.cat_id = cat_id;
    }

    //Hot Deals & Featured Products Constructor
    public HomePageModel(String hot_featured_id, String hot_featured_name, String hot_featured_price, String hot_featured_discount, String hot_featured_discounted_price, String hot_featured_img) {
        this.hot_featured_id = hot_featured_id;
        this.hot_featured_name = hot_featured_name;
        this.hot_featured_price = hot_featured_price;
        this.hot_featured_discount = hot_featured_discount;
        this.hot_featured_discounted_price = hot_featured_discounted_price;
        this.hot_featured_img = hot_featured_img;
    }


    // Banner Getter Setter
    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }


    //Best Offers, Offers For You & Accessories Getter Setter

    public String getOffers_id() {
        return offers_id;
    }

    public void setOffers_id(String offers_id) {
        this.offers_id = offers_id;
    }

    public String getOffers_name() {
        return offers_name;
    }

    public void setOffers_name(String offers_name) {
        this.offers_name = offers_name;
    }

    public String getOffers_img() {
        return offers_img;
    }

    public void setOffers_img(String offers_img) {
        this.offers_img = offers_img;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    //Hot Deals & Featured Products Getter Setter
    public String getHot_featured_id() {
        return hot_featured_id;
    }

    public void setHot_featured_id(String hot_featured_id) {
        this.hot_featured_id = hot_featured_id;
    }

    public String getHot_featured_name() {
        return hot_featured_name;
    }

    public void setHot_featured_name(String hot_featured_name) {
        this.hot_featured_name = hot_featured_name;
    }

    public String getHot_featured_price() {
        return hot_featured_price;
    }

    public void setHot_featured_price(String hot_featured_price) {
        this.hot_featured_price = hot_featured_price;
    }

    public String getHot_featured_discount() {
        return hot_featured_discount;
    }

    public void setHot_featured_discount(String hot_featured_discount) {
        this.hot_featured_discount = hot_featured_discount;
    }

    public String getHot_featured_discounted_price() {
        return hot_featured_discounted_price;
    }

    public void setHot_featured_discounted_price(String hot_featured_discounted_price) {
        this.hot_featured_discounted_price = hot_featured_discounted_price;
    }

    public String getHot_featured_img() {
        return hot_featured_img;
    }

    public void setHot_featured_img(String hot_featured_img) {
        this.hot_featured_img = hot_featured_img;
    }

    //New Products and Recently Viewed Getter Setter
    public String getNewProd_recent_id() {
        return newProd_recent_id;
    }

    public void setNewProd_recent_id(String newProd_recent_id) {
        this.newProd_recent_id = newProd_recent_id;
    }

    public String getNewProd_recent_name() {
        return newProd_recent_name;
    }

    public void setNewProd_recent_name(String newProd_recent_name) {
        this.newProd_recent_name = newProd_recent_name;
    }

    public String getNewProd_recent_price() {
        return newProd_recent_price;
    }

    public void setNewProd_recent_price(String newProd_recent_price) {
        this.newProd_recent_price = newProd_recent_price;
    }

    public String getNewProd_recent_discount() {
        return newProd_recent_discount;
    }

    public void setNewProd_recent_discount(String newProd_recent_discount) {
        this.newProd_recent_discount = newProd_recent_discount;
    }

    public String getNewProd_recent_discounted_price() {
        return newProd_recent_discounted_price;
    }

    public void setNewProd_recent_discounted_price(String newProd_recent_discounted_price) {
        this.newProd_recent_discounted_price = newProd_recent_discounted_price;
    }

    public String getNewProd_recent_img() {
        return newProd_recent_img;
    }

    public void setNewProd_recent_img(String newProd_recent_img) {
        this.newProd_recent_img = newProd_recent_img;
    }

    // Brand Getter Setter

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_img() {
        return brand_img;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }

    public Drawable getImgViewAll() {
        return imgViewAll;
    }

    public void setImgViewAll(Drawable imgViewAll) {
        this.imgViewAll = imgViewAll;
    }
}
