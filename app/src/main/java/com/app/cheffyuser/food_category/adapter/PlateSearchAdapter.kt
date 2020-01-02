package com.app.cheffyuser.food_category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlateSearch
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import com.github.florent37.shapeofview.shapes.RoundRectView

class PlateSearchAdapter(
    private val context: Context,
    private val items: MutableList<PlateSearch?>?,
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
        return items?.size ?: 0
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: PlateSearch? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.foodimage)
        private val foodname = itemView.findViewById<TextView>(R.id.foodname)
        private val fooddes = itemView.findViewById<TextView>(R.id.foodtext)
        private val foodprice = itemView.findViewById<TextView>(R.id.foodprice)
        private val times = itemView.findViewById<TextView>(R.id.times)
        private val deliverytext = itemView.findViewById<TextView>(R.id.deliverytext)
        private val badge_price = itemView.findViewById<TextView>(R.id.badge_price)
        private val holder_foodother = itemView.findViewById<ConstraintLayout>(R.id.holder_foodother)
        private val float_availability = itemView.findViewById<RoundRectView>(R.id.float_availability)

        init {

            foodimage.setOnClickListener(this)
            foodname.setOnClickListener(this)
            deliverytext.setOnClickListener(this)
            times.setOnClickListener(this)
            holder_foodother.setOnClickListener(this)

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = items!![position]

            //foodimage.loadUrl(model?.name)
            foodname.text = model?.name
            fooddes.text = model?.description
            foodprice.text = "$ ${model?.price}"
            times.text = "${model!!.deliveryTime!!.minus(5)}-${model?.deliveryTime} min"
            badge_price.hideView()

            if (!model?.available!!){
                float_availability.showView()
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
}
