package com.app.cheffyuser.cart.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.ItemCartAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_item_cart.*

class ItemCartActivity : AppCompatActivity(), OnMapReadyCallback {


    internal var foodItemList = arrayOf("Grilled salmon", "Pasta Ham")
    internal var foodPriceList = doubleArrayOf(96.00, 120.00)
    internal var imgList = intArrayOf(R.drawable.img_food_1, R.drawable.img_food_2)

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_cart)


        // set a LinearLayoutManager with default vertical orientation
        val linearLayoutManager = LinearLayoutManager(this@ItemCartActivity)

        // set a LinearLayoutManager with default Horizontal orientation
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recycler_view.layoutManager = linearLayoutManager

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        val customAdapter =
            ItemCartAdapter(this@ItemCartActivity, foodItemList, foodPriceList, imgList)
        recycler_view.adapter = customAdapter // set the Adapter to RecyclerView

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this@ItemCartActivity)

        add_card_lay.setOnClickListener {
            val intent = Intent(this@ItemCartActivity, AddCardActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val latLng = LatLng(38.907192, -77.036873)
        mMap!!.addMarker(MarkerOptions().position(latLng))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))


        //for normal map view
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

        //for sattelite map view
        // mMap.setMapType(googleMap.MAP_TYPE_SATELLITE);

        //for add zoom in maps
        val zoomLevel = 16.0f //This goes up to 21
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
    }
}
