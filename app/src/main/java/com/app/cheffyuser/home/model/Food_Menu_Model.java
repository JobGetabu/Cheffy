package com.app.cheffyuser.home.model;

public class Food_Menu_Model {

    private String food_status,food_name, food_rating,food_time,food_delivery_text;
    private int food_image;

    public Food_Menu_Model(String food_status, String food_name, String food_rating, String food_time, String food_delivery_text, int food_image) {
        this.food_status = food_status;
        this.food_name = food_name;
        this.food_rating = food_rating;
        this.food_time = food_time;
        this.food_delivery_text = food_delivery_text;
        this.food_image = food_image;
    }

    public String getFood_status() {
        return food_status;
    }

    public void setFood_status(String food_status) {
        this.food_status = food_status;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_rating() {
        return food_rating;
    }

    public void setFood_rating(String food_rating) {
        this.food_rating = food_rating;
    }

    public String getFood_time() {
        return food_time;
    }

    public void setFood_time(String food_time) {
        this.food_time = food_time;
    }

    public String getFood_delivery_text() {
        return food_delivery_text;
    }

    public void setFood_delivery_text(String food_delivery_text) {
        this.food_delivery_text = food_delivery_text;
    }

    public int getFood_image() {
        return food_image;
    }

    public void setFood_image(int food_image) {
        this.food_image = food_image;
    }
}
