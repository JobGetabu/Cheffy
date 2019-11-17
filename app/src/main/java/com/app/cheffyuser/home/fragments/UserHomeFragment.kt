package com.app.cheffyuser.home.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.FoodDetailsActivityNew
import com.app.cheffyuser.home.adapter.FoodNearbyAdapter
import com.app.cheffyuser.home.adapter.FoodPopularAdapter
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.remote.Status
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.fragment_chef_home.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserHomeFragment : BaseFragment() {

    private lateinit var foodNearbyAdapter: FoodNearbyAdapter
    private lateinit var foodNearbyAdapter2: FoodNearbyAdapter
    private lateinit var foodPopularAdapter: FoodPopularAdapter

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

        address_txt.requestFocus()

        detectedLocation()
        setUpNearbyList()
        setUpNewList()
        setPopularList()

        hideKeyboard(activity!!)

    }

    fun detectedLocation(){
        Timber.d("Location: lat=>  ${vm.mCurrentLatitude} lon=>${vm.mCurrentLongtitide} address=>${vm.mAddressText}")
    }

    private fun setUpNearbyList() {
        nearbylist.setHasFixedSize(true)

        //TODO: pass in location coordinates
        vm.fetchFoodNearbyLocation().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {
                    //TODO: stop shimmer effect in view
                    //TODO: send manged logs to crashlytics in production
                    createSnack(ctx = activity!!, txt = "No nearby foods")

                }
                Status.SUCCESS -> {
                    datas.let {
                        foodNearbyAdapter = FoodNearbyAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any){
                                    model as PlatesResponse
                                    createSnack(ctx = activity!!, txt = "clicked ${model.name}")
                                    startActivity(FoodDetailsActivityNew.newIntent(context!!))
                                }
                            })
                    }

                    nearbylist.adapter = foodNearbyAdapter
                }
                Status.LOADING -> {
                    //TODO: stop shimmer effect in view
                    //still loading data
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
                    //TODO: stop shimmer effect in view
                    createSnack(ctx = activity!!, txt = "No nearby foods")

                }
                Status.SUCCESS -> {
                    datas.let {
                        foodNearbyAdapter2 = FoodNearbyAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any){
                                    model as PlatesResponse
                                    createSnack(ctx = activity!!, txt = "clicked ${model.name}")
                                    startActivity(FoodDetailsActivityNew.newIntent(context!!))
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
                    //TODO: stop shimmer effect in view
                    createSnack(ctx = activity!!, txt = "No nearby foods")

                }
                Status.SUCCESS -> {
                    datas.let {
                        foodPopularAdapter = FoodPopularAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any){
                                    model as PlatesResponse
                                    createSnack(ctx = activity!!, txt = "clicked ${model.name}")
                                    startActivity(FoodDetailsActivityNew.newIntent(context!!))
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

}
