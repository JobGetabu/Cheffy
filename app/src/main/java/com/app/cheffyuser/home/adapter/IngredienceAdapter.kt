package com.app.cheffyuser.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.model.PlatesResponse

class IngredienceAdapter(
    private val countTo3: Boolean = false,
    private val context: Context,
    private val ingredients: MutableList<PlatesResponse.Ingredient?>?
) : RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_ingredient_list, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        if (countTo3) return 2
        return ingredients?.size ?: 0
    }


    private fun getItem(position: Int): PlatesResponse.Ingredient? {
        return if (position != RecyclerView.NO_POSITION)
            ingredients!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View
    ) :
        BaseViewHolder(itemView) {
        private var model: PlatesResponse.Ingredient? = null

        //init views here
        private val counter = itemView.findViewById<TextView>(R.id.counter)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val date = itemView.findViewById<TextView>(R.id.date)

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = ingredients!![position]

            // set whatever you want. for instance;

            name.text = model?.name
            date.text = model?.purchaseDate
            counter.text = "$position  "

        }

        override fun clear() {}
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = ingredients!!.size
        ingredients.clear()
        notifyItemRangeRemoved(0, size)
    }
}
