package com.app.cheffyuser.create_account.repository

import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.create_account.model.SignupResponse
import com.app.cheffyuser.networking.remote.ApiService
import com.app.cheffyuser.networking.remote.Resource
import com.app.cheffyuser.networking.remote.ResponseHandler
import com.app.cheffyuser.networking.remote.RetrofitBuilder
import timber.log.Timber

class AuthRepository {

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

    suspend fun createUserAccounts(signupRequest: SignupRequest) =
        apiService.createUserAccount(signupRequest)

    suspend fun createUserAccount(signupRequest: SignupRequest): Resource<SignupResponse> {
        return try {
            val response = apiService.createUserAccount(signupRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e,"")
            responseHandler.handleException(e)
        }
    }


}
