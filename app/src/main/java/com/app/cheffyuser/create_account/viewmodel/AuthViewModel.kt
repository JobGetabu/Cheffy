package com.app.cheffyuser.create_account.viewmodel

import androidx.lifecycle.ViewModel
import com.app.cheffyuser.create_account.repository.AuthRepository

class AuthViewModel : ViewModel() {
    val repository: AuthRepository = AuthRepository()

    //sample get data with new coroutines with retrofit 2.6.0
    /*
    val firstTodo = liveData(Dispatchers.IO) {
        val retrivedTodo = repository.getTodo(1)

        emit(retrivedTodo)
    }
     */
}