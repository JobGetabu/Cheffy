package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.Main3TabsAdapter
import com.google.android.material.tabs.TabLayout

class FoodDetailsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodDetailsActivity::class.java)
    }

    private val tabLayout: TabLayout? = null
    private val viewPager: ViewPager? = null
    internal lateinit var imgFullImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        imgFullImage = findViewById(R.id.full_imageview)


        val tabLayout = findViewById<TabLayout>(R.id.food_details_tab_layout)
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
                    imgFullImage.setImageResource(R.drawable.details_background)
                }

                if (tab.position == 1) {
                    imgFullImage.setImageResource(R.drawable.image_kitchen)
                }

                if (tab.position == 2) {
                    imgFullImage.setImageResource(R.drawable.reciept)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }


}
