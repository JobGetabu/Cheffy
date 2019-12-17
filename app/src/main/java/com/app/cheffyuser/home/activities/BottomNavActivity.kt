package com.app.cheffyuser.home.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ax.synt.droidlocation.DroidLocationAppCompatActivity
import ax.synt.droidlocation.DroidLocationRequestBuilder
import br.com.mauker.materialsearchview.MaterialSearchView
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.TabsFragment
import com.app.cheffyuser.create_account.fragments.NeedToLoginFragment
import com.app.cheffyuser.custom_order.CustomOrderActivity
import com.app.cheffyuser.food_category.FoodCategoryFragment
import com.app.cheffyuser.home.adapter.MainbottomAdapter
import com.app.cheffyuser.home.fragments.NoNetListener
import com.app.cheffyuser.home.fragments.NoNetworkDialogue
import com.app.cheffyuser.home.fragments.UserHomeFragment
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.fragments.AccountFragment
import com.app.cheffyuser.utils.TokenManager
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.toast
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.android.synthetic.main.activity_bottom_nav.*
import timber.log.Timber


class BottomNavActivity : DroidLocationAppCompatActivity(), DroidListener,
    InstallStateUpdatedListener {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, BottomNavActivity::class.java)

        private const val UPDATE_REQUEST_CODE = 183
    }

    private var mTextMessage: TextView? = null
    private var fragment: Fragment? = null
    private var bottomNavigationView: BottomNavigationView? = null

    lateinit var searchView: MaterialSearchView

    private lateinit var mDroidNet: DroidNet

    private var mCurrentLocation: LatLng? = null
    private var mCurrentLongtitide: Double? = null
    private var mCurrentLatitude: Double? = null
    private var mAddressText: String? = null
    private var isCon: Boolean = true

    private val tokenManager: TokenManager = CheffyApp.instance!!.tokenManager

    // Create instance of the IAUs manager.
    private val appUpdateManager by lazy {
        AppUpdateManagerFactory.create(this).also { it.registerListener(this) }
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    pager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    pager.currentItem = 1

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_custom_order -> {

                    val intent = Intent(this@BottomNavActivity, CustomOrderActivity::class.java)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_cart -> {

                    pager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {

                    if (tokenManager.isLoggedIn) {

                        pager.currentItem = 3
                    } else {
                        pager.currentItem = 4
                    }

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onStart() {
        super.onStart()

        //Request location update
        requestMyCurrentLocation()

        //delay a sec to see there's net
        Handler().postDelayed({
            if (!isCon) {
                netIsOff()
            }
        }, 5000)

        checkIfUpdateWasUnderWay()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        mDroidNet = DroidNet.getInstance()
        mDroidNet.addInternetConnectivityListener(this)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        searchView = findViewById(R.id.search_view)

        mTextMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //setup vp
        pager.setPagingEnabled(false)

        val adapter = MainbottomAdapter(supportFragmentManager)
        adapter.addFragments(UserHomeFragment())
        adapter.addFragments(FoodCategoryFragment())
        adapter.addFragments(TabsFragment())
        adapter.addFragments(AccountFragment())
        adapter.addFragments(NeedToLoginFragment())
        pager.adapter = adapter

        pager.offscreenPageLimit = 4
        pager.currentItem = 0


        currentItemObserver()

        searchUtils()

        appUpdatePrep()

        searchPredcts()
    }

    private fun currentItemObserver() {
        vm.pagerCurrentItem.observe(this, Observer {

            if (it == 1) {
                nav_view.selectedItemId = R.id.navigation_category
            }
        })
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
        tokenManager.mCurrentLocation =
            CurrentLocation("Unnamed Road", mCurrentLatitude, mCurrentLongtitide)
    }

    override fun onLocationAddressReceived(fullAddress: String?) {
        Timber.d("onLocationAddressReceived() Address =>$fullAddress")
        mAddressText = fullAddress

        vm.mAddressText.value = fullAddress
        tokenManager.saveLastKnownLocation(fullAddress!!, mCurrentLatitude, mCurrentLongtitide)
        tokenManager.mCurrentLocation =
            CurrentLocation(fullAddress!!, mCurrentLatitude, mCurrentLongtitide)
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

        appUpdateManager.unregisterListener(this)

    }

    //endregion
    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        isCon = isConnected
        Timber.d("net state=> $isConnected")
    }

    private fun netIsOff() {
        if (vm.isFirstLaunch == 1) {

            checkNetwork()
            vm.isFirstLaunch += 1
        }
    }

    fun checkNetwork() {

        //access bundle from viewmodel to change
        vm.isForNet = true

        val fragmentManager = supportFragmentManager
        val newFragment = NoNetworkDialogue()
        newFragment.setOnNetListener(object : NoNetListener {
            override fun onNetComeBack() {
                toast("TODO: test if Recreate this activity is actually necessary")
                //recreate()
            }
        })

        try {
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

        } catch (e: IllegalStateException) {
            //Occurs on very fast switching
            Timber.e(e)
        }
    }

    override fun onPause() {
        super.onPause()
        searchView.clearSuggestions()
    }

    override fun onResume() {
        super.onResume()

        searchView.activityResumed()
        searchPredcts()


        checkIfUpdateWasUnderWay()
    }

    override fun onStop() {
        super.onStop()

        stopLocationUpdates()
    }

    override fun onBackPressed() {

        if (searchView.isOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === MaterialSearchView.REQUEST_VOICE && resultCode === Activity.RESULT_OK) {
            val matches: ArrayList<String>? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false)
                }
            }
            return
        }

        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != AppCompatActivity.RESULT_OK) {
                Timber.d("Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }


    }


    //region UPDATE APP CODE

    private fun appUpdatePrep() {

        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE, this, UPDATE_REQUEST_CODE
                        )
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, this, UPDATE_REQUEST_CODE
                        )
                    }
                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    Timber.d("update check - no new update")
                }
            }
        }.addOnFailureListener {
            Timber.d("Failed to request update check")
        }

    }

    private fun startUpdateFlow(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            // Pass the intent that is returned by 'getAppUpdateInfo()'.
            appUpdateInfo,
            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
            AppUpdateType.IMMEDIATE,
            // The current activity making the update request.
            this,
            // Include a request code to later monitor this update request.
            UPDATE_REQUEST_CODE
        )
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            createSnack(this, "An update has just been downloaded.",
                "RESTART", true, View.OnClickListener {

                    //Deleting all Shared preferences on update
                    tokenManager.DELETEALLPREFS()

                    appUpdateManager.completeUpdate()
                })
        }
    }

    private fun checkIfUpdateWasUnderWay() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    // If an in-app update is already running, resume the update.
                    startUpdateFlow(appUpdateInfo)
                }
            }
    }

    //endregion

    //region search predictions

    private fun searchPredcts() {
        vm.searchPredictions.value = mutableListOf()
        vm.getSearchPredictions().observe(this, Observer { dt ->
            val datas = dt.data

            when (dt.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: search predictions not found")

                }
                Status.SUCCESS -> {

                    vm.predictionsResponse = datas!!
                    val temp: MutableList<String> = mutableListOf()

                    if (!datas!!.typeCategory.isNullOrEmpty()) {
                        datas.typeCategory?.forEach {
                            temp.add(it!!.name!!)
                        }
                    }

                    if (!datas.typePlate.isNullOrEmpty()) {
                        datas.typePlate.forEach {
                            temp.add(it!!.name!!)
                        }
                    }

                    if (!datas.typeChef.isNullOrEmpty()) {
                        datas.typeChef.forEach {
                            temp.add(it!!.chef?.restaurantName!!)
                        }
                    }

                    vm.searchPredictions.value = temp

                }
                Status.LOADING -> {
                    //still loading data
                }
            }

        })


        vm.searchPredictions.observe(this, Observer {

            if (it.isNotEmpty()) {
                searchView.addSuggestions(it)
            }
        })
    }


    private fun searchUtils() {
        searchView.setOnItemClickListener { _, _, position, id ->

            // Do something when the suggestion list is clicked.
            val suggestion = searchView.getSuggestionAtPosition(position)

            searchView.setQuery(suggestion, true)

        }


        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                vm.searchTerm.value = query
                knowIfSuggestion(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }

    private fun knowIfSuggestion(suggestion: String): SearchResult? {
        //search
        vm.predictionsResponse.typeChef!!.forEach {
            if (it!!.chef!!.restaurantName.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_CHEF, it.userId)
                return vm.searchResult
            }
        }

        vm.predictionsResponse.typePlate!!.forEach {
            if (it!!.name.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_PLATE, it.id)
                return vm.searchResult
            }
        }

        vm.predictionsResponse.typeCategory!!.forEach {
            if (it!!.name.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_CATEGORY, it.id)
                return vm.searchResult
            }
        }

        //at this point is not predicted. so <text> search
        vm.searchResult = SearchResult(SEARCH_TEXT, null)
        return vm.searchResult
    }

    //endregion

}
