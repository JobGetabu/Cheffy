package com.app.cheffyuser.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.models.PeopleAddedResponse
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerCheckBoxClickListener

class FoodOtherSelectedAdapter(
    private val context: Context,
    private val items: MutableList<PeopleAddedResponse>?,
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
        return items!!.size
    }

    fun add(item: PeopleAddedResponse) {
        items!!.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun remove(item: PeopleAddedResponse) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): PeopleAddedResponse? {
        return if (position != RecyclerView.NO_POSITION)
            items!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerCheckBoxClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: PeopleAddedResponse? = null

        //init views here

        private val foodname = itemView.findViewById<TextView>(R.id.foodname)
        private val foodprice = itemView.findViewById<TextView>(R.id.foodprice)
        private val foodcheck = itemView.findViewById<CheckBox>(R.id.foodcheck)

        init {
            foodcheck.setOnCheckedChangeListener { _, isChecked ->
                this.clickListener.modelClick(model!!, isChecked)
            }

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            foodname.text = model?.name
            foodprice.text = "$ ${model?.price}"

        }

        override fun clear() {}
        override fun onClick(v: View?) {

        }
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

}
