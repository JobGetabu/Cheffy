package com.app.cheffyuser.home.adapter

import androidx.fragment.app.FragmentManager

/**
 *
 * Used to quickly create all adapters
 * with caching technique of {@link #SmartFragmentStatePagerAdapter}
 *
 */
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