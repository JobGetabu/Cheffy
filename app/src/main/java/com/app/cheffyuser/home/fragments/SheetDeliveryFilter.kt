package com.app.cheffyuser.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_delivery_list.*
import kotlinx.android.synthetic.main.sheet_diet_list.btn_apply

class SheetDeliveryFilter : RoundedBottomSheetDialogFragment() {

    companion object {
        const val TAG = "SheetDeliveryFilter"

        const val LOWER = "LOWER"
        const val LOW = "LOW"
        const val MEDIUM = "MEDIUM"
        const val HIGH = "HIGH"
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    private var deliveryFilter = "DEFAULT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_delivery_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_apply.setOnClickListener {

            //Testing filter
            vm.filterObj.value = "TODO: Delivery filter"

            dismiss()
        }

        item0.setOnClickListener { deliveryClicks(item0) }
        item1.setOnClickListener { deliveryClicks(item1) }
        item2.setOnClickListener { deliveryClicks(item2) }
        item3.setOnClickListener { deliveryClicks(item3) }

    }

    private fun deliveryClicks(v: View) {

        item0.setBackgroundColor(getColor(R.color.white))
        txt0.setTextColor(getColor(R.color.realblack))

        item1.setBackgroundColor(getColor(R.color.white))
        txt1.setTextColor(getColor(R.color.realblack))

        item2.setBackgroundColor(getColor(R.color.white))
        txt2.setTextColor(getColor(R.color.realblack))

        item3.setBackgroundColor(getColor(R.color.white))
        txt3.setTextColor(getColor(R.color.realblack))

        v as RelativeLayout
        v.setBackgroundColor(getColor(R.color.realblack))

        when (v.id) {
            R.id.item0 -> {
                txt0.setTextColor(getColor(R.color.white))
                deliveryFilter = LOWER
            }
            R.id.item1 -> {
                txt1.setTextColor(getColor(R.color.white))
                deliveryFilter = LOW
            }
            R.id.item2 -> {
                txt2.setTextColor(getColor(R.color.white))
                deliveryFilter = MEDIUM
            }
            R.id.item3 -> {
                txt3.setTextColor(getColor(R.color.white))
                deliveryFilter = HIGH
            }
        }

    }

}