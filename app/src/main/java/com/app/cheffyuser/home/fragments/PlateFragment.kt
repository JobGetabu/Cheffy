package com.app.cheffyuser.home.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.ReviewDetailbarAdapter
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_plate.*
import kotlinx.android.synthetic.main.fragment_sub2_tabs.*


/**
 * A simple [Fragment] subclass.
 *
 * since the other fragment are the same as these
 * lets reuse this and save resources. (Resource pointer)
 *
 */
open class PlateFragment : BaseFragment() {

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_plate, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTablayout()


        foodname.text = vm.platesResponse.value?.name
        val tym = vm.platesResponse.value!!.deliveryTime
        if (tym != null) times.text = "${tym.minus(5)}-${tym} min"
        foodprice.text = "$" + vm.platesResponse.value?.price

    }


    private fun initTablayout() {
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ReviewDetailbarAdapter(childFragmentManager)
        adapter.addFragments(PlateDetailsFragment())
        adapter.addFragments(PlateReviewFragment())

        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 2

        tab_layout.setupWithViewPager(viewpager)
    }
}
