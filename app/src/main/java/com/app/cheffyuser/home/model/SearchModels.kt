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
    val chef: Chef? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class Chef(
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