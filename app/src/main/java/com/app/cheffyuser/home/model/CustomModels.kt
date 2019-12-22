package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


data class CreateCustomResponse(
    @SerializedName("data")
    val customData: CustomData? = null,
    @SerializedName("message")
    val message: String? = null
)

data class CustomData(
    @SerializedName("auction")
    val auction: Auction? = null,
    @SerializedName("customPlate")
    val customPlate: CustomPlate? = null,
    @SerializedName("status")
    val status: Int? = null
)

data class Auction(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("customPlateId")
    val customPlateId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("state_type")
    val stateType: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class CustomPlate(
    @SerializedName("chef_location_radius")
    val chefLocationRadius: Int? = null,
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


data class CustomPlateResponse(
    @SerializedName("data")
    val customPlateResponseData: List<CustomPlateResponseData?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("page")
    val page: Page? = null
)

data class CustomPlateResponseData(
    @SerializedName("chef_location_radius")
    val chefLocationRadius: Int? = null,
    @SerializedName("close_date")
    val closeDate: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("CustomPlateAuction")
    val customPlateAuction: CustomPlateAuction? = null,
    @SerializedName("CustomPlateImages")
    val customPlateImages: List<CustomPlateImages?>? = null,
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

data class CustomPlateAuction(
    @SerializedName("bidCount")
    val bidCount: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("state_type")
    val stateType: String? = null,
    @SerializedName("winner")
    val winner: Any? = null,
    @SerializedName("CustomPlateAuctionBids")
    val customPlateAuctionBids: MutableList<CustomPlateAuctionBids>? = null
)


data class CustomPlateImages(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)


data class CustomPlateAuctionBids(
    @SerializedName("Chef")
    val chef: ChefData? = null,
    @SerializedName("chefDeliveryAvailable")
    val chefDeliveryAvailable: Boolean? = null,
    @SerializedName("chefID")
    val chefID: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Any? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("preparation_time")
    val preparationTime: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("winner")
    val winner: Boolean? = null
)

data class ChefData(
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null,
    @SerializedName("location_lat")
    val locationLat: Any? = null,
    @SerializedName("location_lon")
    val locationLon: Any? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone_no")
    val phoneNo: String? = null
)


data class Page(
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("to")
    val to: Int? = null
)
