package com.app.cheffyuser.create_account.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AccessToken {
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null

    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("refresh_token")
    @Expose
    var refreshToken: String? = null
}
