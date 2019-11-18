package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.Main3TabsAdapter
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.TokenManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_food_details.*

class FoodDetailsActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodDetailsActivity::class.java)
    }

    private val tokenManager: TokenManager = CheffyApp.instance!!.tokenManager

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        showSystemUI()

        tabLayout.addTab(tabLayout.newTab().setText("The Plate"))
        tabLayout.addTab(tabLayout.newTab().setText("Kitchen"))
        tabLayout.addTab(tabLayout.newTab().setText("Receipts"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val tabsAdapter = Main3TabsAdapter(supportFragmentManager, tabLayout.tabCount)
        viewpager.adapter = tabsAdapter
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position

                if (tab.position == 0) {
                    top_im.setImageResource(R.drawable.upload_thumbnail)
                }

                if (tab.position == 1) {
                    top_im.setImageResource(R.drawable.image_kitchen)
                }

                if (tab.position == 2) {
                    top_im.setImageResource(R.drawable.reciept)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    /**
     * TODO: Add focus change listener to show and hide the
     *       bottom View cart entry
     *
     */

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        showSystemUI()
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()

        showSystemUI()
    }
}
