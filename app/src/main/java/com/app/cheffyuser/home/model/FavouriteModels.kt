package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName



data class FavouriteRequest(
    val fav_type: String? = null,
    val plateId: Int? = null
)

data class FavouriteResponse(
    @SerializedName("data")
    val `data`: FavData? = null,
    @SerializedName("message")
    val message: String? = null
)

data class FavouriteDeleteResponse(
    @SerializedName("message")
    val message: String? = null
)

data class FavData(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("CustomplateId")
    val customplateId: Int? = null,
    @SerializedName("fav_type")
    val favType: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)


data class FavouriteListResponse(
    @SerializedName("data")
    val `data`: List<Data?>? = null,
    @SerializedName("message")
    val message: String? = null
)

data class Data(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("custom_plates")
    val customPlates: CustomPlates? = null,
    @SerializedName("CustomplateID")
    val customplateID: Int? = null,
    @SerializedName("CustomplateId")
    val customplateId: Int? = null,
    @SerializedName("fav_type")
    val favType: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Plate")
    val plate: FavPlate? = null,
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class CustomPlates(
    @SerializedName("close_date")
    val closeDate: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("CustomPlateImages")
    val customPlateImages: List<CustomPlateImage?>? = null,
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

data class CustomPlateImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("url")
    val url: String? = null
)

data class FavPlate(
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("delivery_type")
    val deliveryType: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("PlateImages")
    val plateImages: List<PlateImage?>? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("sell_count")
    val sellCount: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("UserId")
    val userId: Int? = null
)
