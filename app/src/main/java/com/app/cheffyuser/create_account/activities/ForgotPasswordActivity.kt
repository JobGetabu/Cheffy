package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ForgotRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ForgotPasswordActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_continue.setOnClickListener {
            getToken()
        }

    }

    private fun getToken() {

        val email = tiEmail.text.toString()

        if (email.isEmpty() or !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.isErrorEnabled = true
            etEmail.error = "Enter valid email address"
            return
        }


        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { getToken() })

            return
        }

        val dialog = showDialogue("Sending you verification code", "Please wait ...")


        val pReq = ForgotRequest(email)

        vm.forgotPassword(pReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog)
                }
                Status.SUCCESS -> {

                    val res = it.data
                    successDialogue(alertDialog = dialog, descriptions = "${res?.message}")

                    tokenManager.email = email

                    startActivity(VerifyEmailActivity.newIntent(this))

                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })

    }
}
