package com.app.cheffyuser.custom_order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.CreateAccountActivity
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.activities.FoodDetailsActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.activity_custom_order.*

class CustomOrderActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodDetailsActivity::class.java)
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order)

        btn_post_custom_order.setOnClickListener {

            if (!CheffyApp.instance!!.tokenManager.isLoggedIn) {
                createSnack(
                    ctx = this,
                    txt = "Login to create order",
                    txtAction = "Login",
                    isDefinate = true,
                    action = View.OnClickListener {

                        val intent = Intent(
                            this,
                            CreateAccountActivity::class.java
                        )
                        startActivity(intent)
                    })
            }else{

                val intent = Intent(this@CustomOrderActivity, CustomOrderPostActivity::class.java)
                startActivity(intent)
            }

        }

        fab_close.setOnClickListener { finish() }
    }
}
