package com.app.cheffyuser.utils

import android.content.SharedPreferences
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.create_account.model.*
import com.app.cheffyuser.home.model.CurrentLocation
import com.app.cheffyuser.utils.Constants.PREF_ACCESS_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_APP_VERSION
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_ADDRESS
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LAT
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LOCATION
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LON
import com.app.cheffyuser.utils.Constants.PREF_FIREBASE_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_FULLUSER_DATA_CLASS
import com.app.cheffyuser.utils.Constants.PREF_LAST_ADDRESS
import com.app.cheffyuser.utils.Constants.PREF_LAST_LAT
import com.app.cheffyuser.utils.Constants.PREF_LAST_LOCATION
import com.app.cheffyuser.utils.Constants.PREF_LAST_LON
import com.app.cheffyuser.utils.Constants.PREF_LAUNCH_TIMES
import com.app.cheffyuser.utils.Constants.PREF_PHONE_NUMBER
import com.app.cheffyuser.utils.Constants.PREF_REFRESH_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_USER_DATA_CLASS
import com.app.cheffyuser.utils.Constants.PREF_USER_EMAIL
import com.app.cheffyuser.utils.Constants.PREF_USER_ISLOGIN
import com.app.cheffyuser.utils.Constants.PREF_USER_SHIPPINGREQ_CLASS
import com.app.cheffyuser.utils.Constants.PREF_USER_SHIPPING_CLASS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Global app preference helper for the whole application.
 * singleton
 *
 * Will manage user preferences
 * login Access tokens and sessions
 */
class TokenManager(private val prefs: SharedPreferences) {

    private val editor: SharedPreferences.Editor = prefs.edit()
    private val gson: Gson = Gson()

    val token: AccessToken
        get() {
            val accessToken = AccessToken()
            accessToken.accessToken = prefs.getString(PREF_ACCESS_TOKEN, null)
            accessToken.refreshToken = prefs.getString(PREF_REFRESH_TOKEN, null)

            return accessToken
        }

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(PREF_USER_ISLOGIN, false)

    var user: LoginResponse?
        get() {
            val userDataString = prefs.getString(PREF_USER_DATA_CLASS, null)
            val type = object : TypeToken<LoginResponse>() {}.type
            return gson.fromJson(userDataString, type)
        }
        set(user) {
            val userDataString = gson.toJson(user)
            editor.putString(PREF_USER_DATA_CLASS, userDataString).apply()

            val accessToken = AccessToken()
            accessToken.accessToken = user?.token
            accessToken.refreshToken = user?.token
            saveToken(accessToken)
        }

    var shippingData: ShippingData?
        get() {
            val shippingString = prefs.getString(PREF_USER_SHIPPING_CLASS, null)
            val type = object : TypeToken<ShippingData>() {}.type
            return gson.fromJson(shippingString, type)
        }
        set(shippingData) {
            val shippingString = gson.toJson(shippingData)
            editor.putString(PREF_USER_SHIPPING_CLASS, shippingString).apply()
        }

    var shippingData2: ShippingRequest?
        get() {
            val shippingString = prefs.getString(PREF_USER_SHIPPINGREQ_CLASS, null)
            val type = object : TypeToken<ShippingRequest>() {}.type
            return gson.fromJson(shippingString, type)
        }
        set(shippingData2) {
            val shippingString = gson.toJson(shippingData2)
            editor.putString(PREF_USER_SHIPPINGREQ_CLASS, shippingString).apply()
        }

    var userData: UserData?
        get() {
            val userString = prefs.getString(PREF_FULLUSER_DATA_CLASS, null)
            val type = object : TypeToken<UserData>() {}.type
            return gson.fromJson(userString, type)
        }
        set(userData) {
            val shippingString = gson.toJson(userData)
            editor.putString(PREF_FULLUSER_DATA_CLASS, shippingString).apply()
        }


    var email: String?
        get() = prefs.getString(PREF_USER_EMAIL, null)
        set(email) = editor.putString(PREF_USER_EMAIL, email).apply()

