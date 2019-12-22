package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


data class BidAcceptanceResponse(
    @SerializedName("basket")
    val basket: Basket? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("plate_data")
    val plateData: PlateData? = null
)

data class Basket(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("UserId")
    val userId: Int? = null
)

data class PlateData(
    @SerializedName("chefDeliveryAvailable")
    val chefDeliveryAvailable: Boolean? = null,
    @SerializedName("chefID")
    val chefID: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("customPlateId")
    val customPlateId: Int? = null,
    @SerializedName("delivery_type")
    val deliveryType: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("preparation_time")
    val preparationTime: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)