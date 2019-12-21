package com.app.cheffyuser.create_account.social

import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.create_account.model.SocialLoginRequest
import com.app.cheffyuser.create_account.model.SocialRegRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.networking.Status
import com.facebook.*
import com.facebook.AccessToken.getCurrentAccessToken
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONException
import retrofit2.Call
import timber.log.Timber


class FacebookManager(
    internal var vm: AuthViewModel,
    internal var context: BaseFragment,
    internal val isRegistration: Boolean = false
) {

    companion object {
        private val PROVIDER = "facebook"
    }

    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var accessTokenTracker: AccessTokenTracker?
    private var listener: FacebookLoginListener? = null

    //TODO: Add fbLogin API call
    private var call: Call<Any>? = null

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            Timber.d("fb token loginResult: ${loginResult.accessToken}")
            fetchUser(loginResult.accessToken)
        }

        override fun onCancel() {
            Timber.d("fb cancelled: ")
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
        FacebookSdk.sdkInitialize(getApplicationContext())
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                Timber.d("set fb token $currentAccessToken")
                AccessToken.setCurrentAccessToken(currentAccessToken)
            }

        }
    }

    fun login(activity: BaseFragment, listener: FacebookLoginListener) {

        this.listener = listener

        val accessToken = getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired


        if (isLoggedIn) {
            //Get the user
            Timber.d("fb token: ${getCurrentAccessToken()}")
            fetchUser(getCurrentAccessToken())
        } else {
            Timber.d("fb token not there: ${getCurrentAccessToken()}")

            LoginManager.getInstance()
                .logInWithReadPermissions(activity, listOf("public_profile", "email"))

            LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)

        }


    }

    private fun fetchUser(accessToken: AccessToken) {

        val request = GraphRequest.newMeRequest(accessToken) { body, response ->


            Timber.d("fb res: ${response.rawResponse}")

            try {
                val id = body.getString("id")
                val firstName = body.getString("first_name")
                val lastName = body.getString("last_name")
                var email: String? = null
                if (body.has("email")) {
                    email = body.getString("email")
                } else {
                    email =
                        firstName.toLowerCase() + '.'.toString() + lastName.toLowerCase() + "@facebook.com"
                }
                val photoUrl = "https://graph.facebook.com/$id/picture?type=large"

                if (isRegistration) {
                    getRegistration(firstName, lastName, email, PROVIDER, id, photoUrl)
                } else {
                    getTokenFromBackend(firstName, lastName, email, PROVIDER, id, photoUrl)
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                Timber.d("fb error: ${e.message}")
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

        Timber.d(" social $firstName $provider")
        val soc = SocialLoginRequest(email, provider, providerUserId)

        vm.socialLogin(soc).observe(context, androidx.lifecycle.Observer {
            when (it.status) {
                Status.ERROR -> {
                    listener!!.onError("${it.message}")
                }
                Status.SUCCESS -> {
                    val dt = it.data
                    val tk = CheffyApp.instance!!.tokenManager

                    tk.user = dt
                    tk.setIsLoggedIn()

                    listener!!.onSuccess()
                }
            }
        })
    }

    /*
   * This should be an API call to save user returned by fb to our backend
   * Retrieve Token
   *
   * */
    private fun getRegistration(
        firstName: String,
        lastName: String,
        email: String?,
        provider: String,
        providerUserId: String,
        profile_image: String
    ) {

        Timber.d("social registration$firstName $provider")

        Timber.d(" social $firstName $provider")
        val soc = SocialRegRequest(
            email,
            profile_image,
            "$firstName $lastName",
            provider,
            providerUserId,
            user_type = "user"
        )

        vm.socialRegistration(soc).observe(context, androidx.lifecycle.Observer {
            when (it.status) {
                Status.ERROR -> {
                    listener!!.onError("${it.message}")
                }
                Status.SUCCESS -> {
                    val dt = it.data
                    val tk = CheffyApp.instance!!.tokenManager

                    tk.user = dt
                    tk.setIsLoggedIn()

                    listener!!.onSuccess()
                }
            }
        })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun onDestroy() {
        if (call != null) {
            call!!.cancel()
        }

        call = null
        accessTokenTracker?.stopTracking()

        //LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    fun clearSession() {
        LoginManager.getInstance().logOut()
    }

}