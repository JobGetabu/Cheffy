package com.app.cheffyuser.create_account.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



enum class UserType(type: String) {
    USER("user"),
    CHEF("chef")
}

data class SignupRequest(
    val name: String,
    val email: String,
    val user_type: UserType,
    val password: String
)

data class SignupResponse(
    @SerializedName("token")
    @Expose
    val token: String? = null,
    @SerializedName("result")
    @Expose
    val signupResult: SignupResult? = null,
    @SerializedName("status")
    @Expose
    val status: Int? = null
)

data class SignupResult(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("country_code")
    @Expose
    val country_code: String? = null,
    @SerializedName("phone_no")
    @Expose
    val phone_no: String? = null,
    @SerializedName("user_type")
    @Expose
    val user_type: String? = null,
    @SerializedName("verification_email_status")
    @Expose
    val verification_email_status: String? = null,
    @SerializedName("verification_phone_status")
    @Expose
    val verification_phone_status: String? = null,
    @SerializedName("createdAt")
    @Expose
    val createdAt: String? = null
)

data class LoginRequest(
    val login: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("data")
    val `data`: UserData? = null,
    @SerializedName("token")
    val token: String? = null
)

data class UserData(
    @SerializedName("address")
    val address: List<Any?>? = null,
    @SerializedName("auth_token")
    val authToken: String? = null,
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
    val phoneNo: String? = null,
    @SerializedName("restaurant_name")
    val restaurantName: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("stripe_id")
    val stripeId: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("user_ip")
    val userIp: String? = null,
    @SerializedName("user_type")
    val userType: String? = null,
    @SerializedName("verification_code")
    val verificationCode: String? = null,
    @SerializedName("verification_email_status")
    val verificationEmailStatus: String? = null,
    @SerializedName("verification_email_token")
    val verificationEmailToken: String? = null,
    @SerializedName("verification_phone_status")
    val verificationPhoneStatus: String? = null,
    @SerializedName("verification_phone_token")
    val verificationPhoneToken: String? = null
)

data class ProfileResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val `data`: UserData? = null
)

data class ShippingResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val `data`: List<ShippingData>? = null
)

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
)

data class ShippingRequest(
    @SerializedName("addressLine1")
    val addressLine1: String? = null,
    @SerializedName("addressLine2")
    val addressLine2: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("lat")
    val lat: String? = null,
    @SerializedName("lon")
    val lon: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("zipCode")
    val zipCode: String? = null
)

