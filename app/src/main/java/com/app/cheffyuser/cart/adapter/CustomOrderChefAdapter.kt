package com.app.cheffyuser.cart.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.CustomPlateAuctionBids
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl

class CustomOrderChefAdapter(
    private val context: Activity,
    private val items: MutableList<CustomPlateAuctionBids>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_custom_order_list_row, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun add(item: CustomPlateAuctionBids) {
        items!!.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun remove(item: CustomPlateAuctionBids) {
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

        private var model: CustomPlateAuctionBids? = null

        //init views here
        private val chef_pic = itemView.findViewById<ImageView>(R.id.chef_pic)
        private val txt_chef_name = itemView.findViewById<TextView>(R.id.txt_chef_name)
        private val txt_price = itemView.findViewById<TextView>(R.id.txt_price)
        private val times = itemView.findViewById<TextView>(R.id.times)

        private val btn_accept = itemView.findViewById<Button>(R.id.btn_accept)
        private val btn_reject = itemView.findViewById<Button>(R.id.btn_reject)


        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            chef_pic.loadUrl(model?.chef?.imagePath, R.drawable.avatar_placeholder)
            txt_chef_name.text = model?.chef?.name
            txt_price.text = "$" + "${model?.price}"
            times.text = "${model!!.preparationTime!!.minus(5)}-${model?.preparationTime} min"
        }

        init {

            btn_accept.setOnClickListener {
                acceptBid()
            }

            btn_reject.setOnClickListener {
                createSnack(
                    ctx = context,
                    txt = "Reject this bid",
                    txtAction = "Confirm",
                    action = View.OnClickListener {
                        rejectBid()
                    }
                )
            }


        }

        private fun acceptBid(){

        }

        private fun rejectBid() {
            //todo: remove bid
            remove(model!!)
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