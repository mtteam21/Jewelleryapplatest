package com.example.jewelleryapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderItem {
    @SerializedName("imgpath")
    @Expose
    private String imageUrl;

    @SerializedName("url")
    @Expose
    private String promotionalUrl;

    public SliderItem() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPromotionalUrl() {
        return promotionalUrl;
    }

    public void setPromotionalUrl(String promotionalUrl) {
        this.promotionalUrl = promotionalUrl;
    }

    public SliderItem(String imageUrl, String promotionalUrl) {
        this.imageUrl = imageUrl;
        this.promotionalUrl = promotionalUrl;
    }



}
