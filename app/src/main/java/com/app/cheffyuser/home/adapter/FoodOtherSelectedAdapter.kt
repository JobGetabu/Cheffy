package com.app.cheffyuser.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.model.PlatesResponse

class FoodOtherSelectedAdapter(
    private val context: Context,
    private val foodNearbyModels: MutableList<PlatesResponse>?,
    private val clickListener: RecyclerCheckBoxClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_pple_added, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return foodNearbyModels!!.size
    }

    fun add(platesResponse: PlatesResponse) {
        foodNearbyModels!!.add(platesResponse)
        notifyItemInserted(foodNearbyModels.size - 1)
    }

    private fun remove(platesResponse: PlatesResponse) {
        val position = foodNearbyModels!!.indexOf(platesResponse)
        if (position > -1) {
            foodNearbyModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): PlatesResponse? {
        return if (position != RecyclerView.NO_POSITION)
            foodNearbyModels!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerCheckBoxClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: PlatesResponse? = null

        //init views here

        private val foodname = itemView.findViewById<TextView>(R.id.foodname)
        private val foodprice = itemView.findViewById<TextView>(R.id.foodprice)
        private val foodcheck = itemView.findViewById<CheckBox>(R.id.foodcheck)

        init {

            foodcheck.setOnClickListener(this)
        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = foodNearbyModels!![position]

            foodname.text = model?.name
            foodprice.text = "$ ${model?.price}"


        }

        override fun onClick(view: View) {

            foodcheck.setOnCheckedChangeListener { _, isChecked ->
                this.clickListener.modelClick(model!!, isChecked)
            }

        }

        override fun clear() {}
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

}