    var phone: String?
        get() = prefs.getString(PREF_PHONE_NUMBER, null)
        set(phone) = editor.putString(PREF_PHONE_NUMBER, phone).apply()

    val firebaseToken: String?
        get() = prefs.getString(PREF_FIREBASE_TOKEN, null)


    fun saveToken(token: AccessToken) {
        //refresh editor risk garbage collected
        val editor = prefs.edit()
        editor.putString(PREF_ACCESS_TOKEN, token.accessToken)
        editor.putString(PREF_REFRESH_TOKEN, token.accessToken)

        setIsLoggedIn()
        editor.apply()
    }

    fun saveLastKnownLocation(address: String, lat: Double?, lon: Double?) {
        editor.putBoolean(PREF_LAST_LOCATION, true)
        editor.putString(PREF_LAST_ADDRESS, address)
        editor.putString(PREF_LAST_LAT, lat!!.toString())
        editor.putString(PREF_LAST_LON, lon!!.toString())
        editor.apply()
    }

    fun saveCurrentLocation(address: String, lat: Double?, lon: Double?) {
        editor.putBoolean(PREF_CURRENT_LOCATION, true)
        editor.putString(PREF_CURRENT_ADDRESS, address)
        editor.putString(PREF_CURRENT_LAT, lat!!.toString())
        editor.putString(PREF_CURRENT_LON, lon!!.toString())
        editor.apply()
    }

    var mCurrentLocation: CurrentLocation
        get() = CurrentLocation(
            prefs.getString(PREF_CURRENT_ADDRESS, "Unnamed Road"),
            prefs.getString(PREF_CURRENT_LAT, "0")?.toDouble(),
            prefs.getString(PREF_CURRENT_LON, "0")?.toDouble()
        )
        set(mCurrentLocation) {
            editor.putBoolean(PREF_CURRENT_LOCATION, true)
            editor.putString(PREF_CURRENT_ADDRESS, mCurrentLocation.address)
            editor.putString(PREF_CURRENT_LAT, mCurrentLocation.lat!!.toString())
            editor.putString(PREF_CURRENT_LON, mCurrentLocation.lon!!.toString())
            editor.apply()
        }

    var launchTimes: Int
        get() {
            return prefs.getInt(PREF_LAUNCH_TIMES, 0)
        }
        set(launchTimes) {
            editor.putInt(PREF_LAUNCH_TIMES, launchTimes)
            editor.apply()
        }

    fun deleteToken() {
        //refresh editor risk garbage collected
        val editor = prefs.edit()
        editor.putString(PREF_ACCESS_TOKEN, null)
        editor.putString(PREF_REFRESH_TOKEN, null)
        editor.commit()
        editor.apply()
    }

    fun setIsLoggedIn() {
        //refresh editor risk garbage collected
        val editor = prefs.edit()

        if (this.token.accessToken == null) {
            editor.putBoolean(PREF_USER_ISLOGIN, false)
        } else {
            editor.putBoolean(PREF_USER_ISLOGIN, true)
        }
        editor.apply()
    }

    fun deleteIsLoggedIn() {
        editor.putBoolean(PREF_USER_ISLOGIN, false).apply()
    }


    /**
     * TODO: Check if app was updated
     */
    var appVersion: String
        get() = "${BuildConfig.VERSION_CODE}"
        set(appVersion) {
            editor.putString(PREF_APP_VERSION, appVersion)
            editor.apply()
        }


    fun setFireBaseToken(tk: String) {
        editor.putString(PREF_FIREBASE_TOKEN, tk).apply()
    }

    fun DELETEALLPREFS() {
        editor.clear().apply()
    }

    companion object {
        private var INSTANCE: TokenManager? = null

        @Synchronized
        fun getInstance(prefs: SharedPreferences): TokenManager {
            if (INSTANCE == null) {
                INSTANCE = TokenManager(prefs)
            }

            return INSTANCE as TokenManager
        }
    }
}
