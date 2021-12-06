package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class StoreVariantsDetails {
    @SerializedName("product")
    private String product;

    @SerializedName("price")
    private String price;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("variants")
    private String variants;

    @SerializedName("aid")
    private String aid;

    public StoreVariantsDetails(String product, String price, String user_id, String variants, String aid) {
        this.product = product;
        this.price = price;
        this.user_id = user_id;
        this.variants = variants;
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }



    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVariants() {
        return variants;
    }

    public void setVariants(String variants) {
        this.variants = variants;
    }
}
