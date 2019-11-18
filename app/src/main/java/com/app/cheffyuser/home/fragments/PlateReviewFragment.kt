package com.app.cheffyuser.home.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.ReviewsAdapter
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_plate_review.*


/**
 * A simple [Fragment] subclass.
 */
class PlateReviewFragment : Fragment() {

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plate_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        reviewlist.setHasFixedSize(true)

        val reviews = vm.platesResponse.value?.reviews

        reviews.apply {
            val adapter = ReviewsAdapter(context!!, this?.toMutableList())
            reviewlist.adapter = adapter
        }

    }

}
