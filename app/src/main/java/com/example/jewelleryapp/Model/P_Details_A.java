package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class P_Details_A {
    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("quantity")
    private String quantity;

    public P_Details_A(int id, String type, String quantity) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}