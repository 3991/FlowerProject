package com.example.aristide.flowerproject.model;

/**
 * POJO
 * Each item in the recylerview is an Plant object
 *
 */

public class Plant {
    private String name;
    private int imageResId;
    private int days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
