package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.cheffyuser.R
import kotlinx.android.synthetic.main.activity_set_passoword.*
import timber.log.Timber

class SetPasswordActivity : AppCompatActivity() {

    private var cd = "0000"

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, SetPasswordActivity::class.java)
    }

    fun close(v: View) {
        Timber.i("$v")
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_passoword)

        cd = intent.getStringExtra("email_token")!!

        btn_continue.setOnClickListener{
            goToLogin()
        }
    }

    private fun goToLogin() {

        //TODO: call to show successful password change
    }
}
