package com.app.cheffyuser.home.model
import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 *
 *
 * TODO:
 * Remove the @RawValue annotations once
 * the null responses return their data in the future
 * Consult API team
 *
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class PlatesResponse(
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("chef")
    val chef: Chef? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Ingredients")
    val ingredients: List<Ingredient?>? = null,
    @SerializedName("KitchenImages")
    val kitchenImages: List<KitchenImage?>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("PlateImages")
    val plateImages: List<PlateImage?>? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("ReceiptImages")
    val receiptImages: List<ReceiptImage?>? = null,
    @SerializedName("reviews")
    val reviews: List<Reviews>? = null,
    @SerializedName("sell_count")
    val sellCount: Int? = null
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Category(
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("url")
        val url: String? = null
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Chef(
        @SerializedName("address")
        val address: @RawValue List<Any?>? = null,
        @SerializedName("auth_token")
        val authToken: @RawValue Any? = null,
        @SerializedName("country_code")
        val countryCode: String? = null,
        @SerializedName("createdAt")
        val createdAt: String? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("imagePath")
        val imagePath: @RawValue Any? = null,
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
        val verificationPhoneToken: @RawValue Any? = null
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
    data class Reviews(
        @SerializedName("comment")
        val comment: String? = null,
        @SerializedName("rating")
        val rating: Int? = null,
        @SerializedName("user")
        val user: User? = null
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class User(
            @SerializedName("id")
            val id: Int? = null,
            @SerializedName("name")
            val name: String? = null
        ) : Parcelable
    }

}