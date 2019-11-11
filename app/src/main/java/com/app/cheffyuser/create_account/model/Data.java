package com.app.cheffyuser.create_account.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("location_lat")
    @Expose
    private String location_lat;


    @SerializedName("location_lon")
    @Expose
    private String location_lon;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("restaurant_name")
    @Expose
    private Object restaurantName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("verification_code")
    @Expose
    private Object verificationCode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user_ip")
    @Expose
    private Object userIp;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Data(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Object getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(Object restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocationLat() {
        return location_lat;
    }

    public void setLocationLat(String  location_lat) {
        this.location_lat = location_lat;
    }


    public String getLocationLon() {
        return location_lon;
    }

    public void setLocationLon(String  location_lon) {
        this.location_lon = location_lon;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Object getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Object verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getUserIp() {
        return userIp;
    }

    public void setUserIp(Object userIp) {
        this.userIp = userIp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}