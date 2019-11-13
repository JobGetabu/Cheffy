package com.app.cheffyuser.networking.remote

import com.app.cheffyuser.create_account.model.LoginData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @get:GET("user")
    val user: Call<LoginData>

/*    @POST("login")
    fun login(@Body loginPhoneRequest: LoginPhoneRequest): Call<LoginPhoneResponse>

    @POST("login/verify")
    fun otpVerify(@Body verifyPhoneRequest: VerifyPhoneRequest): Call<VerifyPhoneRespond>

    @POST("register")
    fun saveNameEmail(@Body nameEmailRequest: NameEmailRequest): Call<NameEmailResponse>

    @POST("logout")
    fun logout(): Call<AccessToken>

    @POST("social_auth")
    fun socialAuth(@Body socialLoginRequest: SocialLoginRequest): Call<VerifyPhoneRespond>

    @POST("refresh")
    @FormUrlEncoded
    fun refresh(@Field("refresh_token") refreshToken: String): Call<AccessToken>

    @POST("update-social-user")
    @FormUrlEncoded
    fun updateUserDetails(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("provider_user_id") providerUserId: String,
        @Field("profile_image") profilePic: String
    ): Call<LoginPhoneResponse>*/

}
