package com.app.cheffyuser.cart.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.KeyboardUtils
import com.app.cheffyuser.utils.RoundedBottomSheetDialogFragment
import com.app.cheffyuser.utils.Tools.getFormattedDateSimple
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.single_plate_review.*

class SheetReview : RoundedBottomSheetDialogFragment() {

    companion object {
        const val TAG = "SheetReview"
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun getTheme(): Int = R.style.TipBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        reviewdate.text = "${getFormattedDateSimple(System.currentTimeMillis())}"

        reviewbtn.setOnClickListener {

            if (!reviewtxt.text.isNullOrEmpty()) {
                //TODO: push review
                createSnack(ctx = activity!!, txt = "Thank you!")
                dismiss()

            }
        }

        reviewtxt.requestFocus()

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        KeyboardUtils.hideKeyboard(activity!!)
    }
}