package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class ChildCategoryList {

    @SerializedName("name")
    private String childccame;

    @SerializedName("id")
    private int id;

    @SerializedName("category")
    private String category;

    @SerializedName("subcategories")
    private String subcategory;


    public ChildCategoryList(String childccame, int id, String category, String subcategory) {
        this.childccame = childccame;
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
    }


    public String getChildccame() {
        return childccame;
    }

    public void setChildccame(String childccame) {
        this.childccame = childccame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
