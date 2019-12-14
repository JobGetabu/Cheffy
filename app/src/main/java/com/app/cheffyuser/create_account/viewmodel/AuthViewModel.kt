package com.app.cheffyuser.create_account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.create_account.repository.AuthRepository
import com.app.cheffyuser.networking.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel : ViewModel() {
    val repository: AuthRepository = AuthRepository()

    fun createAccount(signupRequest: SignupRequest): LiveData<Resource<SignupResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.createUserAccount(signupRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun verifyAccount(verifyRequest: VerifyRequest): LiveData<Resource<VerifyResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.verifyAccount(verifyRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun registerAccount(registrationRequest: RegistrationRequest): LiveData<Resource<RegistrationResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.registerAccount(registrationRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun loginUser(loginRequest: LoginRequest): LiveData<Resource<LoginResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.loginUserAccount(loginRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun forgotPassword(forgotRequest: ForgotRequest): LiveData<Resource<ForgotResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.forgotPassword(forgotRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }


}