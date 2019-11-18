package com.app.cheffyuser.home.adapter

import androidx.fragment.app.FragmentManager

class DetailMainbarAdapter(fragmentManager: FragmentManager) : BaseTabbarAdapter(fragmentManager) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "The Plate"
            1 -> "Kitchen"
            2 -> "Receipts"
            else -> ""
        }
    }
}