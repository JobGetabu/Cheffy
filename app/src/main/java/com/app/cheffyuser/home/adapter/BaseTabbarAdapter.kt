package com.app.cheffyuser.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.cheffyuser.utils.SmartFragmentStatePagerAdapter
import java.util.*

/**
 *
 * Used to quickly create all adapters
 * with caching technique of {@link #SmartFragmentStatePagerAdapter}
 *
 */
abstract class BaseTabbarAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    val fragments = ArrayList<Fragment>()
    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Small"
            1 -> "Medium"
            2 -> "Large"
            else -> ""
        }
    }
}