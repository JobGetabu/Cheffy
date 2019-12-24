package com.app.cheffyuser.create_account.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class SignupRequest(
    val email: String
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

@SuppressLint("ParcelCreator")
@Parcelize
data class UserData(
    @SerializedName("address")
    val address: List<ShippingData>? = null,
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
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProfileResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val `data`: UserData? = null
) : Parcelable

data class VerifyRequest(
    val email: String?,
    val email_token: String?
)

data class VerifyResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Int? = null
)

data class RegistrationRequest(
    val email: String?,
    val name: String?,
    val password: String?,
    val user_type: String = "user"
)

data class RegistrationResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("result")
    val `data`: UserData? = null
)

data class ForgotRequest(
    val email: String?
)

data class ForgotResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Int? = null
)

data class ChangePasswordRequest(
    val email: String?,
    val email_token: String?,
    val password: String?
)

data class ChangePasswordResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Int? = null
)

data class EditProfileRequest(
    val name: String?,
    val country_code: String?,
    val email: String?,
    val phone_no: String?,
    val latitude: String?,
    val longitude: String?,
    val image_path: String?
)

data class EditProfileResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val userData: UserData? = null
)

data class LogoutResponse(
    @SerializedName("message")
    val message: String? = null
)

data class SocialRegRequest(
    val email: String? = null,
    val imagePath: String? = null,
    val name: String? = null,
    val provider: String? = null,
    val provider_user_id: String? = null,
    val user_type: String? = null
)

data class SocialLoginRequest(
    val email: String? = null,
    val provider: String? = null,
    val provider_user_id: String? = null
)
