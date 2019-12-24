package com.app.cheffyuser.cart.models

import com.google.gson.annotations.SerializedName


data class AddToBasketRequest(
    val plates: MutableList<BasketRequest>
)

data class BasketRequest(
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("quantity")
    val quantity: Int? = null
)

data class BasketListResponse(
    @SerializedName("delivery_fee")
    val deliveryFee: Double? = null,
    @SerializedName("items")
    val items: List<BasketItems?>? = null,
    @SerializedName("sub_total")
    val subTotal: Double? = null,
    @SerializedName("total")
    val total: Double? = null
)

data class BasketItems(
    @SerializedName("basketItemId")
    val basketItemId: Int? = null,
    @SerializedName("basket_type")
    val basketType: String? = null,
    @SerializedName("custom_plate")
    val customPlate: BasketCustomPlate? = null,
    @SerializedName("note")
    val note: Any? = null,
    @SerializedName("plate")
    val plate: Plate? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("total_value")
    val totalValue: Double? = null
)

data class BasketCustomPlate(
    @SerializedName("chef")
    val chef: ChefData? = null,
    @SerializedName("chefDeliveryAvailable")
    val chefDeliveryAvailable: Boolean? = null,
    @SerializedName("chefID")
    val chefID: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class ChefData(
    @SerializedName("address")
    val address: List<AddressData?>? = null,
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

data class AddressData(
    @SerializedName("addressLine1")
    val addressLine1: String? = null,
    @SerializedName("addressLine2")
    val addressLine2: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deliveryNote")
    val deliveryNote: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("lat")
    val lat: String? = null,
    @SerializedName("lon")
    val lon: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("UserId")
    val userId: Int? = null,
    @SerializedName("zipCode")
    val zipCode: String? = null
)

data class Plate(
    @SerializedName("chefDeliveryAvailable")
    val chefDeliveryAvailable: Boolean? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("userId")
    val userId: Int? = null
)