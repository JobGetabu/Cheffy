package com.app.cheffyuser.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.home.repository.HomeRepository
import com.app.cheffyuser.networking.Resource
import com.app.cheffyuser.profile.model.ProfPicResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import java.util.*

class HomeViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()
    var isFirstLaunch = 1

    var mCurrentLocation: MediatorLiveData<LatLng?> = MediatorLiveData()
    var mCurrentLongtitide: MediatorLiveData<Double?> = MediatorLiveData()
    var mCurrentLatitude: MediatorLiveData<Double?> = MediatorLiveData()
    var mAddressText: MediatorLiveData<String?> = MediatorLiveData()

    var shippingData: MediatorLiveData<ShippingDataResponse> = MediatorLiveData()

    var pagerCurrentItem: MediatorLiveData<Int> = MediatorLiveData()
    var searchTerm: MediatorLiveData<String> = MediatorLiveData()

    var filterObj: MediatorLiveData<String> = MediatorLiveData()
    var selectedSortFilter: MediatorLiveData<Int> = MediatorLiveData()
    var selectedPriceFilter: MediatorLiveData<MutableList<Boolean>> = MediatorLiveData()


    fun editUserAccount(editProfileRequest: EditProfileRequest): LiveData<Resource<EditProfileResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.editUserAccount(editProfileRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getPlate(plateId: Int): LiveData<Resource<SinglePlatesResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getPlate(plateId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchNearByFood(
        lat: String = "38.81212000",
        lon: String = "-76.96691110",
        radius: String = "100"
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
        radius: String = "100"
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

    fun fetchShipping(): LiveData<Resource<List<ShippingDataResponse>>> {
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

    fun getBasket(): LiveData<Resource<BasketListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getBasket()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun addToBasket(addToBasketRequest: AddToBasketRequest): LiveData<Resource<List<AddToBasketResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.addToBasket(addToBasketRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun uploadProfile(fileToUpload: MultipartBody.Part): LiveData<Resource<ProfPicResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.profileUpload(fileToUpload)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun addFavourite(favouriteRequest: FavouriteRequest): LiveData<Resource<FavouriteResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.addFavourite(favouriteRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun removeFavourite(favouriteRequest: FavouriteRequest): LiveData<Resource<FavouriteDeleteResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.removeFavourite(favouriteRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFavourite(): LiveData<Resource<FavouriteListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getFavourite()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    //region foodDetails data

    var platesResponse: MediatorLiveData<PlatesResponse?> = MediatorLiveData()

    //endregion


    //used in CustomOrder upload screen
    var imagesUrls: ArrayList<String>? = arrayListOf()

    //fragment bundles
    var isForNet: Boolean = false
    var tt: String = "Can't connect"
    var desc: String = "Check your internet connection and try again"
    var actionTxt: String = "Try Again"

}