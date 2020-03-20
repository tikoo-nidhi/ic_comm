package com.app.iccomm.Model;

public class WishListModel {
    private int prod_id;
    private String prod_img, prodName, prodActualPrice, prodDiscountedPrice, discountType, discount;

    public WishListModel() {
    }


    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_img() {
        return prod_img;
    }

    public void setProd_img(String prod_img) {
        this.prod_img = prod_img;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdActualPrice() {
        return prodActualPrice;
    }

    public void setProdActualPrice(String prodActualPrice) {
        this.prodActualPrice = prodActualPrice;
    }

    public String getProdDiscountedPrice() {
        return prodDiscountedPrice;
    }

    public void setProdDiscountedPrice(String prodDiscountedPrice) {
        this.prodDiscountedPrice = prodDiscountedPrice;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
