package com.app.cheffyuser.networking

import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.model.AddToBasketRequest
import com.app.cheffyuser.home.model.AddToBasketResponse
import com.app.cheffyuser.home.model.FoodNearByModel
import com.app.cheffyuser.home.model.PlatesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //region auth api

    @POST("user")
    suspend fun createUserAccount(@Body signupRequest: SignupRequest): SignupResponse

    @POST("user/login")
    suspend fun loginUserAccount(@Body loginRequest: LoginRequest): LoginResponse

    @GET("user")
    suspend fun getUser(): ProfileResponse

    @GET("shipping")
    suspend fun getShipping(): List<ShippingResponse>

    @POST("shipping")
    suspend fun setShipping(shippingRequest: ShippingRequest): ShippingResponse

    //endregion


    //region home apis

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

    @GET("plate/")
    suspend fun getFoodNearbyLocation(
        @Query("latitude") lat: String,
        @Query("longitude") lon: String,
        @Query("radiusMiles") radiusMiles: String
    ): MutableList<PlatesResponse>

    @GET("category")
    suspend fun getFoodCategory(): MutableList<FoodCatModel>

    //endregion

    //Region Basket API

    @POST("basket")
    suspend fun addToBasket(@Body addToBasketRequest: AddToBasketRequest): List<AddToBasketResponse>

    //end region

    @POST("refresh")
    @FormUrlEncoded
    fun refresh(@Field("refresh_token") refreshToken: String): Call<AccessToken>



}
