package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.app.cheffyuser.R
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.utils.Constants
import com.app.cheffyuser.utils.toast
import kotlinx.android.synthetic.main.activity_food_add_to_cart.*

class FoodAddToCartActivity : AppCompatActivity() {

    private var platesResponse: PlatesResponse? = null

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodAddToCartActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_add_to_cart)

        platesResponse = intent.getParcelableExtra(Constants.PLATES_RESPONSE_EXTRA)

        foodname.text = platesResponse?.name
        fooddescription.text = platesResponse?.description

        bottomlay.setOnClickListener {
            toast("Added to cart")
            finish()
        }
    }
}
