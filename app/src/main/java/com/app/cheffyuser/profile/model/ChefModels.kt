package com.app.cheffyuser.profile.model

import com.google.gson.annotations.SerializedName


data class ChefResponse(
    @SerializedName("chef")
    val chef: Chef? = null,
    @SerializedName("chefId")
    val chefId: String? = null,
    @SerializedName("data")
    val `data`: List<PlateData?>? = null,
    @SerializedName("message")
    val message: String? = null
)

data class Chef(
    @SerializedName("auth_token")
    val authToken: Any? = null,
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
    val locationLat: Any? = null,
    @SerializedName("location_lon")
    val locationLon: Any? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phone_no")
    val phoneNo: String? = null,
    @SerializedName("restaurant_name")
    val restaurantName: String? = null,
    @SerializedName("status")
    val status: Any? = null,
    @SerializedName("stripe_id")
    val stripeId: Any? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("user_ip")
    val userIp: Any? = null,
    @SerializedName("user_type")
    val userType: String? = null,
    @SerializedName("verification_code")
    val verificationCode: Any? = null,
    @SerializedName("verification_email_status")
    val verificationEmailStatus: String? = null,
    @SerializedName("verification_email_token")
    val verificationEmailToken: String? = null,
    @SerializedName("verification_phone_status")
    val verificationPhoneStatus: String? = null,
    @SerializedName("verification_phone_token")
    val verificationPhoneToken: String? = null
)

data class PlateData(
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("delivery_time")
    val deliveryTime: Int? = null,
    @SerializedName("delivery_type")
    val deliveryType: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("sell_count")
    val sellCount: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)