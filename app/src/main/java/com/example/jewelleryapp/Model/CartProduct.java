package com.example.jewelleryapp.Model;

public class CartProduct {
    private String imgUrl,productName,productPrice,deliveryStatus;


    public CartProduct(String imgUrl, String productName, String productPrice, String deliveryStatus) {
        this.imgUrl = imgUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.deliveryStatus = deliveryStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
