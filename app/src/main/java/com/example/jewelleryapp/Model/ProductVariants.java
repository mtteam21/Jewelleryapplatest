package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class ProductVariants {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("product")
    private int product;




    public ProductVariants(int id, String name, int product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }


    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
