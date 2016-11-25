package com.example.aristide.flowerproject.model;

/**
 * POJO
 * Each item in the recylerview is an Flower object
 *
 */

public class Flower {
    private String name;
    private int imageResId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
