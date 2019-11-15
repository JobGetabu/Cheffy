package com.app.cheffyuser.home.adapter


/**
 * A generic recycler view click listener
 * that accepts any type
 * and injects action
 */
interface RecyclerItemClickListener {
    fun modelClick(model: Any)
}
