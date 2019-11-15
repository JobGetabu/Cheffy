package com.app.cheffyuser

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.cart.TabsFragment
import com.app.cheffyuser.custom_order.CustomOrderActivity
import com.app.cheffyuser.food_category.FoodCategoryFragment
import com.app.cheffyuser.home.fragments.UserHomeFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.profile.fragments.AccountFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {
    private var mTextMessage: TextView? = null

    internal var fragment: Fragment? = null

    internal var bottomNavigationView: BottomNavigationView? = null

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = UserHomeFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {

                    fragment = FoodCategoryFragment()
                    loadFragment(fragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_custom_order -> {

                    val intent = Intent(this@BottomNavActivity, CustomOrderActivity::class.java)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_cart -> {
                    //mTextMessage.setText(R.string.title_notifications);
                    fragment = TabsFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    fragment = AccountFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        mTextMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //loading the default fragment
        loadFragment(UserHomeFragment())
    }


    //loadFragment function
    private fun loadFragment(fragment: Fragment?) {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit()
        }
    }

}
