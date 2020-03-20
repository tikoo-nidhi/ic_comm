package com.app.iccomm.Model;

import android.graphics.drawable.Drawable;

public class MenuModel {

    public int position;
    public String id;
    public String menuName;
    public boolean hasChildren, isGroup;
    public Drawable drawable;


    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, Drawable drawable) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.drawable = drawable;
    }

    public MenuModel(int position, String id, String menuName, boolean hasChildren, boolean isGroup, Drawable drawable) {
        this.position = position;
        this.id = id;
        this.menuName = menuName;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
        this.drawable = drawable;
    }
}
