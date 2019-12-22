package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


data class CreateCustomResponse(
    @SerializedName("data")
    val `data`: CustomData? = null,
    @SerializedName("message")
    val message: String? = null
)

data class CustomData(
    @SerializedName("auction")
    val auction: Auction? = null,
    @SerializedName("images")
    val images: List<CustomImage?>? = null,
    @SerializedName("plate")
    val plate: CustomPlate? = null,
    @SerializedName("status")
    val status: Int? = null
)

data class Auction(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("CustomPlateID")
    val customPlateID: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("state_type")
    val stateType: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class CustomImage(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("CustomPlateID")
    val customPlateID: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)

data class CustomPlate(
    @SerializedName("close_date")
    val closeDate: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price_max")
    val priceMax: Int? = null,
    @SerializedName("price_min")
    val priceMin: Int? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class CreateCustomRequest(
    val name: String,
    val description: String,
    val price_min: Double,
    val price_max: Double,
    val quantity: Int,
    val chef_location_radius: Double
)

data class UploadCustomImagesResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("images")
    val images: List<CustomImage?>? = null
)