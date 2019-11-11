package com.app.cheffyuser.home.model;

public class Home_Vertical_Model {

    private int imageView;
    private String name;
    private String price;
    private String quantity;

    public Home_Vertical_Model(int imageView, String name, String price, String quantity) {
        this.imageView = imageView;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}


