package com.app.cheffyuser.profile.adapter

import android.widget.Filter
import com.app.cheffyuser.profile.model.DropdownItem
import com.app.cheffyuser.utils.AppExecutors
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import timber.log.Timber
import java.util.*

class SearchLocationFilter(private val mAdapter: PlacesPredAdapter, private val placesClient: PlacesClient) : Filter() {
    private var placeResults = false

    // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
    // and once again when the user makes a selection (for example when calling fetchPlace()).
    private val token = AutocompleteSessionToken.newInstance()
    private var dropdownItems: List<DropdownItem>? = null

    init {
        dropdownItems = ArrayList()
    }


    override fun performFiltering(prefix: CharSequence?): Filter.FilterResults {
        val results = Filter.FilterResults()
        placeResults = false
        val placesList = ArrayList<Place>()
        val items = ArrayList<DropdownItem>()

        if (prefix == null || prefix.length == 0) {

            results.values = ArrayList<DropdownItem>()
            results.count = 0

        } else {
            val searchStrLowerCase = prefix.toString().toLowerCase().trim { it <= ' ' }

            Timber.d("search term: $searchStrLowerCase")

            getAutocompletePredictions(searchStrLowerCase).addOnCompleteListener(
                AppExecutors().mainThread(),
                OnCompleteListener { task ->
                    var dropdownItem: DropdownItem

                if (task.isSuccessful) {
                    val autocompletePredictions = task.result!!.autocompletePredictions

                    for (prediction in autocompletePredictions) {
                        Timber.d(prediction.placeId)
                        Timber.d(prediction.getPrimaryText(null).toString())

                        dropdownItem = DropdownItem()
                        dropdownItem.primaryText = prediction.getPrimaryText(null).toString()
                        dropdownItem.secondaryText = prediction.getSecondaryText(null).toString()

                        val place = Place.builder()
                            .setId(prediction.placeId)
                            .setTypes(prediction.placeTypes)
                            .setName(prediction.getFullText(null).toString())
                            .build()
                        dropdownItem.place = place

                        placesList.add(place)
                        items.add(dropdownItem)


                    }

                    dropdownItems = items
                    mAdapter.setDropdownItems(dropdownItems as ArrayList<DropdownItem>)
                    mAdapter.notifyDataSetChanged()

                    for (t: DropdownItem  in dropdownItems!!) {
                        Timber.d("Auto complete => id=" + t.place!!.getId() + " " + t.primaryText + " " + t.secondaryText)
                    }

                    Timber.d("Auto complete predictions size " + dropdownItems!!.size)
                } else {
                    Timber.d("Auto complete prediction unsuccessful")
                }


                placeResults = true
            }).addOnFailureListener(AppExecutors().mainThread(), OnFailureListener { e ->
                if (e is ApiException) {
                    Timber.d("Place not found: " + e.statusCode)
                    Timber.d("Place not found: " + e.message)

                    placeResults = true
                }
            })

            results.values = items
            results.count = items.size
            Timber.d("Autocomplete predictions size after wait" + results.count)
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        dropdownItems = results.values as ArrayList<DropdownItem>
        mAdapter.setDropdownItems(dropdownItems as ArrayList<DropdownItem>)
        mAdapter.notifyDataSetChanged()
    }


    private fun getAutocompletePredictions(query: String): Task<FindAutocompletePredictionsResponse> {

        //create autocomplete filter using data from filter Views
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request = FindAutocompletePredictionsRequest.builder()
            // Call either setLocationBias() OR setLocationRestriction().
            //.setLocationRestriction(bounds)
            //.setCountry("KE")
            .setCountry("US")
            /*  .setTypeFilter(TypeFilter.ESTABLISHMENT)
                  .setTypeFilter(TypeFilter.GEOCODE)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)

            .setTypeFilter(TypeFilter.ADDRESS)
                  .setTypeFilter(TypeFilter.REGIONS)*/

            .setSessionToken(token)
            .setQuery(query)
            .build()

        return placesClient.findAutocompletePredictions(request)
    }

    companion object {
        private val TAG = "places"
    }
}
