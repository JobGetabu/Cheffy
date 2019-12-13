package com.app.cheffyuser.networking

import android.widget.Toast
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.utils.AppExecutors
import com.app.cheffyuser.utils.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private val BASE_URL = "https://mycheffy.herokuapp.com/"

    private val client = buildClient()
    val retrofit =
        buildRetrofit(client)

    private fun buildClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                var request = chain.request()

                val builder1 = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Connection", "close")

                request = builder1.build()
                //the response
                val response = chain.proceed(request)


                val appExecutors = AppExecutors()
                appExecutors.mainThread().execute {
                    ///catch unauthenticated users
                    if (response.code() == 401) {
                        //Intercept any unauthenticated request in app

                        val tm = CheffyApp.instance!!.tokenManager
                        tm.deleteIsLoggedIn()
                        tm.deleteToken()

                        if (BuildConfig.DEBUG)
                            Toast.makeText(
                                CheffyApp.instance!!,
                                "Debug only: Not authenticated",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }

                response
            }
            .addInterceptor(interceptor)

        return builder.build()
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun <T> createServiceWithAuth(service: Class<T>, tokenManager: TokenManager): T {

        val newClient = client.newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor { chain ->

                var request = chain.request()

                val builder = request.newBuilder()

                if (tokenManager.token.accessToken != null) {
                    val token = tokenManager.token.accessToken
                    Timber.d(tokenManager.token.accessToken)
                    builder.addHeader("Content-Type", "application/json")
                    if (!token.isNullOrEmpty())
                        builder.addHeader("x-access-token", "$token")
                }
                request = builder.build()
                chain.proceed(request)
            }.build()

        val newRetrofit = retrofit.newBuilder().client(newClient).build()
        return newRetrofit.create(service)

    }
}
