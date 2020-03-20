package com.app.iccomm.Model;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private String cat_id, cat_name, cat_img;
    private String sub_cat_id, sub_cat_name, sub_cat_img;
    private String child_id, child_name, child_img;
    private List<CategoryModel> data = new ArrayList<>();
    private List<CategoryModel> dataChild = new ArrayList<>();

    public CategoryModel() {
    }

    public CategoryModel(String cat_id, String cat_name, String cat_img, String sub_cat_id, String sub_cat_name, String sub_cat_img, String child_id, String child_name, String child_img) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.cat_img = cat_img;
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
        this.sub_cat_img = sub_cat_img;
        this.child_id = child_id;
        this.child_name = child_name;
        this.child_img = child_img;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getSub_cat_img() {
        return sub_cat_img;
    }

    public void setSub_cat_img(String sub_cat_img) {
        this.sub_cat_img = sub_cat_img;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_img() {
        return child_img;
    }

    public void setChild_img(String child_img) {
        this.child_img = child_img;
    }

    public List<CategoryModel> getData() {
        return data;
    }

    public void setData(List<CategoryModel> data) {
        this.data = data;
    }

    public List<CategoryModel> getDataChild() {
        return dataChild;
    }

    public void setDataChild(List<CategoryModel> dataChild) {
        this.dataChild = dataChild;
    }
}
