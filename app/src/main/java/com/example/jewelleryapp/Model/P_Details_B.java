package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class P_Details_B {
    @SerializedName("id")
    private int id;

    @SerializedName("types")
    private String types;

    @SerializedName("cost")
    private double cost;

    public P_Details_B(int id, String types, double cost) {
        this.id = id;
        this.types = types;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
