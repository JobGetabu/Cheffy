package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.activities.BottomNavActivity
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, UserActivity::class.java)
    }

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btn_sign_up.setOnClickListener {
            val intent = Intent(this@UserActivity, BottomNavActivity::class.java)
            startActivity(intent)

            register()
        }
    }

    private fun register() {

        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { register() })

            return
        }

    }
}


