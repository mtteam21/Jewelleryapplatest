package com.example.jewelleryapp.CustmizedProjects.Models;

import com.google.gson.annotations.SerializedName;

public class P_SpecificationModel {

    @SerializedName("name")
    private String productSpecificationName;
    @SerializedName("cu_id")
    private int id;
    private boolean isCheck;

    public P_SpecificationModel(String productSpecificationName, int id) {
        this.productSpecificationName = productSpecificationName;
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getProductSpecificationName() {
        return productSpecificationName;
    }

    public void setProductSpecificationName(String productSpecificationName) {
        this.productSpecificationName = productSpecificationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
