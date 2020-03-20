package com.app.iccomm.Model;

public class CartModel {
    private String prod_cart_id, prod_qty;
    private String prod_cart_img, prod_cart_price, prod_cart_name, prod_cart_sold_by;
    private String variant;
    private String variantSize;
    private String variantColor;

    public CartModel() {
    }


    public CartModel(String prod_cart_id, String prod_cart_img, String prod_cart_price, String prod_cart_name, String prod_cart_sold_by, String prod_qty) {
        this.prod_cart_id = prod_cart_id;
        this.prod_cart_img = prod_cart_img;
        this.prod_cart_price = prod_cart_price;
        this.prod_cart_name = prod_cart_name;
        this.prod_cart_sold_by = prod_cart_sold_by;
        this.prod_qty = prod_qty;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getProd_cart_id() {
        return prod_cart_id;
    }

    public void setProd_cart_id(String prod_cart_id) {
        this.prod_cart_id = prod_cart_id;
    }

    public String getProd_cart_img() {
        return prod_cart_img;
    }

    public void setProd_cart_img(String prod_cart_img) {
        this.prod_cart_img = prod_cart_img;
    }

    public String getProd_cart_price() {
        return prod_cart_price;
    }

    public void setProd_cart_price(String prod_cart_price) {
        this.prod_cart_price = prod_cart_price;
    }

    public String getProd_cart_name() {
        return prod_cart_name;
    }

    public void setProd_cart_name(String prod_cart_name) {
        this.prod_cart_name = prod_cart_name;
    }

    public String getProd_cart_sold_by() {
        return prod_cart_sold_by;
    }

    public void setProd_cart_sold_by(String prod_cart_sold_by) {
        this.prod_cart_sold_by = prod_cart_sold_by;
    }

    public String getProd_qty() {
        return prod_qty;
    }

    public void setProd_qty(String prod_qty) {
        this.prod_qty = prod_qty;
    }

    public String getVariantSize() {
        return variantSize;
    }

    public void setVariantSize(String variantSize) {
        this.variantSize = variantSize;
    }

    public String getVariantColor() {
        return variantColor;
    }

    public void setVariantColor(String variantColor) {
        this.variantColor = variantColor;
    }
}
