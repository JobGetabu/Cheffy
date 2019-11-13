package com.app.cheffyuser.create_account.repository

import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.networking.remote.ApiService
import com.app.cheffyuser.networking.remote.RetrofitBuilder

class AuthRepository {

    private val apiService: ApiService
    private val apiServiceAuthed: ApiService

    init {

        val tokenManager = CheffyApp.instance!!.tokenManager
        apiService = RetrofitBuilder.createService(ApiService::class.java)
        apiServiceAuthed =
            RetrofitBuilder.createServiceWithAuth(ApiService::class.java, tokenManager)
    }

    suspend fun createUserAccount(signupRequest: SignupRequest) =
        apiService.createUserAccount(signupRequest)


}
