package com.app.cheffyuser.create_account.social

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.networking.remote.ApiService
import com.app.cheffyuser.utils.TokenManager
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONException
import retrofit2.Call
import java.util.*

class FacebookManager(
    internal var context: Context,
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    private val callbackManager: CallbackManager?
    private var listener: FacebookLoginListener? = null

    //TODO: Add fbLogin API call
    private var call: Call<Any>? = null

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            fetchUser(loginResult.accessToken)
        }

        override fun onCancel() {

            //TODO: dismiss job + dialogues
        }

        override fun onError(error: FacebookException) {
            listener!!.onError(error.message!!)
        }
    }

    interface FacebookLoginListener {
        fun onSuccess()
        fun onError(message: String)
    }

    init {
        this.callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager!!, facebookCallback)
    }

    /*
     * TODO: to fetch and save user details in the app
     * */
    fun fetchUser() {}

    fun login(activity: Activity, listener: FacebookLoginListener) {
        this.listener = listener

        if (AccessToken.getCurrentAccessToken() != null) {
            //Get the user
            fetchUser(AccessToken.getCurrentAccessToken())
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
        }

    }

    private fun fetchUser(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(accessToken) { body, response ->
            try {
                val id = body.getString("id")
                val firstName = body.getString("first_name")
                val lastName = body.getString("last_name")
                var email: String? = null
                if (body.has("email")) {
                    email = body.getString("email")
                } else {
                    email = firstName.toLowerCase() + '.'.toString() + lastName.toLowerCase() + "@facebook.com"
                }
                val photoUrl = "https://graph.facebook.com/$id/picture?type=large"

                //TODO: save to SharedPreferences
                //tokenManager => save user data

                getTokenFromBackend(firstName, lastName, email, PROVIDER, id, photoUrl)
            } catch (e: JSONException) {
                e.printStackTrace()
                listener!!.onError(e.message!!)
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id, name, first_name, last_name, email")
        request.parameters = parameters
        request.executeAsync()
    }

    /*
    * This should be an API call to save user returned by fb to our backend
    * Retrieve Token
    *
    * */
    private fun getTokenFromBackend(
        firstName: String,
        lastName: String,
        email: String?,
        provider: String,
        providerUserId: String,
        profile_image: String
    ) {

        //TODO: Make a social login call to backend to retrieve a token
        // activate out listeners here
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    fun onDestroy() {
        if (call != null) {
            call!!.cancel()
        }
        call = null
        if (callbackManager != null) {
            LoginManager.getInstance().unregisterCallback(callbackManager)
        }
    }

    fun clearSession() {
        LoginManager.getInstance().logOut()
    }

    companion object {
        private val TAG = "FacebookManager"
        private val PROVIDER = "facebook"
    }
}