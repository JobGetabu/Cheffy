package com.app.cheffyuser.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.fragments.AddCartFragment
import com.app.cheffyuser.cart.fragments.CustomOrderFragment
import com.app.cheffyuser.cart.fragments.DeliveryFragment
import com.app.cheffyuser.cart.fragments.TrackingOrderFragment
import com.app.cheffyuser.home.adapter.CartTabAdapter
import com.app.cheffyuser.home.fragments.BaseFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tabs.*


class TabsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CartTabAdapter(childFragmentManager)
        //setup vp
        //pager.setPagingEnabled(true)

        adapter.addFragments(AddCartFragment())
        adapter.addFragments(CustomOrderFragment())
        adapter.addFragments(TrackingOrderFragment())
        adapter.addFragments(DeliveryFragment())
        pager.adapter = adapter

        pager.offscreenPageLimit = 4
        pager.currentItem = 0

        tab_layout.tabGravity = TabLayout.GRAVITY_FILL
        tab_layout.setupWithViewPager(pager)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        //TODO: refresh the viewmodels
                    }

                    1 -> {

                    }

                    2 -> {

                    }
                }
            }

        })

    }
}
