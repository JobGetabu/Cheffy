package com.app.cheffyuser.home.activities

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.cart.activities.ItemCartActivity
import com.app.cheffyuser.home.adapter.DetailMainbarAdapter
import com.app.cheffyuser.home.fragments.KitchenFragment
import com.app.cheffyuser.home.fragments.PlateFragment
import com.app.cheffyuser.home.fragments.ReceiptFragment
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.activities.ChefProfileActivity
import com.app.cheffyuser.utils.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_food_details.*
import kotlinx.android.synthetic.main.common_star_time_delivey.*
import kotlinx.android.synthetic.main.float_viewcart.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlin.math.abs


class FoodDetailsActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodDetailsActivity::class.java)

        const val plateId = "plateId"
    }

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

        dotsManager(1)

        loadPlateData()

    }

    private fun loadPlateData() {

        loader_layout.showView()

        var id: Int? = null

        if (vm.platesResponse.value != null) {
            id = (vm.platesResponse.value as PlatesResponse).id
        }


        if (id == null) {
            id = intent.getIntExtra(plateId, 0)
        }
        //TODO check id from another intent => search

        vm.getPlate(id).observe(this, Observer {

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: No plate")

                    loader_layout.hideView()

                }
                Status.SUCCESS -> {
                    loader_layout.hideView()

                    vm.platesResponse.value = it.data?.data
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun uiStuff() {

        chefname.setOnClickListener {
            goToChefProfile()
        }

        image_card.setOnClickListener {
            goToChefProfile()
        }

        val chef = vm.platesResponse.value?.chef

        if (!vm.platesResponse.value?.plateImages.isNullOrEmpty()) {

            top_im.loadUrl(vm.platesResponse.value?.plateImages?.get(0)?.url)
        }

        //setup chef details
        if (chef != null) {
            chefname.text = chef.name
            chefpic.loadUrl(chef.imagePath)

            var ratingSum = 0
            var rating = 0

            vm.platesResponse.value?.reviews?.forEach {
                //ratingSum += it.rating!!
            }

            vm.platesResponse.value?.reviews?.let {
                if (it.count() != 0)
                    rating = ratingSum / it.count()
            }

            food_ratings.text = "$rating(${ratingSum})"

            times.text =
                "${vm.platesResponse.value!!.deliveryTime!!.minus(5)}-${vm.platesResponse.value?.deliveryTime} min"

            if (!chef.address.isNullOrEmpty())
                address.text = chef.address[0].addressLine1.toString()
        }

        getBasket()

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
                        if (!vm.platesResponse.value?.plateImages.isNullOrEmpty())
                            top_im.loadUrl(vm.platesResponse.value?.plateImages?.get(0)?.url)

                        dotsManager(1)
                    }

                    1 -> {
                        if (!vm.platesResponse.value?.kitchenImages.isNullOrEmpty())
                            top_im.loadUrl(vm.platesResponse.value?.kitchenImages?.get(0)?.url)

                        dotsManager(2)
                    }

                    2 -> {
                        if (!vm.platesResponse.value?.receiptImages.isNullOrEmpty())
                            top_im.loadUrl(vm.platesResponse.value?.receiptImages?.get(0)?.url)

                        dotsManager(3)

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
        hideSystemUI()

        getBasket()
    }

    private val onOffsetChangedListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->


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

    private fun goToChefProfile() {

        //val pair1: android.util.Pair<View, String> = android.util.Pair.create(topimageview, "slider")
        val pair2: android.util.Pair<View, String> = android.util.Pair.create(chefpic, "slider1")
        //val pair3: android.util.Pair<View, String> = android.util.Pair.create(tabLayout, "slider3")

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            this, pair2
        )

        val model = vm.platesResponse.value
        val intent = ChefProfileActivity.newIntent(this)
        intent.putExtra(Constants.PLATES_RESPONSE_EXTRA, model)
        startActivity(intent, activityOptions.toBundle())
    }

    private fun dotsManager(isBig: Int) {
        when (isBig) {
            1 -> {
                dot1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                dot2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                dot3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
            }
            2 -> {
                dot2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                dot1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                dot3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
            }
            3 -> {
                dot3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                dot2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                dot1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
            }
        }
    }


    private fun getBasket() {

        layout_item_cart_body.setOnClickListener {
            startActivity(Intent(this, ItemCartActivity::class.java))
        }

        vm.getBasket().observe(this, Observer {
            val data = it.data

            when (it.status) {
                Status.SUCCESS -> {
                    if (!data!!.items.isNullOrEmpty()) {
                        layout_item_cart.showView()
                        tv_total.text = "$" + "${data.total}"
                        tv_count.text = "${data.items?.count()}"

                    } else {
                        layout_item_cart.hideView()
                    }
                }
            }
        })
    }

}
