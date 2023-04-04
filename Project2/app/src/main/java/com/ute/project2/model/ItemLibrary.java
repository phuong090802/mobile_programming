package com.ute.project2.model;


public class ItemLibrary {
    private final int image;
    private final int text;

    public ItemLibrary(int image, int text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public int getText() {
        return text;
    }
}
