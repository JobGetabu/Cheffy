package com.app.cheffyuser.create_account.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener



class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)


        val tabLayout =
            findViewById<View>(R.id.tab_layout_login) as TabLayout

        tabLayout.addTab(tabLayout.newTab().setText("Log in"))
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager =
            findViewById<View>(R.id.view_pager_login) as ViewPager

        val tabsAdapter =
            TabsAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.adapter = tabsAdapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object :
            TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}