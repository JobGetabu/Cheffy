package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


const val SEARCH_PLATE = "plate"
const val SEARCH_CATEGORY = "category"
const val SEARCH_CHEF= "chef"
const val SEARCH_TEXT = "text"

data class PredictionsResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("type_category")
    val typeCategory: List<TypeCategory?>? = null,
    @SerializedName("type_chef")
    val typeChef: List<TypeChef?>? = null,
    @SerializedName("type_plate")
    val typePlate: List<TypePlate?>? = null
)

data class TypeCategory(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)

data class TypeChef(
    @SerializedName("chef")
    val chef: ChefName? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class ChefName(
    @SerializedName("restaurant_name")
    val restaurantName: String? = null
)

data class TypePlate(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)

data class SearchResult(
    val type: String? = null,
    val id: Int? = null
)


data class PlateSearchResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("plates")
    val plates: List<PlateSearch?>? = null,
    @SerializedName("restaurants")
    val restaurants: List<Any?>? = null
)


data class PlateSearch(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("sell_count")
    val sellCount: Int? = null,
    @SerializedName("delivery_type")
    val deliveryType: String? = null,
    @SerializedName("available")
    val available: Boolean? = null,
    @SerializedName("chefDeliveryAvailable")
    val chefDeliveryAvailable: Boolean? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)