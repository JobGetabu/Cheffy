package com.app.cheffyuser.home.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.ReviewDetailbarAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_sub2_tabs.*


/**
 * A simple [Fragment] subclass.
 */
class KitchenFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_kitchen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initTablayout()
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
