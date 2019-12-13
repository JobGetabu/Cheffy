package com.app.cheffyuser.cart.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.activities.ItemCartActivity
import com.app.cheffyuser.cart.adapter.AddCartAdapter
import com.app.cheffyuser.home.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_cart.*
import kotlinx.android.synthetic.main.no_item_layout.*


/**
 * A simple [Fragment] subclass.
 */
class AddCartFragment : BaseFragment() {

    internal var foodItemList = arrayOf("Grilled salmon", "Pasta Ham  ")
    internal var foodPriceList = doubleArrayOf(96.00, 120.00)
    internal var imgList = intArrayOf(R.drawable.ic_item_1, R.drawable.ic_item_2)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val customAdapter = AddCartAdapter(activity, foodItemList, foodPriceList, imgList)
        //recycler_view.adapter = customAdapter // set the Adapter to RecyclerView

        no_item_text.text = "Foods in cart appear here"

        layout_item_cart.setOnClickListener {
            val intent = Intent(activity, ItemCartActivity::class.java)
            startActivity(intent)
        }

    }

}
