package com.app.cheffyuser.custom_order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.activities.FoodDetailsActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
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
            val intent = Intent(this@CustomOrderActivity, CustomOrderPostActivity::class.java)
            startActivity(intent)
        }


        fab_close.setOnClickListener { finish() }
    }
}
