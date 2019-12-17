package com.app.cheffyuser.networking

import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.profile.model.ChefResponse
import com.app.cheffyuser.profile.model.ProfPicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //region auth api

    @POST("user")
    suspend fun createUserAccount(@Body signupRequest: SignupRequest): SignupResponse

    @PUT("user/edit")
    suspend fun editUserAccount(@Body editProfileRequest: EditProfileRequest): EditProfileResponse

    @POST("user/verify-email-token")
    suspend fun verifyAccount(@Body verifyRequest: VerifyRequest): VerifyResponse

    @POST("user/complete-registration")
    suspend fun registerAccount(@Body registrationRequest: RegistrationRequest): RegistrationResponse


    @POST("user/login")
    suspend fun loginUserAccount(@Body loginRequest: LoginRequest): LoginResponse

    @POST("user/forgot-password")
    suspend fun forgotPassword(@Body forgotRequest: ForgotRequest): ForgotResponse

    @GET("user")
    suspend fun getUser(): ProfileResponse

    @GET("shipping")
    suspend fun getShipping(): List<ShippingDataResponse>

    @POST("shipping")
    suspend fun setShipping(@Body shippingRequest: ShippingRequest): ShippingResponse

    //endregion

    //region home apis

    @GET("plate/show/{plateId}")
    suspend fun getPlate(
        @Path(value = "plateId") plateId: Int
    ): SinglePlatesResponse

    @GET("plate/near")
    suspend fun getFoodNearBy(
        @Query("latitude") lat: String,
        @Query("longitude") lon: String,
        @Query("radius") radius: String
    ): MutableList<FoodNearByModel>

    @GET("plate/?newest")
    suspend fun getFoodPopular(): MutableList<PlatesResponse>

    @GET("plate/?popular")
    suspend fun getFoodNewest(): MutableList<PlatesResponse>

    @GET("plate/{foodId}/related")
    suspend fun getRelatedFood(@Path("foodId") foodId: Int): MutableList<PlatesResponse>

    @GET("category/{categoryId}/plates")
    suspend fun getPlatesByCategory(@Path("categoryId") categoryId: Int): MutableList<PlatesResponse>

    @GET("plate/")
    suspend fun getFoodNearbyLocation(
        @Query("latitude") lat: String,
        @Query("longitude") lon: String,
        @Query("radius") radiusMiles: String
    ): MutableList<PlatesResponse>

    @GET("category")
    suspend fun getFoodCategory(): MutableList<FoodCatModel>

    @POST("favourite/add")
    suspend fun addFavourite(@Body favouriteRequest: FavouriteRequest): FavouriteResponse

    @DELETE("favourite/remove/{fav_type}/{plateId}")
    suspend fun removeFavourite(
        @Path("fav_type") fav_type: String,
        @Path("plateId") plateId: Int
    ): FavouriteDeleteResponse

    @GET("favourite/")
    suspend fun getFavourite(): FavouriteListResponse

    //endregion

    //chef

    @GET("plate/chef/{chefId}")
    suspend fun getChefData(
        @Path(value = "chefId") chefId: Int
    ): ChefResponse

    //endregion

    //Region Basket API

    @POST("basket")
    suspend fun addToBasket(@Body addToBasketRequest: AddToBasketRequest): List<AddToBasketResponse>

    @GET("basket")
    suspend fun getBasket(): BasketListResponse

    //end region

    @POST("refresh")
    @FormUrlEncoded
    fun refresh(@Field("refresh_token") refreshToken: String): Call<AccessToken>


    //region upload profile
    @Multipart
    @PUT("docs/profilePhoto")
    suspend fun uploadProfileImage(@Part file: MultipartBody.Part): ProfPicResponse

    //end region

    @GET("user/searchPredictions")
    suspend fun getSearchPredictions(): PredictionsResponse


    //logout
    @POST("user/logout")
    suspend fun logout(): LogoutResponse
}
