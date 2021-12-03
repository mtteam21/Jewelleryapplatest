package com.example.jewelleryapp.CustmizedProjects.Models;

import com.google.gson.annotations.SerializedName;

public class CustomD_Model {
    @SerializedName("name")
    String designName;

    @SerializedName("id")
    int id;


    public CustomD_Model(String designName, int id) {
        this.designName = designName;
        this.id = id;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
