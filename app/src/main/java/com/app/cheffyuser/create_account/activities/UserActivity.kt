package com.app.cheffyuser.create_account.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.cheffyuser.home.activities.BottomNavActivity
import com.app.cheffyuser.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btn_sign_up.setOnClickListener {
            val intent = Intent(this@UserActivity, BottomNavActivity::class.java)
            startActivity(intent)
        }
    }
}


