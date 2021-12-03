package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class AddressModel {
    @SerializedName("address")
    private String address;
    @SerializedName("id")
    private int id;
    @SerializedName("uid")
    private int uid;
    @SerializedName("houseno")
    private String houseno;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("type")
    private String type;

   public boolean isSelected = true;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AddressModel(String address, int uid, String houseno, String landmark, String pincode, String type) {
        this.address = address;
        this.uid = uid;
        this.houseno = houseno;
        this.landmark = landmark;
        this.pincode = pincode;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
