package com.app.cheffyuser.profile.adapter

import androidx.fragment.app.FragmentManager
import com.app.cheffyuser.home.adapter.BaseTabbarAdapter

class ProfilebarAdapter(fragmentManager: FragmentManager) : BaseTabbarAdapter(fragmentManager) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Your Favorite"
            1 -> "Account Setting"
            else -> ""
        }
    }
}