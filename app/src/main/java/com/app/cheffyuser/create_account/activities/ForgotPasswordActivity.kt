package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.cheffyuser.R
import com.app.cheffyuser.home.BaseActivity
import timber.log.Timber

class ForgotPasswordActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ForgotPasswordActivity::class.java)
    }

    fun close(v: View) {
        Timber.i("$v")
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

}
