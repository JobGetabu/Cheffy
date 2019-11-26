package com.app.cheffyuser.networking


import com.app.cheffyuser.utils.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

import java.io.IOException

/**
 * Cheffy Apis don't currently support Refreshing token
 * for now
 *
 */
class CustomAuthenticator private constructor(private val tokenManager: TokenManager) :
    Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) {
            return null
        }

        val accessToken = tokenManager.token

        val service =
            RetrofitBuilder.createService(ApiService::class.java)
        val call = service.refresh(accessToken.refreshToken!! + "a")
        val res = call.execute()

        if (res.isSuccessful) {
            val newAccessToken = res.body()
            tokenManager.saveToken(newAccessToken!!)

            return response.request().newBuilder()
                .header("x-access-token", res.body()!!.accessToken!!)
                .build()
        } else {
            return null
        }
    }

    private fun responseCount(response: Response?): Int {
        var response = response
        var result = 1
        while ((response!!.priorResponse()) != null) {
            result++
        }

        return result
    }

    companion object {
        private var INSTANCE: CustomAuthenticator? = null

        @Synchronized
        fun getInstance(tokenManager: TokenManager): CustomAuthenticator {
            if (INSTANCE == null) {
                INSTANCE =
                    CustomAuthenticator(tokenManager)
            }

            return INSTANCE as CustomAuthenticator
        }
    }
}
