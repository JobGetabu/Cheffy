package com.app.cheffyuser.profile.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.profile.adapter.PlacesPredAdapter
import com.app.cheffyuser.profile.adapter.RecyclerPlaceClickListener
import com.app.cheffyuser.profile.model.DropdownItem
import com.app.cheffyuser.utils.KeyboardUtils
import com.app.cheffyuser.utils.showView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.my_places_autocomplete_main_fullscreen.*
import timber.log.Timber
import java.util.*


class PlaceSelectorActivity : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient
    private lateinit var placesAdapter: PlacesPredAdapter
    private lateinit var handler: Handler

    companion object {
        private var isDrop: Boolean = false
        fun getPlaceFromIntent(var0: Intent): DropdownItem {
            val x = var0.getParcelableExtra<Parcelable>("result")

            return x as DropdownItem
        }

        fun newIntent(context: Context, isDropadding: Boolean = false): Intent {
            isDrop = isDropadding
            return Intent(context, PlaceSelectorActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_places_autocomplete_main_fullscreen)

        // Create a new Places client instance.
        // Initialize Places.
        Places.initialize(applicationContext, getString(R.string.places_apikey))
        placesClient = Places.createClient(this)

        handler = Handler()
        uiStaff()
    }

    private fun uiStaff() {

        places_autocomplete_edit_text.requestFocus()

        if (isDrop) {
            extraplace_title.text = "Delivery address"
        }

        placesAdapter = PlacesPredAdapter(this, placesClient, object : RecyclerPlaceClickListener {
            override fun onPlaceClick(it: DropdownItem) {
                KeyboardUtils.hideKeyboard(this@PlaceSelectorActivity)

                val fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
                //run a task to get the place clicked
                val request = FetchPlaceRequest.builder(it.place!!.id!!, fields).build()
                placesClient.fetchPlace(request)
                    .addOnCompleteListener { ts ->
                        if (ts.isSuccessful) {

                            val pp = ts.result!!.place
                            it.place = pp

                            val returnIntent = Intent()
                            returnIntent.putExtra("result", it)
                            setResult(Activity.RESULT_OK, returnIntent)
                            finish()

                            Timber.d("seletedPickupItem: ${pp.address} ${pp.latLng}")
                        }
                    }
            }
        })

        val layoutman = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        places_autocomplete_list.layoutManager = layoutman
        places_autocomplete_list.setHasFixedSize(true)
        places_autocomplete_list.adapter = placesAdapter

        confirm_close.setOnClickListener {
            places_autocomplete_edit_text.setText("")
            placesAdapter.clearList()
        }

        toolbar_close.setOnClickListener {
            close()
        }

        confirm_back.setOnClickListener {
            close()
        }

        places_autocomplete_edit_text.addTextChangedListener(textWatcher(places_autocomplete_edit_text))

        places_autocomplete_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                places_autocomplete_list.showView()
            }
        }
    }

    private fun textWatcher(editable: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val query = editable.text

            if (editable.text.length > 2) {
                placesAdapter.filter.filter(query) {
                    if (it > 0) dest_progress.visibility = View.GONE else dest_progress.visibility =
                        View.VISIBLE

                    handler.postDelayed({ dest_progress.visibility = View.GONE }, 2000)
                }

                Timber.d("onTextChanged: => $query")

            } else {
                dest_progress.visibility = View.VISIBLE
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (editable.text.isEmpty()) {
                dest_progress.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        close()
    }

    private fun close() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }
}
