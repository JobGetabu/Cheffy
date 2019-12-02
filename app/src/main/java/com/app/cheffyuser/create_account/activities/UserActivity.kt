package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.fragments.LoginFragment
import com.app.cheffyuser.create_account.model.LoginResponse
import com.app.cheffyuser.create_account.model.RegistrationRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.toast
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, UserActivity::class.java)

        const val CHECKOUT_IN_PROGRESS: String = "CHECKOUT_IN_PROGRESS"
    }

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    private var checkoutInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btn_sign_up.setOnClickListener {
            register()
        }

        checkoutInProgress = intent.getBooleanExtra(LoginFragment.CHECKOUT_IN_PROGRESS, false)
    }

    //TODO: API Add promotional message
    private fun register() {

        val name = etName.editText!!.text.toString()
        val password = etPassword.editText!!.text.toString()

        if (name.isEmpty()) {
            etName.isErrorEnabled = true
            etName.error = "Enter a valid name"
            return
        }

        if (password.isEmpty() && password.length < 6) {
            etPassword.isErrorEnabled = true
            etPassword?.error = "at least 6 characters"
            return
        }

        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { register() })

            return
        }

        val dialog = showDialogue("Registering account", "Please wait ...")
        val req = RegistrationRequest(tokenManager.email!!, name, password, "user")

        vm.registerAccount(req).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog, descriptions = it.message.toString())

                    val res = it.data

                    toast("Proceed to login", Toast.LENGTH_LONG)

                    //save in prefs
                    val user = LoginResponse(res?.data, null)
                    tokenManager.user = user
                    tokenManager.setIsLoggedIn()

                    CHECKOUT_IN_PROGRESS
                    val intent = Intent(this, CreateAccountActivity::class.java)
                    intent.putExtra(CHECKOUT_IN_PROGRESS, checkoutInProgress)
                    startActivity(intent)

                    finish()
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })

    }
}


