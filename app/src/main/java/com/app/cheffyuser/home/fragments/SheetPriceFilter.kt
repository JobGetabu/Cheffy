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

    private var lowerBool = false
    private var lowBool = false
    private var mediumBool = false
    private var highBool = false

    private val priceBool: MutableList<Boolean> = arrayListOf(false, false, false, false)

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
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

            priceBool.add(0, lowerBool)
            priceBool.add(1, lowBool)
            priceBool.add(2, mediumBool)
            priceBool.add(3, highBool)


            vm.selectedPriceFilter.value = priceBool

            dismiss()
        }

        priceClicks()
    }

    private fun priceClicks() {

        item0.setOnClickListener {
            lowerBool = if (lowerBool) {

                item0.setBackgroundColor(getColor(R.color.white))
                txt0.setTextColor(getColor(R.color.realblack))
                false

            } else {
                item0.setBackgroundColor(getColor(R.color.realblack))
                txt0.setTextColor(getColor(R.color.white))
                true
            }
        }

        item1.setOnClickListener {
            lowBool = if (lowBool) {

                item1.setBackgroundColor(getColor(R.color.white))
                txt1.setTextColor(getColor(R.color.realblack))
                false

            } else {
                item1.setBackgroundColor(getColor(R.color.realblack))
                txt1.setTextColor(getColor(R.color.white))
                true
            }
        }

        item2.setOnClickListener {
            mediumBool = if (mediumBool) {

                item2.setBackgroundColor(getColor(R.color.white))
                txt2.setTextColor(getColor(R.color.realblack))
                false

            } else {
                item2.setBackgroundColor(getColor(R.color.realblack))
                txt2.setTextColor(getColor(R.color.white))
                true
            }
        }

        item3.setOnClickListener {
            mediumBool = if (mediumBool) {

                item3.setBackgroundColor(getColor(R.color.white))
                txt3.setTextColor(getColor(R.color.realblack))
                false

            } else {
                item3.setBackgroundColor(getColor(R.color.realblack))
                txt3.setTextColor(getColor(R.color.white))
                true
            }
        }

    }

}