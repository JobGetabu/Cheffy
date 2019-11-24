package com.app.cheffyuser.home.adapter

import androidx.fragment.app.FragmentManager

class MainbottomAdapter(fragmentManager: FragmentManager) : BaseTabbarAdapter(fragmentManager) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            else -> ""
        }
    }
}