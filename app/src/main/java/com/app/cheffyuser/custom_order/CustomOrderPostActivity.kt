package com.app.cheffyuser.custom_order

import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_order_post.*

class CustomOrderPostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order_post)

        btn_post_order.setOnClickListener {
            val intent = Intent(this@CustomOrderPostActivity, OrderCompleteActivity::class.java)
            startActivity(intent)
        }

    }
}
