package com.app.cheffyuser.home.repository

import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.cart.models.*
import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.food_category.model.FoodCategoryResponse
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.networking.ApiService
import com.app.cheffyuser.networking.Resource
import com.app.cheffyuser.networking.ResponseHandler
import com.app.cheffyuser.networking.RetrofitBuilder
import com.app.cheffyuser.profile.model.ChefResponse
import com.app.cheffyuser.profile.model.ProfPicResponse
import okhttp3.MultipartBody
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

    suspend fun editUserAccount(editProfileRequest: EditProfileRequest): Resource<EditProfileResponse> {
        return try {
            val response = apiServiceAuthed.editUserAccount(editProfileRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun logout(): Resource<LogoutResponse> {
        return try {
            val response = apiServiceAuthed.logout()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getPlate(plateId: Int): Resource<SinglePlatesResponse> {
        return try {
            val response = apiService.getPlate(plateId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchNearByFood(
        lat: String,
        lon: String,
        radius: String
    ): Resource<MutableList<FoodNearByModel>> {
        return try {
            val response = apiService.getFoodNearBy(lat, lon, radius)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodPopular(): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodPopular()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodNewest(): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodNewest()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchRelatedFood(foodId: Int): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getRelatedFood(foodId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getPlatesByCategory(categoryId: Int): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getPlatesByCategory(categoryId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }


    suspend fun fetchFoodNearbyLocation(
        lat: String,
        lon: String,
        radiusMiles: String
    ): Resource<MutableList<PlatesResponse>> {
        return try {
            val response = apiService.getFoodNearbyLocation(lat, lon, radiusMiles)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchFoodCategory(): Resource<FoodCategoryResponse> {
        return try {
            val response = apiService.getFoodCategory()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchUser(): Resource<ProfileResponse> {
        return try {
            val response = apiServiceAuthed.getUser()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun fetchShipping(): Resource<List<ShippingDataResponse>> {
        return try {
            val response = apiServiceAuthed.getShipping()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun setShipping(shippingRequest: ShippingRequest): Resource<ShippingResponse> {
        return try {
            val response = apiServiceAuthed.setShipping(shippingRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun addFavourite(favouriteRequest: FavouriteRequest): Resource<FavouriteResponse> {
        return try {
            val response = apiServiceAuthed.addFavourite(favouriteRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun removeFavourite(favR: FavouriteRequest): Resource<FavouriteDeleteResponse> {
        return try {
            val response = apiServiceAuthed.removeFavourite(favR.fav_type!!, favR.plateId!!)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getFavourite(): Resource<FavouriteListResponse> {
        return try {
            val response = apiServiceAuthed.getFavourite()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    //chef

    suspend fun getChef(chefId: Int): Resource<ChefResponse> {
        return try {
            val response = apiService.getChefData(chefId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    //endregion

    //Region Basket

    suspend fun addToBasket(addToBasketRequest: AddToBasketRequest): Resource<BasketListResponse> {
        return try {
            val response = apiServiceAuthed.addToBasket(addToBasketRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getBasket(): Resource<BasketListResponse> {
        return try {
            val response = apiServiceAuthed.getBasket()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getPeopleAlsoAdded(plateId: Int): Resource<List<PeopleAddedResponse>> {
        return try {
            val response = apiService.getPeopleAlsoAdded(plateId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }


    //end Region

    suspend fun profileUpload(fileToUpload: MultipartBody.Part): Resource<ProfPicResponse> {
        return try {
            val response = apiServiceAuthed.uploadProfileImage(fileToUpload)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getSearchPredictions(): Resource<PredictionsResponse> {
        return try {
            val response = apiService.getSearchPredictions()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    //Region custom plates

    suspend fun uploadCustomPlateImages(
        customPlateId: Int,
        filesToUpload: MutableList<MultipartBody.Part>
    ): Resource<UploadCustomImagesResponse> {
        return try {
            val response = apiServiceAuthed.uploadCustomPlateImages(customPlateId, filesToUpload)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun createCustomPlate(createCustomRequest: CreateCustomRequest): Resource<CreateCustomResponse> {
        return try {
            val response = apiServiceAuthed.createCustomPlate(createCustomRequest)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getCustomPlates(): Resource<CustomPlateResponse> {
        return try {
            val response = apiServiceAuthed.getCustomPlates()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun getCustomPlate(customPlateId: Int): Resource<CustomPlateResponseData> {
        return try {
            val response = apiServiceAuthed.getCustomPlate(customPlateId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    suspend fun acceptBid(bidId: Int): Resource<BidAcceptanceResponse> {
        return try {
            val response = apiServiceAuthed.acceptBid(bidId)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            //just extra logging
            Timber.tag("HTTP").e(e, "")
            responseHandler.handleException(e)
        }
    }

    //endregion
}
