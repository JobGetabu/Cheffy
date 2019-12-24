package com.app.cheffyuser.cart.adapter


/**
 * A generic recycler view click listener
 * that accepts any type
 * and injects action
 */
interface UpdateCartClickListener {
    fun modelClick(model: Any, isUpdated: Boolean)
}
