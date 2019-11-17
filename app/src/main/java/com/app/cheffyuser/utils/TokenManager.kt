package com.app.cheffyuser.utils

import android.content.SharedPreferences
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.create_account.model.AccessToken
import com.app.cheffyuser.home.model.CurrentLocation
import com.app.cheffyuser.utils.Constants.PREF_ACCESS_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_APP_VERSION
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_ADDRESS
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LAT
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LOCATION
import com.app.cheffyuser.utils.Constants.PREF_CURRENT_LON
import com.app.cheffyuser.utils.Constants.PREF_FIREBASE_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_LAST_ADDRESS
import com.app.cheffyuser.utils.Constants.PREF_LAST_LAT
import com.app.cheffyuser.utils.Constants.PREF_LAST_LOCATION
import com.app.cheffyuser.utils.Constants.PREF_LAST_LON
import com.app.cheffyuser.utils.Constants.PREF_LAUNCH_TIMES
import com.app.cheffyuser.utils.Constants.PREF_PHONE_NUMBER
import com.app.cheffyuser.utils.Constants.PREF_REFRESH_TOKEN
import com.app.cheffyuser.utils.Constants.PREF_USER_EMAIL
import com.app.cheffyuser.utils.Constants.PREF_USER_ISLOGIN


/**
 * Global app preference helper for the whole application.
 * singleton
 *
 * Will manage user preferences
 * login Access tokens and sessions
 */
class TokenManager(private val prefs: SharedPreferences) {

    private val editor: SharedPreferences.Editor = prefs.edit()

    val token: AccessToken
        get() {
            val accessToken = AccessToken()
            accessToken.accessToken = prefs.getString(PREF_ACCESS_TOKEN, null)
            accessToken.refreshToken = prefs.getString(PREF_REFRESH_TOKEN, null)

            return accessToken
        }

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(PREF_USER_ISLOGIN, false)


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
