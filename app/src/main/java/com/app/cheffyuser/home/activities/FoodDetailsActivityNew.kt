package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.Main3TabsAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_food_details_new.*

class FoodDetailsActivityNew : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodDetailsActivityNew::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details_new)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        tabLayout.addTab(tabLayout.newTab().setText("The Plate"))
        tabLayout.addTab(tabLayout.newTab().setText("Kitchen"))
        tabLayout.addTab(tabLayout.newTab().setText("Receipts"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL


        val viewPager = findViewById<View>(R.id.food_view_pager) as ViewPager
        val tabsAdapter = Main3TabsAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = tabsAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

                if (tab.position == 0) {
                    top_im.setImageResource(R.drawable.details_background)
                }

                if (tab.position == 1) {
                    top_im.setImageResource(R.drawable.image_kitchen)
                }

                if (tab.position == 2) {
                    top_im.setImageResource(R.drawable.reciept)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }


}
