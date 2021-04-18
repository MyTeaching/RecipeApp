package com.example.foodieapp;

import android.graphics.Bitmap;

public class MealItem {
    private String title;
    private String description;
    private int imageResource;
    private Bitmap imageBitmap;
    private String ingredients;
    private String link;

    public MealItem(String title, String description){
        this.title = title;
        this.description = description;
    }

    public MealItem(String title, String description, int imageId){
        this.title = title;
        this.description = description;
        this.imageResource = imageId;
    }

    public MealItem(String title, String description, int imageId, String ingredients, String link){
        this.title = title;
        this.description = description;
        this.imageResource = imageId;
        this.ingredients = ingredients;
        this.link = link;
    }

    public MealItem(String title, String description, String ingredients, String link){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.link = link;
    }

    public MealItem(String title, String description, Bitmap mealImage, String ingredients, String link){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.link = link;
        this.imageBitmap = mealImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId(){
        return imageResource;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getLink() {
        return link;
    }

    public Bitmap getImageBitmap(){ return imageBitmap; }
}
