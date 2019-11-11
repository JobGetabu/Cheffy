package com.app.cheffyuser.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class FoodNearByModel {

        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("plate_id")
        @Expose
        private Integer plateId;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("imageURL")
        @Expose
        private String imageURL;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("delivery_time")
        @Expose
        private Integer deliveryTime;
        @SerializedName("rating")
        @Expose
        private Integer rating;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Integer getPlateId() {
            return plateId;
        }

        public void setPlateId(Integer plateId) {
            this.plateId = plateId;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Integer deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

    }