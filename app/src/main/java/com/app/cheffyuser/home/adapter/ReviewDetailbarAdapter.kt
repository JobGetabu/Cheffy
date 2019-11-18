package com.app.cheffyuser.home.adapter

import androidx.fragment.app.FragmentManager

class ReviewDetailbarAdapter(fragmentManager: FragmentManager) : BaseTabbarAdapter(fragmentManager) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Details"
            1 -> "Review"
            else -> ""
        }
    }
}