package com.app.cheffyuser.home.model;

public class Home_Horizontal_Model {

    private int imageView;
    private String text;

    public Home_Horizontal_Model(int imageView, String text) {
        this.imageView = imageView;
        this.text = text;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
