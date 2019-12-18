package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ChangePasswordRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.activity_set_passoword.*

class SetPasswordActivity : BaseActivity() {

    private var cd = "0000"

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, SetPasswordActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_passoword)

        cd = intent.getStringExtra("email_token")!!

        btn_continue.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {

        val password = etEmail.text.toString()

        if (password.isEmpty() && password.length < 6) {
            etEmailxx.isErrorEnabled = true
            etEmailxx?.error = "at least 6 characters"
            return
        }

        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { goToLogin() })

            return
        }

        val dialog = showDialogue("Resetting password", "Please wait ...")

        val sReq = ChangePasswordRequest(tokenManager.email, cd, password)

        vm.changePassword(sReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it?.data?.message}", dialog!!)
                }
                Status.SUCCESS -> {

                    val res = it.data!!
                    successDialogue(alertDialog = dialog, descriptions = res.message!!)

                    val intent = Intent(this, CreateAccountActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })
    }
}
