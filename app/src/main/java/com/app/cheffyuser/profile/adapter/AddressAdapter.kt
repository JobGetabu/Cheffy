package com.app.cheffyuser.profile.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.ShippingData
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener

class AddressAdapter(
    private val context: Activity,
    private val items: MutableList<ShippingData>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_address, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private fun remove(item: ShippingData) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {

        private var model: ShippingData? = null

        //init views here
        private val img1 = itemView.findViewById<ImageView>(R.id.img1)
        private val pritxt = itemView.findViewById<TextView>(R.id.txt1)
        private val sectxt = itemView.findViewById<TextView>(R.id.txt)
        private val body = itemView.findViewById<ConstraintLayout>(R.id.item1)

        init {

            img1.setOnClickListener(this)
            pritxt.setOnClickListener(this)
            sectxt.setOnClickListener(this)
            body.setOnClickListener(this)

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            pritxt.text = model?.addressLine1
            sectxt.text = model?.city

            if (model!!.isDefaultAddress!!) {
                img1.setColorFilter(getMyColor(R.color.appgreen))

            } else {
                img1.setColorFilter(getMyColor(R.color.white))
            }
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
        val size = items!!.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getMyColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}
