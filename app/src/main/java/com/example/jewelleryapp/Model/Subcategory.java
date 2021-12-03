package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class Subcategory {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("imgpath")
    private String imgpath;

    public Subcategory(int id, String name, String imgpath) {
        this.id = id;
        this.name = name;
        this.imgpath = imgpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
