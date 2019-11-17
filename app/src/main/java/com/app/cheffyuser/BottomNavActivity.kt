package com.app.cheffyuserk

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ax.synt.droidlocation.DroidLocationAppCompatActivity
import ax.synt.droidlocation.DroidLocationRequestBuilder
import com.app.cheffyuser.cart.TabsFragment
import com.app.cheffyuser.custom_order.CustomOrderActivity
import com.app.cheffyuser.food_category.FoodCategoryFragment
import com.app.cheffyuser.home.fragments.UserHomeFragment
import com.app.cheffyuser.home.model.CurrentLocation
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.profile.fragments.AccountFragment
import com.app.cheffyuser.utils.TokenManager
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class BottomNavActivity : DroidLocationAppCompatActivity(), DroidListener {
    private var mTextMessage: TextView? = null
    private var fragment: Fragment? = null
    private var bottomNavigationView: BottomNavigationView? = null

    private lateinit var mDroidNet: DroidNet

    private var mCurrentLocation: LatLng? = null
    private var mCurrentLongtitide: Double? = null
    private var mCurrentLatitude: Double? = null
    private var mAddressText: String? = null
    private var isCon: Boolean = true

    val tokenManager: TokenManager = CheffyApp.instance!!.tokenManager

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = UserHomeFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {

                    fragment = FoodCategoryFragment()
                    loadFragment(fragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_custom_order -> {

                    val intent = Intent(this@BottomNavActivity, CustomOrderActivity::class.java)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_cart -> {
                    //mTextMessage.setText(R.string.title_notifications);
                    fragment = TabsFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    fragment = AccountFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        mDroidNet = DroidNet.getInstance()
        mDroidNet.addInternetConnectivityListener(this)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        mTextMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //loading the default fragment
        loadFragment(UserHomeFragment())
    }


    //loadFragment function
    private fun loadFragment(fragment: Fragment?) {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit()
        }
    }


    override fun onLocationPermissionGranted() {
        Timber.d("onLocationPermissionGranted() Lat =>$mCurrentLatitude Lon =>$mCurrentLongtitide")
    }

    override fun onLocationPermissionDenied() {
        Timber.d("onLocationPermissionDenied()")
    }

    override fun onLocationProviderEnabled() {
        Timber.d("onLocationProviderEnabled() => Location services are now ON")

        //Request location update
        requestMyCurrentLocation()
    }

    override fun onLocationProviderDisabled() {
        Timber.d("onLocationProviderDisabled() => Location services are still OFF")
    }

    override fun onLocationReceived(location: Location?) {
        mCurrentLatitude = location?.latitude
        mCurrentLongtitide = location?.longitude


        mCurrentLocation = LatLng(mCurrentLatitude!!, mCurrentLongtitide!!)
        Timber.d("onLocationReceived() Lat =>$mCurrentLatitude Lon =>$mCurrentLongtitide")

        //vm
        vm.mCurrentLatitude.value = mCurrentLatitude
        vm.mCurrentLongtitide.value = mCurrentLongtitide
        vm.mCurrentLocation.value = mCurrentLocation

        requestAddressServices(location)
        //if geocoder fails
        tokenManager.saveLastKnownLocation("Unnamed Road", mCurrentLatitude, mCurrentLongtitide)
        tokenManager.mCurrentLocation = CurrentLocation("Unnamed Road", mCurrentLatitude, mCurrentLongtitide)
    }

    override fun onLocationAddressReceived(fullAddress: String?) {
        Timber.d("onLocationAddressReceived() Address =>$fullAddress")
        mAddressText = fullAddress

        vm.mAddressText.value = fullAddress
        tokenManager.saveLastKnownLocation(fullAddress!!, mCurrentLatitude, mCurrentLongtitide)
        tokenManager.mCurrentLocation = CurrentLocation(fullAddress!!, mCurrentLatitude, mCurrentLongtitide)
    }

    private fun requestMyCurrentLocation() {
        val locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(5000)
            .setFastestInterval(5000)
        val droidLocationRequest = DroidLocationRequestBuilder()
            .setLocationRequest(locationRequest)
            .setFallBackToLastLocationTime(3000)
            .build()
        //requestSingleLocationFix(droidLocationRequest)
        requestLocationUpdates(droidLocationRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mDroidNet.isInitialized) {

            mDroidNet.removeInternetConnectivityChangeListener(this)
        }

        stopLocationUpdates()

    }

    //endregion
    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        isCon = isConnected
        if (isConnected) {
            //do Stuff with internet
        } else {
            //no internet

        }
    }

}
