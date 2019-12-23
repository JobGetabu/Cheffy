package com.app.cheffyuser.cart.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.models.BasketItems
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl
import com.app.cheffyuser.utils.toast

class CartItemsAdapter(
    private val context: Activity,
    private val items: MutableList<BasketItems?>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_cart_order_row, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun add(item: BasketItems) {
        items!!.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun remove(item: BasketItems) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) : BaseViewHolder(itemView) {

        private var model: BasketItems? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.img_food)
        private val delImg = itemView.findViewById<ImageView>(R.id.del_img)
        private val foodname = itemView.findViewById<TextView>(R.id.txt_item_name)
        private val price = itemView.findViewById<TextView>(R.id.txt_price)

        private val itemCount = itemView.findViewById<TextView>(R.id.counter)
        private val minus = itemView.findViewById<ImageView>(R.id.minus_img)
        private val plus = itemView.findViewById<ImageView>(R.id.plus_img)

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            itemCount.text = "${model!!.quantity}"
            price.text = "$" + "${model!!.totalValue}"

            foodimage.loadUrl(model!!.plate!!.name)
            foodname.text = "${model!!.plate!!.name}"

        }

        init {
            minus.setOnClickListener {
                this.clickListener.modelClick(model!!)
                context.toast("TODO: Minus item ${model?.plate?.name}")
            }

            plus.setOnClickListener {
                this.clickListener.modelClick(model!!)
                context.toast("TODO: Plus item ${model?.plate?.name}")
            }

            delImg.setOnClickListener {
                this.clickListener.modelClick(model!!)
                createSnack(ctx = context, txt = "Removed ${model?.plate?.name}")
                remove(model!!)
            }

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
}
