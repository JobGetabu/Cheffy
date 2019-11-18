package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.home.adapter.DetailMainbarAdapter
import com.app.cheffyuser.home.fragments.KitchenFragment
import com.app.cheffyuser.home.fragments.PlateFragment
import com.app.cheffyuser.home.fragments.ReceiptFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.Constants
import com.app.cheffyuser.utils.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_food_details.*
import timber.log.Timber
import kotlin.math.abs



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
        setContentView(com.app.cheffyuser.R.layout.activity_food_details)

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
        bottomlay.setOnClickListener {

            //TODO: Add check and verifications
            startActivity(FoodAddToCartActivity.newIntent(this))
        }
    }

    private fun initTablayout() {
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = DetailMainbarAdapter(supportFragmentManager)
        adapter.addFragments(PlateFragment())
        adapter.addFragments(KitchenFragment())
        adapter.addFragments(ReceiptFragment())

        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 3

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
                        top_im.setImageResource(com.app.cheffyuser.R.drawable.upload_thumbnail)
                    }

                    1 -> {
                        top_im.setImageResource(com.app.cheffyuser.R.drawable.image_kitchen)
                    }

                    2 -> {
                        top_im.setImageResource(com.app.cheffyuser.R.drawable.reciept)
                    }
                }
            }

        })

        tabLayout.setupWithViewPager(viewpager)
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
        showSystemUI()
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
