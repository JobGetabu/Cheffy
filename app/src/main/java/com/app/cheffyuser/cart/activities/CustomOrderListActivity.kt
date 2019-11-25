package com.app.cheffyuser.cart.activities

import android.os.Bundle
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.CustomOrderListAdapter
import com.app.cheffyuser.home.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_order_list.*

class CustomOrderListActivity : BaseActivity() {

    var foodItemList = arrayOf("Cheffy", "Chefyy")
    var foodPriceList = doubleArrayOf(96.00, 120.00)
    var imgList = intArrayOf(R.drawable.ic_item_1, R.drawable.ic_item_2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order_list)


        val customAdapter =
            CustomOrderListAdapter(this, foodItemList, foodPriceList, imgList)
        recycler_view.setAdapter(customAdapter) // set the Adapter to RecyclerView
    }
}