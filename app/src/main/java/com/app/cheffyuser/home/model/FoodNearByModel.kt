package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName

data class FoodNearByModel(
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("delivery_type")
    val deliveryType: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("distance")
    val distance: Int? = null,
    @SerializedName("imageURL")
    val imageURL: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("plate_id")
    val plateId: Int? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("userId")
    val userId: Int? = null
)