package com.app.cheffyuser.food_category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.utils.loadUrl

class FoodCatAdapter(
    private val context: Context,
    private val foodCats: MutableList<FoodCatModel>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_food_category, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return foodCats?.size ?: 0
    }

    fun add(foodCatModel: FoodCatModel) {
        foodCats!!.add(foodCatModel)
        notifyItemInserted(foodCats.size - 1)
    }

    private fun remove(foodCatModel: FoodCatModel) {
        val position = foodCats!!.indexOf(foodCatModel)
        if (position > -1) {
            foodCats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): FoodCatModel? {
        return if (position != RecyclerView.NO_POSITION)
            foodCats!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: FoodCatModel? = null

        //init views here
        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val name = itemView.findViewById<TextView>(R.id.name)


        init {

            image.setOnClickListener(this)
            name.setOnClickListener(this)

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = foodCats!![position]

            // set whatever you want. for instance;
            image.loadUrl(model?.url, R.drawable.food_image2)
            name.text = model?.name

        }

        override fun onClick(view: View) {
            this.clickListener.modelClick(model!!)
        }

        override fun clear() {}
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = foodCats!!.size
        foodCats.clear()
        notifyItemRangeRemoved(0, size)
    }
}
