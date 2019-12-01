package com.app.cheffyuser.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.utils.loadUrl
import com.app.cheffyuser.utils.toast
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener

class FoodFavouriteAdapter(
    private val context: Context,
    private val foodNearbyModels: MutableList<PlatesResponse>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_fav_fooditem, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return foodNearbyModels?.size ?: 0
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
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: PlatesResponse? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.foodimage)
        private val foodname = itemView.findViewById<TextView>(R.id.foodname)
        private val times = itemView.findViewById<TextView>(R.id.times)
        private val deliverytext = itemView.findViewById<TextView>(R.id.deliverytext)
        private val spark_button = itemView.findViewById<SparkButton>(R.id.spark_button)

        init {

            foodimage.setOnClickListener(this)
            foodname.setOnClickListener(this)
            deliverytext.setOnClickListener(this)
            times.setOnClickListener(this)

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = foodNearbyModels!![position]

            // set whatever you want. for instance;
            foodimage.loadUrl(model?.plateImages?.get(0)?.url)
            foodname.text = model?.name
            times.text = "${model!!.deliveryTime!!.minus(5)}-${model?.deliveryTime} min"

            spark_button.isChecked = true

            spark_button.setEventListener(object : SparkEventListener {
                override fun onEvent(
                    button: ImageView,
                    buttonState: Boolean
                ) {
                    if (buttonState) { // Button is active
                        context.toast("TODO: Add to favourite")
                    } else { // Button is inactive
                        context.toast("TODO: hide from favourite")
                        //remove(model!!)
                    }
                }

                override fun onEventAnimationEnd(
                    button: ImageView,
                    buttonState: Boolean
                ) {
                }

                override fun onEventAnimationStart(
                    button: ImageView,
                    buttonState: Boolean
                ) {
                }
            })

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
        val size = foodNearbyModels!!.size
        foodNearbyModels.clear()
        notifyItemRangeRemoved(0, size)
    }
}
