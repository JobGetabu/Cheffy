package com.app.cheffyuser.networking

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
                        //TODO. Intercept any unauthenticated request in app
                        //Force to sign in again
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

        val newClient = client.newBuilder().addInterceptor { chain ->

            var request = chain.request()

            val builder = request.newBuilder()

            if (tokenManager.token.accessToken != null) {
                val token = tokenManager.token.accessToken
                Timber.d(tokenManager.token.accessToken)
                builder.addHeader("Content-Type", "application/json")
                       .addHeader("x-access-token", "$token")
            }
            request = builder.build()
            chain.proceed(request)
        }.authenticator(
            CustomAuthenticator.getInstance(
                tokenManager
            )
        ).build()

        val newRetrofit = retrofit.newBuilder().client(newClient).build()
        return newRetrofit.create(service)

    }
}
