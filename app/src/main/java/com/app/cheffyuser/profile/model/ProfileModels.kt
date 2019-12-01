package com.app.cheffyuser.profile.model

import com.google.gson.annotations.SerializedName


data class ProfPicResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("result")
    val result: Result? = null
)

data class Result(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("ProfilePhoto")
    val profilePhoto: ProfilePhoto? = null,
    @SerializedName("social_security_number")
    val socialSecurityNumber: Any? = null,
    @SerializedName("state_type")
    val stateType: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)

data class ProfilePhoto(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("state_type")
    val stateType: String? = null,
    @SerializedName("url")
    val url: String? = null
)