package com.app.cheffyuser.create_account.social

import android.content.Context
import com.app.cheffyuser.CheffyApp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import timber.log.Timber

class GoogleManager(
    private val context: Context,
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

        //TODO: save to SharedPreferences
        //tokenManager => save user data

        getTokenFromBackend(firstName, lastName, email, PROVIDER, id, photoUrl)

    }

    /*
   * This should be an API call to save user returned by google to our backend
   * Retrieve Token
   *
   * */
    private fun getTokenFromBackend(
        firstName: String,
        lastName: String,
        email: String?,
        provider: String,
        providerUserId: String,
        photoUrl: String
    ) {

        //TODO: Make a social login call to backend to retrieve a token
        // activate out listeners here

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