package com.app.cheffyuser.profile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ShippingData
import com.app.cheffyuser.create_account.model.ShippingRequest
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.adapter.AddressAdapter
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.loadAnim
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.activity_list_shipping.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber

class ListShippingActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ListShippingActivity::class.java)
    }

    private lateinit var adapter: AddressAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_shipping)


        uiStuff()
    }

    private fun uiStuff() {
        no_item_text.text = getString(R.string.your_address_appear)

        fab.setOnClickListener {
            startActivity(ShippingActivity.newIntent(this))
        }

        setupList()
    }

    private fun setupList() {

        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()

        vm.fetchShipping().observe(this, Observer {

            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    adapter = AddressAdapter(
                        this,
                        data!!.shippingResponseData?.toMutableList(),
                        object : RecyclerItemClickListener {
                            override fun modelClick(model: Any) {
                                model as ShippingData

                                setShipping(model)
                            }
                        })

                    recycler_view.adapter = adapter
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun setShipping(model: ShippingData) {

        val ship = ShippingRequest()
        ship.zipCode = model.zipCode
        ship.addressLine1 = model.addressLine1
        ship.addressLine2 = model.addressLine2
        ship.deliveryNote = model.deliveryNote
        ship.state = model.state
        ship.city = model.city
        ship.lat = model.lat
        ship.lon = model.lon
        ship.isDefaultAddress = true


        val dialog = showDialogue("Switching shipping address", "Please wait ...")

        vm.setDefaultShipping(model.id!!, ship).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "${it?.message}")

                    errorDialogue("Error", "Something went wrong", dialog)
                    //checkNetwork()
                }
                Status.SUCCESS -> {

                    tokenManager.shippingData2 = ship
                    tokenManager.shippingData = it.data?.shippingResponseData

                    successDialogue(alertDialog = dialog, descriptions = "${it.data?.message}")
                    finish()
                }
                Status.LOADING -> {

                }
            }
        })
    }
}
