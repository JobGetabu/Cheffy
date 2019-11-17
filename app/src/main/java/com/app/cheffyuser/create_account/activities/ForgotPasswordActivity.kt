package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ForgotPasswordActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_continue.setOnClickListener {
            startActivity(VerifyEmailActivity.newIntent(this))
        }
    }

}
