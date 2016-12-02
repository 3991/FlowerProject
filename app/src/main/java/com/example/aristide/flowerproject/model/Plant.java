package com.example.aristide.flowerproject.model;

/**
 *
 * Each item in the recylerview is an Plant object
 */
public class Plant {
    private String name;
    private String date;
    private int imageResId;
    private int days;
    private int state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {return days; }

    public void setDays(int days) {
        this.days = days;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getState() {return state; }

    public void setState(int state) {
        this.state = state;
    }
}
