package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.CartItemsFullAdapter
import com.app.cheffyuser.cart.adapter.UpdateCartClickListener
import com.app.cheffyuser.cart.models.BasketListResponse
import com.app.cheffyuser.create_account.model.ShippingData
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.activities.ShippingActivity
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.loadAnim
import com.app.cheffyuser.utils.showView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_item_cart.*
import kotlinx.android.synthetic.main.item_loading_full.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber

class ItemCartActivity : BaseActivity(), OnMapReadyCallback {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ItemCartActivity::class.java)

        const val DELIVERY = 0
        const val PICKUP = 1
        var SELECTDELIVERY = -1

        const val CREDITCARD = 0
        const val CASH = 1
        var SELECTPAYMENT = -1
    }

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var map: GoogleMap
    private var ship: ShippingData? = null

    private lateinit var cartItemsAdapter: CartItemsFullAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_cart)

        uiStuff()

    }

    private fun uiStuff() {
        mapFragment = (supportFragmentManager
            .findFragmentById(R.id.mMap) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)

        shippingDetails()

        btn_checkout.setOnClickListener {
            checkOut()
        }

        selectDeliveryFun()
        selectPayment()

        //items
        no_item_text.text = "Foods in cart appear here"

        setupCartList()

    }


    private fun shippingDetails() {

        ship = tokenManager.shippingData

        if (!ship?.addressLine1.isNullOrEmpty()) {
            primary_txt.text = "${ship?.addressLine1}"
            primary_txt2.text = "${ship?.city}"
        }

        if (!ship?.deliveryNote.isNullOrEmpty()) {
            edit_location.text = "Add delivery note"
        } else {
            edit_location.text = "Edit delivery note"
        }

        edit_location.setOnClickListener {
            val intent = Intent(
                this,
                ShippingActivity::class.java
            )
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!

        if (ship != null) {
            if (ship!!.lat != null) {
                setupMarker(LatLng(ship?.lat!!.toDouble(), ship?.lon!!.toDouble()))
            } else {
                val loc = LatLng(40.714890, -73.992690)
                setupMarker(loc)
            }
        }
    }

    private fun setupMarker(loc: LatLng) {
        map.addMarker(
            MarkerOptions()
                .position(loc)
                .title("You")
        )

        val position = CameraPosition.Builder()
            .target(loc)
            .zoom(16F)
            .tilt(20.0F)
            .build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

    private fun selectDeliveryFun() {

        if (SELECTDELIVERY == 0) {
            //logic
        } else {

        }

        deliver_btn.setOnClickListener {
            SELECTDELIVERY = 0
            deliver_btn.background = getMyDrawable(R.drawable.btn_bg_default)
            deliver_btn.setTextColor(getMyColor(R.color.white))

            pickup_btn.background = getMyDrawable(R.drawable.btn_bg_border)
            pickup_btn.setTextColor(getMyColor(R.color.realblack))
        }
        pickup_btn.setOnClickListener {
            SELECTDELIVERY = 1
            pickup_btn.background = getMyDrawable(R.drawable.btn_bg_default)
            pickup_btn.setTextColor(getMyColor(R.color.white))

            deliver_btn.background = getMyDrawable(R.drawable.btn_bg_border)
            deliver_btn.setTextColor(getMyColor(R.color.realblack))
        }
    }

    private fun selectPayment() {
        if (SELECTPAYMENT == 0) {
            //logic
        } else {

        }

        card_m_btn.setOnClickListener {
            SELECTPAYMENT = 0
            card_m_btn.background = getMyDrawable(R.drawable.button_round_1)
            card_m_btn_txt.setTextColor(getMyColor(R.color.white))

            cash_m_btn.background = getMyDrawable(R.drawable.round_border2)
            cash_m_btn_txt.setTextColor(getMyColor(R.color.realblack))
        }

        cash_m_btn.setOnClickListener {
            SELECTPAYMENT = 1
            cash_m_btn.background = getMyDrawable(R.drawable.button_round_1)
            cash_m_btn_txt.setTextColor(getMyColor(R.color.white))

            card_m_btn.background = getMyDrawable(R.drawable.round_border2)
            card_m_btn_txt.setTextColor(getMyColor(R.color.realblack))
        }

    }

    private fun checkOut() {

    }

    private fun updateTotals(dt: BasketListResponse) {
        tv_subtotal.text = "$ " + "${dt.subTotal}"
        tv_deliveryfee.text = "$ " + "${dt.deliveryFee}"
        tv_total.text = "$ " + "${dt.total}"
    }

    private fun setupCartList() {
        val lm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = lm
        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()


        vm.getBasket().observe(this, Observer {

            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: No cart foods")

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    if (!data!!.items.isNullOrEmpty()) {

                        updateTotals(data)

                        cartItemsAdapter = CartItemsFullAdapter(
                            this,
                            vm,
                            data.items?.toMutableList(),
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    //loading
                                    recycler_view.hideView()
                                    noitem_layout.hideView()
                                    loader_layout.showView()
                                }
                            }, object : UpdateCartClickListener {
                                override fun modelClick(model: Any, isUpdated: Boolean) {
                                    //just got updated
                                    recycler_view.showView()
                                    noitem_layout.hideView()
                                    loader_layout.hideView()

                                    updateTotals(model as BasketListResponse)

                                }
                            })
                        recycler_view.adapter = cartItemsAdapter

                    } else {

                        updateTotals(data)

                        recycler_view.hideView()
                        noitem_layout.showView()
                        loader_layout.hideView()

                    }
                }
                Status.LOADING -> {
                }
            }
        })
    }

}
