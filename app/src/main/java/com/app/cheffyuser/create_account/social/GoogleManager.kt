package com.app.cheffyuser.create_account.social

import androidx.fragment.app.FragmentActivity
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.create_account.model.SocialLoginRequest
import com.app.cheffyuser.create_account.model.SocialRegRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.networking.Status
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import timber.log.Timber

class GoogleManager(
    internal var vm: AuthViewModel,
    internal var context: FragmentActivity,
    internal val isRegistration: Boolean = false,
    account: GoogleSignInAccount,
    private val listener: GoogleLoginListener
) {

    companion object {
        private val PROVIDER = "google"
    }

    private val tokenManager = CheffyApp.instance!!.tokenManager

    interface GoogleLoginListener {
        fun onSuccess()
        fun onError(message: String)
    }

    init {
        fetchUser(account)
    }

    private fun fetchUser(account: GoogleSignInAccount) {
        val id = account.id.toString()
        val firstName = toFirstName(account.displayName.toString())
        val lastName = toLastName(account.displayName.toString())
        val email = account.email
        val photoUrl = account.photoUrl.toString()
        Timber.d(firstName + lastName + email + photoUrl + id)

        if (isRegistration) {
            getRegistration(firstName, lastName, email, PROVIDER, id, photoUrl)
        } else {
            getTokenFromBackend(firstName, lastName, email, PROVIDER, id, photoUrl)
        }
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

                    listener.onSuccess()
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
                    listener.onError("${it.message}")
                }
                Status.SUCCESS -> {
                    val dt = it.data
                    val tk = CheffyApp.instance!!.tokenManager

                    tk.user = dt
                    tk.setIsLoggedIn()

                    listener.onSuccess()
                }
            }
        })
    }

    private fun toFirstName(name: String): String {
        try {
            var firstName: String
            if (name.split("\\w+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 1) {
                firstName = name.substring(0, name.lastIndexOf(' '))
            } else {
                firstName = name
            }

            return firstName
        } catch (e: StringIndexOutOfBoundsException) {
            Timber.d("toFirstName: $e")
            return ""
        }

    }

    private fun toLastName(name: String): String {
        try {
            var lastName = ""
            if (name.split("\\w+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 1) {

                lastName = name.substring(name.lastIndexOf(" ") + 1)
            }
            return lastName

        } catch (e: StringIndexOutOfBoundsException) {
            Timber.d("toLastName: $e")
            return ""
        }
    }

}