package com.app.cheffyuser.networking.remote;


import com.app.cheffyuser.networking.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * @deprecated
 * use {@link #RetrofitBuilder}
 *
 * TODO: delete after refactoring functionality to new class
 */
@Deprecated
public class ApiClient {

    private static final String BASE_URL = Constant.BASE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

}