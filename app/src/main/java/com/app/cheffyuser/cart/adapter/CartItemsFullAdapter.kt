package com.app.cheffyuser.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.models.BasketItems
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.loadUrl
import com.app.cheffyuser.utils.toast

class CartItemsFullAdapter(
    private val context: AppCompatActivity,
    private val vm: HomeViewModel,
    private var items: MutableList<BasketItems?>?,
    private val clickListener: RecyclerItemClickListener,
    private val cartUpdateListener: UpdateCartClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_cart_order_row, parent, false)
        return ViewHolder(view, clickListener, cartUpdateListener)

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
        notifyItemInserted(items!!.size - 1)
    }

    private fun remove(item: BasketItems) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener,
        private val cartUpdateListener: UpdateCartClickListener
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

            if (model?.basketType.equals("plate")) {
                foodimage.loadUrl(model!!.plate!!.name)
                foodname.text = "${model!!.plate!!.name}"
            }

            if (model?.basketType.equals("custom_plate")) {
                foodimage.loadUrl(model!!.customPlate!!.name)
                foodname.text = "${model!!.customPlate!!.name}"
            }

            itemCount.text = "${model!!.quantity}"
            price.text = "$" + "${model!!.totalValue}"

        }

        init {
            minus.setOnClickListener {
                subtractItemBy1(model!!)
            }

            plus.setOnClickListener {
                addItemBy1(model!!)
            }

            delImg.setOnClickListener {
                deleteItem(model!!)
                remove(model!!)
            }
        }

        override fun clear() {}

        private fun addItemBy1(model: BasketItems) {
            context.toast("Updating cart")
            this.clickListener.modelClick(model)

            vm.addItemby1(model.basketItemId!!).observe(context, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        this.cartUpdateListener.modelClick(model, true)
                        context.toast("Couldn't update cart")
                    }
                    Status.SUCCESS -> {
                        items = it.data?.items?.toMutableList()

                        this.cartUpdateListener.modelClick(it.data!!, true)


                        notifyDataSetChanged()
                    }
                }
            })
        }

        private fun subtractItemBy1(model: BasketItems) {
            context.toast("Updating cart")
            this.clickListener.modelClick(model)

            vm.subtractItemby1(model.basketItemId!!).observe(context, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        this.cartUpdateListener.modelClick(model, true)
                        context.toast("Couldn't update cart")
                    }
                    Status.SUCCESS -> {
                        items = it.data?.items?.toMutableList()

                        this.cartUpdateListener.modelClick(it.data!!, true)

                        notifyDataSetChanged()
                    }
                }
            })
        }

        private fun deleteItem(model: BasketItems) {
            context.toast("Updating cart")
            this.clickListener.modelClick(model)

            vm.deleteItem(model.basketItemId!!).observe(context, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        this.cartUpdateListener.modelClick(model, true)
                        context.toast("Couldn't update cart")
                    }
                    Status.SUCCESS -> {
                        items = it.data?.items?.toMutableList()

                        this.cartUpdateListener.modelClick(it.data!!, true)

                        notifyDataSetChanged()
                    }
                }
            })
        }

    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = items!!.size
        items!!.clear()
        notifyItemRangeRemoved(0, size)
    }
}
