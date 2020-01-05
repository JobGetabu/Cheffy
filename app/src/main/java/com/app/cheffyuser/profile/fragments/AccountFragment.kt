package com.app.cheffyuser.profile.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ShippingRequest
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.activities.EditProfileActivity
import com.app.cheffyuser.profile.activities.ShippingActivity
import com.app.cheffyuser.profile.adapter.ProfilebarAdapter
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_user_main.*


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : BaseFragment() {

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStuff()
        initTablayout()
    }

    private fun uiStuff() {

        vm.fetchUser().observe(this, Observer { dt ->
            val datas = dt.data

            when (dt.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "User can't be fetched now")

                }
                Status.SUCCESS -> {
                    tokenManager.userData = datas?.data

                    datas.let {

                        tv_user_name.text = it?.data?.name
                        user_image.loadUrl(it?.data?.imagePath, R.drawable.avatar_placeholder)
                    }
                }
                Status.LOADING -> {
                    //still loading data
                }
            }

        })

        vm.fetchShipping().observe(this, Observer {
            val datas = it.data?.shippingResponseData

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(
                            ctx = activity!!,
                            txt = "Debug only: Address can't be fetched now"
                        )
                }
                Status.SUCCESS -> {
                    if (!datas.isNullOrEmpty()) {

                        val data = datas.first { d -> d.isDefaultAddress == true }

                        tokenManager.shippingData = data
                        vm.shippingData.value = data

                        val ship = ShippingRequest()
                        ship.lat = data.lat
                        ship.lon = data.lon
                        ship.state = data.state
                        ship.city = data.city
                        ship.addressLine1 = data.addressLine1
                        ship.addressLine2 = data.addressLine2
                        ship.zipCode = data.zipCode

                        tokenManager.shippingData2 = ship

                    }

                    checkShippingData()

                    datas.let {
                        if (!datas.isNullOrEmpty())
                            tv_user_address.text = "${it?.get(0)?.city} ${it?.get(0)?.state}"
                    }
                }
                Status.LOADING -> {
                    //still loading data
                }
            }

        })

        img_profile_edit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initTablayout() {
        profile_tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ProfilebarAdapter(childFragmentManager)
        adapter.addFragments(UserFavoriteFragment())
        adapter.addFragments(AccountsSettingFragment())

        profile_view_pager.adapter = adapter
        profile_view_pager.offscreenPageLimit = 2

        profile_tab_layout.setupWithViewPager(profile_view_pager)

    }

    private fun checkShippingData() {

        if (!tokenManager.isLoggedIn) return
        if (tokenManager.shippingData2 != null) return
        if (tokenManager.shippingData != null) return

        val intent = Intent(
            activity,
            ShippingActivity::class.java
        )
        startActivity(intent)
    }

}
