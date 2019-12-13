package com.app.cheffyuser.home.repository

import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.create_account.model.ProfileResponse
import com.app.cheffyuser.create_account.model.ShippingDataResponse
import com.app.cheffyuser.create_account.model.ShippingRequest
import com.app.cheffyuser.create_account.model.ShippingResponse
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.networking.ApiService
import com.app.cheffyuser.networking.Resource
import com.app.cheffyuser.networking.ResponseHandler
import com.app.cheffyuser.networking.RetrofitBuilder
import com.app.cheffyuser.profile.model.ProfPicResponse
import okhttp3.MultipartBody
import timber.log.Timber

class HomeRepository {

    private val apiService: ApiService
    private val apiServiceAuthed: ApiService
    private val responseHandler: ResponseHandler

    init {

        val tokenManager = CheffyApp.instance!!.tokenManager
        apiService = RetrofitBuilder.createService(ApiService::class.java)
        apiServiceAuthed =
            RetrofitBuilder.createServiceWithAuth(ApiService::class.java, tokenManager)
        responseHandler = ResponseHandler()
    }

    suspend fun fetchNearByFood(
        lat: String,
        lon: String,
        radius: String
    ): Resource<MutableList<FoodNearByModel>> {
        return try {
            val response = apiService.getFoodNearBy(lat, lon, radius)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodPopular(): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodPopular()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodNewest(): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodNewest()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchRelatedFood(foodId: Int): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getRelatedFood(foodId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodNearbyLocation(
        lat: String,
        lon: String,
        radiusMiles: String
    ): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodNearbyLocation(lat, lon, radiusMiles)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodCategory(): Resource<MutableList<FoodCatModel>> {
        return try {
            val response = apiService.getFoodCategory()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchUser(): Resource<ProfileResponse> {
        return try {
            val response = apiServiceAuthed.getUser()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchShipping(): Resource<List<ShippingDataResponse>> {
        return try {
            val response = apiServiceAuthed.getShipping()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun setShipping(shippingRequest: ShippingRequest): Resource<ShippingResponse> {
        return try {
            val response = apiServiceAuthed.setShipping(shippingRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    //Region Basket


    suspend fun addToBasket(addToBasketRequest: AddToBasketRequest): Resource<List<AddToBasketResponse>> {
        return try {
            val response = apiServiceAuthed.addToBasket(addToBasketRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getBasket(): Resource<BasketListResponse> {
        return try {
            val response = apiServiceAuthed.getBasket()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }


    //end Region

    suspend fun profileUpload(fileToUpload: MultipartBody.Part): Resource<ProfPicResponse> {
        return try {
            val response = apiServiceAuthed.uploadProfileImage(fileToUpload)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

}
