package com.app.cheffyuser.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.profile.model.PlateData
import com.app.cheffyuser.utils.loadUrl
import timber.log.Timber

class ChefPlatesAdapter(
    private val context: Context,
    private val items: MutableList<PlateData?>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_foodother_plate, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return items!!.size
    }

    fun add(item: PlateData) {
        items!!.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun remove(item: PlateData) {
        val position = items!!.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): PlateData? {
        return if (position != RecyclerView.NO_POSITION)
            items!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: PlateData? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.foodimage)
        private val foodname = itemView.findViewById<TextView>(R.id.foodname)
        private val fooddes = itemView.findViewById<TextView>(R.id.foodtext)
        private val foodprice = itemView.findViewById<TextView>(R.id.foodprice)
        private val times = itemView.findViewById<TextView>(R.id.times)
        private val deliverytext = itemView.findViewById<TextView>(R.id.deliverytext)
        private val badge_price = itemView.findViewById<TextView>(R.id.badge_price)
        private val holder_foodother = itemView.findViewById<LinearLayout>(R.id.holder_foodother)

        init {

            foodimage.setOnClickListener(this)
            foodname.setOnClickListener(this)
            deliverytext.setOnClickListener(this)
            times.setOnClickListener(this)
            holder_foodother.setOnClickListener(this)

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            Timber.d("item at => $position")
            this.model = items!![position]
            Timber.d("item at => $model")

            // set whatever you want. for instance;

                foodimage.loadUrl(model?.name)

            foodname.text = model?.name
            fooddes.text = model?.description
            foodprice.text = "$ ${model?.price}"
            badge_price.text = model?.deliveryType
            times.text = "${model!!.deliveryTime!!.minus(5)}-${model?.deliveryTime} min"

        }

        override fun onClick(view: View) {
            this.clickListener.modelClick(model!!)
        }

        override fun clear() {}
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

}
