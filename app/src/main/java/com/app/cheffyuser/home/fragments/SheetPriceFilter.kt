package com.app.cheffyuser.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_price_list.*

class SheetPriceFilter : RoundedBottomSheetDialogFragment() {

    companion object {
        const val TAG = "SheetPriceFilter"
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_price_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_apply.setOnClickListener {

            //Testing filter
            vm.filterObj.value = "TODO: Price filter"

            dismiss()
        }
    }

}