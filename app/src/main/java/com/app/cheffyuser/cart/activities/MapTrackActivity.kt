package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapTrackActivity : BaseActivity(), OnMapReadyCallback {


    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, MapTrackActivity::class.java)
    }

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var map: GoogleMap

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_track)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        mapFragment = (supportFragmentManager
            .findFragmentById(R.id.mMap) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)

        uiStuff()
    }

    private fun uiStuff() {

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!

        map.setOnMapLoadedCallback {
            OnMapReadyCallback {

                //TODO: refactor
                // Add a thin red line from London to New York.
                it.addPolyline(
                    PolylineOptions()
                        .add(LatLng(38.8948932, -77.0365529), LatLng(38.8948932, -77.0365529))
                        .width(5f)
                        .color(Color.RED)
                )

                //addMarker(LatLng(5.03284353, -42.8176576), R.drawable.map_marker_st)
                addMarker(LatLng(38.8948932, -77.0365529), R.drawable.ic_map_marker_pt)

                val latLngBounds = LatLngBounds.Builder()
                    .include(LatLng(38.8948932, -77.0365529))
                    .include(LatLng(38.8948932, -77.0365529))
                    .build()

                val position = CameraPosition.Builder()
                    .target(LatLng(38.8948932, -77.0365529))
                    .zoom(16F)
                    .tilt(20.0F)
                    .build()

                it.animateCamera(CameraUpdateFactory.newCameraPosition(position))

                //it.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50))
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

    private fun addMarker(loc: LatLng, @DrawableRes icon: Int) {

        map.addMarker(
            MarkerOptions()
                .position(loc)
                .draggable(false)
                .icon(bitmapDescriptorFromVector(this, icon))
        )

    }

    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor? {
        val background =
            ContextCompat.getDrawable(context, R.drawable.ic_map_marker_pt)
        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable =
            ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            40,
            20,
            vectorDrawable.intrinsicWidth + 40,
            vectorDrawable.intrinsicHeight + 20
        )
        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}
