package com.app.cheffyuser.cart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.DeliveryAdapter

/**
 * A simple [Fragment] subclass.
 */
class DeliveryFragment : Fragment() {
    var foodItemList =
        arrayOf("Grilled salmon", "Pasta Ham")
    var foodPriceList = doubleArrayOf(96.00, 120.00)
    var imgList = intArrayOf(R.drawable.image1, R.drawable.img_food_2)
    var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery, container, false)
        // get the reference of RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        // set a LinearLayoutManager with default vertical orientation
        val linearLayoutManager = LinearLayoutManager(activity)
        // set a LinearLayoutManager with default Horizontal orientation
// LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView!!.setLayoutManager(linearLayoutManager)
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        val customAdapter =
            DeliveryAdapter(activity, foodItemList, foodPriceList, imgList)
        recyclerView!!.setAdapter(customAdapter) // set the Adapter to RecyclerView
        return view
    }
}