package com.ute.project2.model;

public class Category {
    private final String categoryName;
    private final int categoryImage;

    public Category(String categoryName, int categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
