package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


data class FavouriteRequest(
    val favType: String? = null,
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

