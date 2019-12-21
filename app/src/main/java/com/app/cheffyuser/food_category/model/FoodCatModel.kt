package com.app.cheffyuser.food_category.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class FoodCategoryResponse(
    @SerializedName("data")
    val `data`: MutableList<FoodCatModel?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("page")
    val page: Page? = null
): Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class FoodCatModel(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("UserId")
    val userId: Int? = null
): Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Page(
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("to")
    val to: Int? = null
) : Parcelable