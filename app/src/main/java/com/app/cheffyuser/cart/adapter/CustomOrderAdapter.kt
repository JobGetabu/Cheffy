package com.app.cheffyuser.cart.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.models.CustomPlateResponseData
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.utils.loadUrl

class CustomOrderAdapter(
    private val context: Activity,
    private val items: MutableList<CustomPlateResponseData?>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_custom_order_row, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun add(item: CustomPlateResponseData) {
        items!!.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun remove(item: CustomPlateResponseData) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) : BaseViewHolder(itemView), View.OnClickListener {

        private var model: CustomPlateResponseData? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.img_food)
        private val foodname = itemView.findViewById<TextView>(R.id.txt_item_name)
        private val bids = itemView.findViewById<TextView>(R.id.txt_bids)
        private val txt_viewall = itemView.findViewById<TextView>(R.id.txt_viewall)


        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            if (!model?.customPlateImages.isNullOrEmpty())
                foodimage.loadUrl(model!!.customPlateImages!![0]!!.url)

            foodname.text = "${model!!.name}"

            val bidCount = model?.customPlateAuction?.bidCount
            if (bidCount == null) bids.text = "(0) Chef"
            else bids.text = "($bidCount) Chef"
        }

        init {
            txt_viewall.setOnClickListener(this)
            foodimage.setOnClickListener(this)
            foodname.setOnClickListener(this)
            bids.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun clear() {}

        override fun onClick(v: View?) {
            clickListener.modelClick(model!!)
        }
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = items!!.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}
