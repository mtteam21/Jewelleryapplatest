package com.example.jewelleryapp.Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("category")
    private String category;

    @SerializedName("subcategory")
    private String subcategory;

    @SerializedName("child_category")
    private String child_category;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

//    @SerializedName("imgpath")
//    String imgpath;

    @SerializedName("discount")
    double discount;

    @SerializedName("imgpath")
    List<ProductImages> productImagesList;

    public Product(List<ProductImages> productImagesList) {
        this.productImagesList = productImagesList;
    }

    public List<ProductImages> getProductImagesList() {
        return productImagesList;
    }

    public void setProductImagesList(List<ProductImages> productImagesList) {
        this.productImagesList = productImagesList;
    }

    public Product(int id) {
        this.id = id;
    }

    public Product(String name, String category, String subcategory, String child_category, double price, String description, double discount) {
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.child_category = child_category;
        this.price = price;
        this.description = description;
//        this.imgpath = imgpath;
        this.discount = discount;
    }

    public Product() {

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

    public String getChild_category() {
        return child_category;
    }

    public void setChild_category(String child_category) {
        this.child_category = child_category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getImgpath() {
//        return imgpath;
//    }
//
//    public void setImgpath(String imgpath) {
//        this.imgpath = imgpath;
//    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
