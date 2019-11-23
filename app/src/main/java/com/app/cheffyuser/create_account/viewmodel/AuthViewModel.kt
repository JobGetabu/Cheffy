package com.app.cheffyuser.create_account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.create_account.model.SignupResponse
import com.app.cheffyuser.create_account.repository.AuthRepository
import com.app.cheffyuser.networking.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel : ViewModel() {
    val repository: AuthRepository = AuthRepository()

    fun createAccount(signupRequest: SignupRequest): LiveData<Resource<SignupResponse>>{
        return liveData(Dispatchers.IO) {
            val data = repository.createUserAccount(signupRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }
}