package com.app.cheffyuser.custom_order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BottomNavActivity

class OrderCompleteActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, OrderCompleteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_complete)

        closeMe()

    }

    private fun closeMe(){

        Handler().postDelayed({
            startActivity(BottomNavActivity.newIntent(this).apply {
                Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finish()
        },3000)
    }
}
