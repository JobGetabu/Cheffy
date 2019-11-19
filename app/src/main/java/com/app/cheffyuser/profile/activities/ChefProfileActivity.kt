package com.app.cheffyuser.profile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.DetailMainbarAdapter
import com.app.cheffyuser.home.fragments.KitchenFragment
import com.app.cheffyuser.home.fragments.PlateFragment
import com.app.cheffyuser.home.fragments.ReceiptFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.Constants
import com.app.cheffyuser.utils.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_chef_profile.*
import timber.log.Timber
import kotlin.math.abs

class ChefProfileActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ChefProfileActivity::class.java)
    }

    private val tokenManager: TokenManager = CheffyApp.instance!!.tokenManager

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_profile)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener)

        showSystemUI()
        hideSystemNavOnly()

        initTablayout()

        //get passed data
        vm.platesResponse.value =
            intent.getParcelableExtra(Constants.PLATES_RESPONSE_EXTRA)

        uiStuff()


    }

    private fun uiStuff() {


    }

    private fun initTablayout() {
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = DetailMainbarAdapter(supportFragmentManager)
        adapter.addFragments(PlateFragment())
        adapter.addFragments(KitchenFragment())
        adapter.addFragments(ReceiptFragment())

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

            }
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

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private val onOffsetChangedListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->

            Timber.d("appbar verticalOffset => $verticalOffset")

            when {
                abs(verticalOffset) == appBarLayout.totalScrollRange -> // Collapsed
                    hideSystemUI()
                verticalOffset == 0 -> // Fully Expanded - show the status bar
                    showSystemUI()
                else -> // Somewhere in between
                    // We could optionally dim icons in this step by adding the flag:
                    // View.SYSTEM_UI_FLAG_LOW_PROFILE
                    hideSystemUI()
            }
        }
}
