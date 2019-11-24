package com.app.cheffyuser.cart.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.activities.CustomOrderCheckoutActivity
import com.app.cheffyuser.cart.adapter.CustomOrderAdapter
import kotlinx.android.synthetic.main.fragment_custom_order.*


/**
 * A simple [Fragment] subclass.
 */
class CustomOrderFragment : Fragment() {

    internal var foodItemList = arrayOf("Grilled salmon", "Pasta Ham  ")
    internal var foodPriceList = doubleArrayOf(96.00, 120.00)
    internal var imgList = intArrayOf(R.drawable.ic_item_1, R.drawable.ic_item_2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_custom_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val customAdapter = CustomOrderAdapter(activity, foodItemList, foodPriceList, imgList)
        recycler_view.adapter = customAdapter // set the Adapter to RecyclerView


        layout_custom_order_cart.setOnClickListener {
            val intent = Intent(activity, CustomOrderCheckoutActivity::class.java)
            startActivity(intent)
        }

    }

}// Required empty public constructor
