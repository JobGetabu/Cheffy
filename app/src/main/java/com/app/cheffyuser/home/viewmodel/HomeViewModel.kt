package com.app.cheffyuser.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.home.model.FoodNearByModel
import com.app.cheffyuser.home.repository.HomeRepository
import com.app.cheffyuser.networking.remote.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()

    fun fetchNearByFood(
        lat: String = "-5.03284353",
        lon: String = "-42.8176576",
        radius: String = "10"
    ): LiveData<Resource<MutableList<FoodNearByModel>>>{
        return liveData(Dispatchers.IO){
            val data = repository.fetchNearByFood(lat,lon,radius)
            emit(Resource.loading(null))
            emit(data)
        }
    }
}