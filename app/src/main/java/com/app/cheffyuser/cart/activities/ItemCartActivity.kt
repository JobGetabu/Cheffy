package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ShippingData
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_item_cart.*

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

        ship = tokenManager.shippingData

        btn_checkout.setOnClickListener {
            checkOut()
        }

        selectDeliveryFun()
        selectPayment()
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

}
