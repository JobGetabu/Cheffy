package com.app.cheffyuser.home.adapter


/**
 * A generic recycler view click listener
 * that accepts any type
 * and injects action
 */
interface RecyclerCheckBoxClickListener {
    fun modelClick(model: Any, check: Boolean)
}
