package com.app.cheffyuser.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.cheffyuser.cart.models.*
import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.food_category.model.FoodCategoryResponse
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.home.repository.HomeRepository
import com.app.cheffyuser.networking.Resource
import com.app.cheffyuser.profile.model.ChefResponse
import com.app.cheffyuser.profile.model.ProfPicResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody

class HomeViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()
    var isFirstLaunch = 1

    var mCurrentLocation: MediatorLiveData<LatLng?> = MediatorLiveData()
    var mCurrentLongtitide: MediatorLiveData<Double?> = MediatorLiveData()
    var mCurrentLatitude: MediatorLiveData<Double?> = MediatorLiveData()
    var mAddressText: MediatorLiveData<String?> = MediatorLiveData()

    var shippingData: MediatorLiveData<ShippingData> = MediatorLiveData()

    var pagerCurrentItem: MediatorLiveData<Int> = MediatorLiveData()

    var searchTerm: MediatorLiveData<String> = MediatorLiveData()
    var searchPredictions: MediatorLiveData<MutableList<String>> = MediatorLiveData()

    var predictionsResponse = PredictionsResponse()
    var searchResult = SearchResult()

    var filterObj: MediatorLiveData<String> = MediatorLiveData()
    var selectedSortFilter: MediatorLiveData<Int> = MediatorLiveData()
    var selectedPriceFilter: MediatorLiveData<MutableList<Boolean>> = MediatorLiveData()

    var goToLoginTrigger: MediatorLiveData<Boolean> = MediatorLiveData()


    fun editUserAccount(editProfileRequest: EditProfileRequest): LiveData<Resource<EditProfileResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.editUserAccount(editProfileRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun logout(): LiveData<Resource<LogoutResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.logout()
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

    fun fetchFoodPopular(): LiveData<Resource<GetPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodPopular()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFoodNewest(): LiveData<Resource<GetPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodNewest()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchRelatedFood(foodId: Int): LiveData<Resource<GetPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchRelatedFood(foodId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getPlatesByCategory(categoryId: Int): LiveData<Resource<GetPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getPlatesByCategory(categoryId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun fetchFoodNearbyLocation(
        lat: String = "-5.0323423",
        lon: String = "-42.8150343",
        radius: String = "10"
    ): LiveData<Resource<GetPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchFoodNearbyLocation(lat, lon, radius)
            emit(Resource.loading(null))
            emit(data)
        }
    }


    fun fetchFoodCategory(): LiveData<Resource<FoodCategoryResponse>> {
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

    fun fetchShipping(): LiveData<Resource<ShippingAddressResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.fetchShipping()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun setShipping(shippingRequest: ShippingRequest): LiveData<Resource<SetShippingResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.setShipping(shippingRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }


    fun getChefData(chefId: Int): LiveData<Resource<ChefResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getChef(chefId)
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

    fun addItemby1(basketId: Int): LiveData<Resource<BasketListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.addItemby1(basketId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun subtractItemby1(basketId: Int): LiveData<Resource<BasketListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.subtractItemby1(basketId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun deleteItem(basketId: Int): LiveData<Resource<BasketListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.deleteItem(basketId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun addToBasket(addToBasketRequest: AddToBasketRequest): LiveData<Resource<BasketListResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.addToBasket(addToBasketRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getPeopleAlsoAdded(plateId: Int): LiveData<Resource<List<PeopleAddedResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getPeopleAlsoAdded(plateId)
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

    fun getSearchPredictions(): LiveData<Resource<PredictionsResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getSearchPredictions()
            emit(Resource.loading(null))
            emit(data)
        }
    }


    //Region custom plates

    fun uploadCustomPlateImages(
        customPlateId: Int,
        filesToUpload: MutableList<MultipartBody.Part>
    ): LiveData<Resource<UploadCustomImagesResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.uploadCustomPlateImages(customPlateId, filesToUpload)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun createCustomPlate(createCustomRequest: CreateCustomRequest): LiveData<Resource<CreateCustomResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.createCustomPlate(createCustomRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getCustomPlates(): LiveData<Resource<CustomPlateResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getCustomPlates()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getCustomPlate(customPlateId: Int): LiveData<Resource<CustomPlateResponseData>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getCustomPlate(customPlateId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun acceptBid(bidId: Int): LiveData<Resource<BidAcceptanceResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.acceptBid(bidId)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    //endregion

    //cards

    fun addCreditCard(cardRequest: CreditCardRequest): LiveData<Resource<CreditCardResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.addCreditCard(cardRequest)
            emit(Resource.loading(null))
            emit(data)
        }
    }

    fun getCreditCards(): LiveData<Resource<MutableList<CreditCardResponse>>> {
        return liveData(Dispatchers.IO) {
            val data = repository.getCreditCard()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    //order
    fun makeOrder(orderRequest: OrderRequest): LiveData<Resource<OrderResponse>> {
        return liveData(Dispatchers.IO) {
            val data = repository.makeOrder(orderRequest)
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