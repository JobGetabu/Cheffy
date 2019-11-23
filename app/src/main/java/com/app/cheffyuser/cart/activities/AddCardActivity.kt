package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.cheffyuser.R

class AddCardActivity : AppCompatActivity() {


    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddCardActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        uiStuff()
    }

    private fun uiStuff() {

    }
}
