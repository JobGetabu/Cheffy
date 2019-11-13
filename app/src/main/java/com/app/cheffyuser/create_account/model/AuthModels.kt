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
    val loginResult: LoginResult? = null,
    @SerializedName("status")
    @Expose
    val status: Int? = null
)

data class LoginResult(
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
    @SerializedName("verification_status")
    @Expose
    val verification_status: Any? = null
)
