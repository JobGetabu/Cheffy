package com.app.cheffyuser.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by Job on Thursday : 4/12/2018.
 */

//Swiping must be disabled. for the bottom nav... as we are using a view page
//NoSwipePager in your XML layout instead of a regular ViewPager


class NoSwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    init {
        enabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (enabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (enabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        Companion.enabled = enabled
    }

    companion object {
        private var enabled: Boolean = false
    }
}
