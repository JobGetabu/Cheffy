package com.app.cheffyuser.create_account.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.app.cheffyuser.food_category.model.Page
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ShippingRequest(
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var lat: String? = null,
    var lon: String? = null,
    var state: String? = null,
    var zipCode: String? = null,
    var deliveryNote: String? = null
)

@SuppressLint("ParcelCreator")
@Parcelize
data class SetShippingResponse(
    @SerializedName("data")
    val shippingResponseData: ShippingData? = null,
    @SerializedName("message")
    val message: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ShippingAddressResponse(
    @SerializedName("data")
    val shippingResponseData: List<ShippingData>? = null,
    @SerializedName("message")
    val page: Page? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ShippingData(
    @SerializedName("addressLine1")
    val addressLine1: String? = null,
    @SerializedName("addressLine2")
    val addressLine2: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("isDefaultAddress")
    val isDefaultAddress: Boolean? = null,
    @SerializedName("lat")
    val lat: String? = null,
    @SerializedName("lon")
    val lon: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("zipCode")
    val zipCode: String? = null,
    @SerializedName("deliveryNote")
    val deliveryNote: String? = null
) : Parcelable

