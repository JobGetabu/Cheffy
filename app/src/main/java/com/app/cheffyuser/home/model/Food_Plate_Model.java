package com.app.cheffyuser.home.model;

public class Food_Plate_Model {

    private String food_name,food_text,food_price,food_time,food_delivery_text,food_cat_deliver;
    private int food_image;


    public Food_Plate_Model(String food_name, String food_text, String food_price, String food_time, String food_delivery_text, String food_cat_deliver, int food_image) {
        this.food_name = food_name;
        this.food_text = food_text;
        this.food_price = food_price;
        this.food_time = food_time;
        this.food_delivery_text = food_delivery_text;
        this.food_cat_deliver = food_cat_deliver;
        this.food_image = food_image;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_text() {
        return food_text;
    }

    public void setFood_text(String food_text) {
        this.food_text = food_text;
    }

    public String getFood_price() {
        return food_price;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
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

    public String getFood_cat_deliver() {
        return food_cat_deliver;
    }

    public void setFood_cat_deliver(String food_cat_deliver) {
        this.food_cat_deliver = food_cat_deliver;
    }

    public int getFood_image() {
        return food_image;
    }

    public void setFood_image(int food_image) {
        this.food_image = food_image;
    }
}
