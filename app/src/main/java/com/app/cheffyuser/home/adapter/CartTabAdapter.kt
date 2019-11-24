package com.app.cheffyuser.home.adapter

import androidx.fragment.app.FragmentManager

/**
 * and also the Tabs in the Cart food page
 *
 */
class CartTabAdapter(fragmentManager: FragmentManager) : BaseTabbarAdapter(fragmentManager) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Add Cart"
            1 -> "Custom Order"
            2 -> "Tracking Order"
            3 -> "Delivery Complete"
            else -> ""
        }
    }
}