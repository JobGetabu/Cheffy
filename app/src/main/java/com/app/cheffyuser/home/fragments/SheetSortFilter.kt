package com.app.cheffyuser.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_sort_list.*

class SheetSortFilter : RoundedBottomSheetDialogFragment() {

    private var selectedSortFilter = 0

    companion object {
        const val TAG = "SheetSortFilter"

        const val DEFAULT = 0
        const val POPULAR = 1
        const val RATING = 2
        const val DELIVERY = 3

    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_sort_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_apply.setOnClickListener {

            //Testing filter
            vm.selectedSortFilter.value = selectedSortFilter

            dismiss()
        }


        item0.setOnClickListener { sortClick(item0) }
        img0.setOnClickListener { sortClick(img0) }
        txt0.setOnClickListener { sortClick(txt0) }

        item1.setOnClickListener { sortClick(item1) }
        img1.setOnClickListener { sortClick(img1) }
        txt1.setOnClickListener { sortClick(txt1) }

        item2.setOnClickListener { sortClick(item2) }
        img2.setOnClickListener { sortClick(img2) }
        txt2.setOnClickListener { sortClick(txt2) }

        item3.setOnClickListener { sortClick(item3) }
        img3.setOnClickListener { sortClick(img3) }
        txt3.setOnClickListener { sortClick(txt3) }
    }

    private fun sortClick(v: View) {

        img0.setColorFilter(getColor(R.color.white))
        img1.setColorFilter(getColor(R.color.white))
        img2.setColorFilter(getColor(R.color.white))
        img3.setColorFilter(getColor(R.color.white))

        when (v.id) {

            R.id.item0, R.id.txt0, R.id.img0 -> {
                img0.setColorFilter(getColor(R.color.appgreen))
                selectedSortFilter = DEFAULT
            }

            R.id.item1, R.id.txt1, R.id.img1 -> {
                img1.setColorFilter(getColor(R.color.appgreen))
                selectedSortFilter = POPULAR
            }

            R.id.item2, R.id.txt2, R.id.img2 -> {
                img2.setColorFilter(getColor(R.color.appgreen))
                selectedSortFilter = RATING
            }

            R.id.item3, R.id.txt3, R.id.img3 -> {
                img3.setColorFilter(getColor(R.color.appgreen))
                selectedSortFilter = DELIVERY
            }

        }
    }
}