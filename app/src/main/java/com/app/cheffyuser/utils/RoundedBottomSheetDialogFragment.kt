package com.app.cheffyuser.utils

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.app.cheffyuser.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BottomSheetDialog fragment that uses a custom
 * theme which sets a rounded background to the dialog
 * and doesn't dim the navigation bar
 */
open class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context!!, color)
    }
}