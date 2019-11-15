package com.app.cheffyuser.home.repository

import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.home.model.FoodNearByModel
import com.app.cheffyuser.networking.remote.ApiService
import com.app.cheffyuser.networking.remote.Resource
import com.app.cheffyuser.networking.remote.ResponseHandler
import com.app.cheffyuser.networking.remote.RetrofitBuilder
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
            val response = apiService.getFoodNearBy(lat,lon,radius)
            responseHandler.handleSuccess(response)
        }catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }


}
