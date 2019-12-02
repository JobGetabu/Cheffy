package com.app.cheffyuser.home.fragments


import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BottomNavActivity
import com.app.cheffyuser.home.activities.FoodDetailsActivity
import com.app.cheffyuser.home.adapter.FoodNearbyAdapter
import com.app.cheffyuser.home.adapter.FoodPopularAdapter
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.Constants.PLATES_RESPONSE_EXTRA
import com.app.cheffyuser.utils.KeyboardUtils
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.fragment_chef_home.*
import kotlinx.android.synthetic.main.search_bar_layout.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserHomeFragment : BaseFragment() {

    private lateinit var foodNearbyAdapter: FoodNearbyAdapter
    private lateinit var foodNearbyAdapter2: FoodNearbyAdapter
    private lateinit var foodPopularAdapter: FoodPopularAdapter

    private var isFocused = false

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStuff()
    }

    private fun uiStuff() {

        detectedLocation()
        setUpNearbyList()
        setUpNewList()
        setPopularList()

        address_txt.requestFocus()
        search_et.isFocusable = false

        KeyboardUtils.hideKeyboard(activity!!)


        search_et.setOnClickListener {

            Timber.d("search clicked")
            vm.pagerCurrentItem.value = 1
            //showKeyboard(activity!!)

            val mum: BottomNavActivity = getActivity()!! as BottomNavActivity

            mum.searchView.openSearch()

        }

    }

    private fun detectedLocation() {
        Timber.d("Location: lat=>  ${vm.mCurrentLatitude.value} lon=>${vm.mCurrentLongtitide.value} address=>${vm.mAddressText.value}")
    }

    private fun setUpNearbyList() {
        nearbylist.setHasFixedSize(true)
        main_content.hideView()

        //TODO: pass in location coordinates
        vm.fetchFoodNearbyLocation().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No nearby foods")

                    //check for net
                    try {
                        val act: BottomNavActivity = getActivity()!! as BottomNavActivity
                        act.checkNetwork()
                    } catch (e: Exception) {
                        Timber.wtf(e)
                    }

                    shimmer_view_container.showView()
                    main_content.hideView()

                    //checkNetwork()
                }
                Status.SUCCESS -> {

                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.hideView()
                    main_content.showView()

                    datas.let {
                        foodNearbyAdapter = FoodNearbyAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as PlatesResponse

                                    goToFoodDetails(model)
                                }
                            })
                    }

                    nearbylist.adapter = foodNearbyAdapter
                }
                Status.LOADING -> {
                    //TODO: stop shimmer effect in view
                    //still loading data
                    shimmer_view_container.showView()
                    main_content.hideView()
                }
            }

        })

    }

    private fun setUpNewList() {
        newlist.setHasFixedSize(true)

        vm.fetchFoodNewest().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No new foods")

                }
                Status.SUCCESS -> {
                    datas.let {
                        foodNearbyAdapter2 = FoodNearbyAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as PlatesResponse

                                    goToFoodDetails(model)
                                }
                            })
                    }

                    newlist.adapter = foodNearbyAdapter2
                }
                Status.LOADING -> {
                    //TODO: stop shimmer effect in view
                    //still loading data
                }
            }

        })

    }

    private fun setPopularList() {
        popularlist.setHasFixedSize(true)

        vm.fetchFoodPopular().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No popular foods")

                    //check for net
                    try {
                        val act: BottomNavActivity = getActivity()!! as BottomNavActivity
                        act.checkNetwork()
                    } catch (e: Exception) {
                        Timber.wtf(e)
                    }

                }
                Status.SUCCESS -> {
                    datas.let {
                        foodPopularAdapter = FoodPopularAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as PlatesResponse

                                    goToFoodDetails(model)
                                }
                            })
                    }

                    popularlist.adapter = foodPopularAdapter
                }
                Status.LOADING -> {
                    //TODO: stop shimmer effect in view
                    //still loading data
                }
            }

        })
    }

    private fun goToFoodDetails(model: PlatesResponse) {
        val pair1: android.util.Pair<View, String> = android.util.Pair.create(address_txt, "slider")

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            activity!!, pair1
        )

        val intent = FoodDetailsActivity.newIntent(context!!)
        intent.putExtra(PLATES_RESPONSE_EXTRA, model)

        startActivity(intent, activityOptions.toBundle())
    }
}
