package com.app.cheffyuser.networking.remote;


import com.app.cheffyuser.create_account.model.LoginData;
import com.app.cheffyuser.food_category.model.FoodCategoryModel;
import com.app.cheffyuser.home.model.FoodNearByModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/*
* @deprecated
* use {@link ApiService}
*
* TODO: delete after refactoring functionality to new interface
 */
@Deprecated
public interface ApiInterface {

//    @GET("plate")
//    Call<List<Food_Menu_Model>> platesList();




    @POST("/user/login")
    public Call<LoginData> userLogin(
           @Body LoginData loginData);




    @GET("/category")
    Call<List<FoodCategoryModel>> categoryList();


//    @GET("/plate/near?latitude=-5.0328144&longitude=-42.8150343&radius=10")
//    Call<List<FoodNearByModel>> foodNearBy();

   // @GET("/plate/near?latitude={lat}&longitude={lon}&radius={radius}")
    @GET("/plate/near")
    Call <List<FoodNearByModel>> getFoodNearBy(@Query("latitude") String lat,
                                               @Query("longitude") String lon,
                                               @Query("radius") String radius
                                                  );



}