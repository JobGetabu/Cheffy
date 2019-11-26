package com.app.cheffyuser.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.create_account.model.ProfileResponse
import com.app.cheffyuser.create_account.model.ShippingRequest
import com.app.cheffyuser.create_account.model.ShippingResponse
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.model.FoodNearByModel
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.repository.HomeRepository
import com.app.cheffyuser.networking.Resource
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import java.util.*

class HomeViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()

    var mCurrentLocation: MediatorLiveData<LatLng?> = MediatorLiveData()
    var mCurrentLongtitide: MediatorLiveData<Double?> = MediatorLiveData()
    var mCurrentLatitude: MediatorLiveData<Double?> = MediatorLiveData()
    var mAddressText: MediatorLiveData<String?> = MediatorLiveData()

    fun fetchNearByFood(
        lat: String = "-5.03284353",
        lon: String = "-42.8176576",
        radius: String = "10"
    ): LiveData<Resource<MutableList<FoodNearByModel>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchNearByFood(lat, lon, radius)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFoodPopular(): LiveData<Resource<MutableList<PlatesResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodPopular()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFoodNewest(): LiveData<Resource<MutableList<PlatesResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodNewest()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchRelatedFood(foodId: Int): LiveData<Resource<MutableList<PlatesResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchRelatedFood(foodId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFoodNearbyLocation(
        lat: String = "-5.03284353",
        lon: String = "-42.8176576",
        radius: String = "10"
    ): LiveData<Resource<MutableList<PlatesResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodNearbyLocation(lat, lon, radius)
            emit(Resource.loading(null))
            emit(data)
        }
    }


    fun fetchFoodCategory(): LiveData<Resource<MutableList<FoodCatModel>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodCategory()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchUser(): LiveData<Resource<ProfileResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchUser()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchShipping(): LiveData<Resource<List<ShippingResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchShipping()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun setShipping(shippingRequest: ShippingRequest): LiveData<Resource<ShippingResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.setShipping(shippingRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    //region foodDetails data



    var platesResponse: MediatorLiveData<PlatesResponse?> = MediatorLiveData()


    //endregion


    //used in CustomOrder upload screen
    var imagesUrls: ArrayList<String>? = arrayListOf()

}