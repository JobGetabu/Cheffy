package com.app.cheffyuser.profile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.adapter.ChefPlatesAdapter
import com.app.cheffyuser.profile.model.PlateData
import com.app.cheffyuser.utils.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_chef_profile.*
import kotlinx.android.synthetic.main.item_loading.*
import timber.log.Timber
import kotlin.math.abs


class ChefProfileActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ChefProfileActivity::class.java)
    }

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

        var id = (vm.platesResponse.value as PlatesResponse).chef!!.id

        if (id == null) {
            id = intent.getIntExtra("chefId", 0)
        }

        loader_layout.showView()

        vm.getChefData(id).observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Chef can't be found now")

                    //check for net
                    try {
                        if (!isConnected)
                            checkNetwork()
                    } catch (e: Exception) {
                        Timber.wtf(e)
                    }
                }
                Status.SUCCESS -> {

                    loader_layout.hideView()

                    val chef = datas?.chef

                    restarantname.text = chef?.restaurantName
                    chefname.text = chef?.name
                    address.text = "fetching address..."
                    im_chef.loadUrl(chef?.imagePath)

                    if (!datas?.data.isNullOrEmpty()) {
                        val plates = datas?.data

                        plates?.forEachIndexed { index, plateData ->
                            tabLayout.addTab(
                                tabLayout.newTab().setText("${plateData?.name}"),
                                index
                            )
                        }

                        setPlateList(plates!!.toMutableList())

                    } else {
                        tabLayout.addTab(
                            tabLayout.newTab().setText("Popular foods")
                        )
                    }
                }
                Status.LOADING -> {
                }
            }
        })

    }

    private fun initTablayout() {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0!!.position
                setCurrentItem(position, true)
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


    private fun setPlateList(items: MutableList<PlateData?>?) {
        recyclerview_plate.setHasFixedSize(true)

        val adapter = ChefPlatesAdapter(this, items,
            object : RecyclerItemClickListener {
                override fun modelClick(model: Any) {
                    model as PlateData

                    createSnack(ctx = this@ChefProfileActivity, txt = "Go to categoty foods")

                }
            })

        recyclerview_plate.adapter = adapter

        recyclerview_plate.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = getCurrentItem()

                    Timber.d("Recycler position => $position")
                    //set position to tabs
                    tabLayout.getTabAt(position)?.select()

                }
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerview_plate)
    }

    private fun getCurrentItem(): Int {
        return (recyclerview_plate.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    private fun setCurrentItem(position: Int, smooth: Boolean) {
        if (smooth) recyclerview_plate.smoothScrollToPosition(position) else recyclerview_plate.scrollToPosition(
            position
        )
    }
}
