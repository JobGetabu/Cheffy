package com.app.cheffyuser.profile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ShippingRequest
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.model.DropdownItem
import com.app.cheffyuser.utils.createSnack
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import kotlinx.android.synthetic.main.activity_shipping.*
import timber.log.Timber


class ShippingActivity : BaseActivity(), OnMapReadyCallback {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ShippingActivity::class.java)

        const val AUTOCOMPLETE_REQUEST_CODE = 1002
    }

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var map: GoogleMap
    private var ship: ShippingRequest? = null

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)


        mapFragment = (supportFragmentManager
            .findFragmentById(R.id.mMap) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)

        uiStuff()
    }

    private fun uiStuff() {

        edit_location.setOnClickListener {
            startActivityForResult(
                PlaceSelectorActivity.newIntent(this, true),
                AUTOCOMPLETE_REQUEST_CODE
            )
        }

        save_btn.setOnClickListener {
            saveAndClose()
        }

        //set up location\


        //init ship
        ship = ShippingRequest()
        val shipRes = tokenManager.shippingData
        ship!!.lat = shipRes?.lat
        ship!!.lon = shipRes?.lon
        ship!!.state = shipRes?.state
        ship!!.city = shipRes?.city
        ship!!.addressLine1 = shipRes?.addressLine1
        ship!!.addressLine2 = shipRes?.addressLine2
        ship!!.zipCode = shipRes?.zipCode

    }

    private fun saveAndClose() {
        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { saveAndClose() })
            return
        }

        if (ship == null) {
            createSnack(
                this, txt = "Please pick a location"
            )
            return
        }

        if (ship?.lon.isNullOrEmpty()) {
            createSnack(
                this, txt = "Please pick a location"
            )
            return
        }

        val dialog = showDialogue("Setting shipping location", "Please wait ...")

        //TODO: push shipping data to server
        vm.setShipping(ship!!).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Error setting shipping")

                    errorDialogue("Error", "Please try again later", dialog)
                    //checkNetwork()
                }
                Status.SUCCESS -> {

                    successDialogue(alertDialog = dialog)
                    finish()
                }
                Status.LOADING -> {

                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val dd = PlaceSelectorActivity.getPlaceFromIntent(data!!)
                    setupDefaultPickup(dd)

                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Timber.d(status.statusMessage)
                }
                RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }

    private fun setupDefaultPickup(dd: DropdownItem) {

        ship = ShippingRequest()

        primary_txt.text = "${dd.primaryText}"
        primary_txt2.text = "${dd.secondaryText}"

        etAddress.editText?.setText("${dd.place?.address}")

        ship?.zipCode = etzip.editText?.text.toString()
        ship?.addressLine1 = etAddress.editText?.toString()
        ship?.addressLine2 = etAddress2.editText?.toString()
        ship?.city = "${dd.place?.address}"
        ship?.lat = "${dd.place?.latLng?.latitude}"
        ship?.lon = "${dd.place?.latLng?.longitude}"

        tokenManager.shippingData2 = ship

        val loc = LatLng(ship?.lat!!.toDouble(), ship?.lon!!.toDouble())
        setupMarker(loc)

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
            MarkerOptions().position(loc)
                .title("Marker in Sydney")
        )

        val position = CameraPosition.Builder()
            .target(loc)
            .zoom(16F)
            .tilt(20.0F)
            .build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

}