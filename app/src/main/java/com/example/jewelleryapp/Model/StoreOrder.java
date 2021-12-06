package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class StoreOrder {

    @SerializedName("aid")
    private String aid;

    @SerializedName("qty")
    private String qty;

    @SerializedName("product")
    private String product;

    @SerializedName("price")
    private String price;

    @SerializedName("userid")
    private String userid;

    public StoreOrder(String aid, String qty, String product, String price,String userId) {
        this.aid = aid;
        this.qty = qty;
        this.product = product;
        this.price = price;
        this.userid = userId ;
    }


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

