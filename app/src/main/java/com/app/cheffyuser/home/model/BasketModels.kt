package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


data class AddToBasketRequest(
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("quantity")
    val quantity: Int? = null
)

data class AddToBasketResponse(
    @SerializedName("plate")
    val plate: Plate? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("total_value")
    val totalValue: Int? = null
)

data class Plate(
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class BasketListResponse(
    @SerializedName("delivery_fee")
    val deliveryFee: Int? = null,
    @SerializedName("items")
    val items: List<Item?>? = null,
    @SerializedName("sub_total")
    val subTotal: Int? = null,
    @SerializedName("total")
    val total: Int? = null
) {
    data class Item(
        @SerializedName("plate")
        val plate: List<Plate>? = null,
        @SerializedName("quantity")
        val quantity: Int? = null,
        @SerializedName("total_value")
        val totalValue: Int? = null
    )
}