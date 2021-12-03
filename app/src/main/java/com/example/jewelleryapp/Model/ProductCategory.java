package com.example.jewelleryapp.Model;

public class ProductCategory {
    private String categoryName,categoryIcon;

    public ProductCategory(String categoryName, String categoryIcon) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
