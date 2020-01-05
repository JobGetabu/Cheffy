package com.app.cheffyuser.home.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.app.cheffyuser.food_category.model.Page
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue




@SuppressLint("ParcelCreator")
@Parcelize
data class GetPlateResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("page")
    val page: Page? = null,
    @SerializedName("data")
    val platesResponse: MutableList<PlatesResponse>? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SinglePlatesResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("page")
    val page: Page? = null,
    @SerializedName("data")
    val data: PlatesResponse? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PlatesResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("userId")
    val userId: Int? = null,
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
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("chef")
    val chef: Chef? = null,
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("AggregateReview")
    val aggregateReview: AggregateReview? = null,
    @SerializedName("DietCategories")
    val dietCategories: List<DietCategory?>? = null,
    @SerializedName("Ingredients")
    val ingredients: List<Ingredient?>? = null,
    @SerializedName("PlateImages")
    val plateImages: List<PlateImage?>? = null,
    @SerializedName("KitchenImages")
    val kitchenImages: List<KitchenImage?>? = null,
    @SerializedName("ReceiptImages")
    val receiptImages: List<ReceiptImage?>? = null,
    @SerializedName("reviews")
    val reviews: MutableList<Review>? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Chef(
    @SerializedName("address")
    val address: List<AddressData>? = null,
    @SerializedName("auth_token")
    val authToken:  String? = null,
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null,
    @SerializedName("location_lat")
    val locationLat: String? = null,
    @SerializedName("location_lon")
    val locationLon: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phone_no")
    val phoneNo: @RawValue Any? = null,
    @SerializedName("restaurant_name")
    val restaurantName: String? = null,
    @SerializedName("status")
    val status: @RawValue Any? = null,
    @SerializedName("stripe_id")
    val stripeId: @RawValue Any? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("user_ip")
    val userIp: @RawValue Any? = null,
    @SerializedName("user_type")
    val userType: String? = null,
    @SerializedName("verification_code")
    val verificationCode: @RawValue Any? = null,
    @SerializedName("verification_email_status")
    val verificationEmailStatus: String? = null,
    @SerializedName("verification_email_token")
    val verificationEmailToken: String? = null,
    @SerializedName("verification_phone_status")
    val verificationPhoneStatus: String? = null,
    @SerializedName("verification_phone_token")
    val verificationPhoneToken:  String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Category(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("url")
    val url: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class DietCategory(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("PlateDietCategory")
    val plateDietCategory: PlateDietCategory? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PlateDietCategory(
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("dietCategoryId")
    val dietCategoryId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Ingredient(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("purchase_date")
    val purchaseDate: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PlateImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class KitchenImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ReceiptImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
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
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("zipCode")
    val zipCode: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Review(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("user")
    val user: User? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AggregateReview(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("review_type")
    val reviewType: String? = null,
    @SerializedName("chefID")
    val chefID: Int? = null,
    @SerializedName("driverID")
    val driverID: Int? = null,
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("userCount")
    val userCount: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("driverId")
    val driverId: Int? = null
) : Parcelable