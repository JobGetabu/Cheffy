package com.app.cheffyuser.profile.fragments

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
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.adapter.FoodFavouriteAdapter
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.fragment_profile_chef.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserFavoriteFragment : Fragment() {


    private lateinit var foodFavAdapter: FoodFavouriteAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_chef, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setFavouriteList()

        swipeToRefresh.setOnRefreshListener {
            setFavouriteList()
        }

    }


    private fun setFavouriteList() {
        recyclerview_food_menu.setHasFixedSize(true)

        recyclerview_food_menu.hideView()
        shimmer_view_container.showView()


        vm.fetchFavourite().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    swipeToRefresh.isRefreshing = false

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No popular foods")

                    Timber.d("$it")

                    //check for net
                    try {
                        val act: BottomNavActivity = getActivity()!! as BottomNavActivity
                        act.checkNetwork()
                    } catch (e: Exception) {
                        Timber.wtf(e)
                    }

                    recyclerview_food_menu.hideView()
                    shimmer_view_container.showView()

                }
                Status.SUCCESS -> {
                    recyclerview_food_menu.showView()
                    shimmer_view_container.hideView()

                    swipeToRefresh.isRefreshing = false

                    val myFavs = mutableListOf<Any>()

                    datas?.data?.forEach { d ->
                        if (d != null) {
                            if (d.customPlates != null) {

                                myFavs.add(d.customPlates)
                            }

                            if (d.plate != null) {
                                myFavs.add(d.plate)
                            }
                        }
                    }

                    myFavs.let {
                        foodFavAdapter = FoodFavouriteAdapter(activity!!, vm, myFavs,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as Any

                                }
                            })
                    }

                    recyclerview_food_menu.adapter = foodFavAdapter
                }
                Status.LOADING -> {
                }
            }

        })
    }

}