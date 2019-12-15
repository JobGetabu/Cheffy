package com.app.cheffyuser.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.BaseViewHolder
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.CustomPlates
import com.app.cheffyuser.home.model.FavPlate
import com.app.cheffyuser.home.model.FavouriteRequest
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener
import timber.log.Timber

class FoodFavouriteAdapter(
    private val context: FragmentActivity,
    private val vm: HomeViewModel,
    private val items: MutableList<Any>?,
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
        return items?.size ?: 0
    }

    private fun remove(item: Any) {
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

        private var model: Any? = null

        //init views here
        private val foodimage = itemView.findViewById<ImageView>(R.id.foodimage)
        private val im_btn2 = itemView.findViewById<ImageButton>(R.id.im_btn2)
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
            this.model = items!![position]

            if (model is CustomPlates) {

                // set whatever you want. for instance;
                val dt = (model as CustomPlates)

                if (!dt.customPlateImages.isNullOrEmpty()) {
                    foodimage.loadUrl(dt.customPlateImages.get(0)?.url)
                }
                foodname.text = dt.name

                im_btn2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cash_on_delivery))
                times.text = "$"+"${dt.priceMin}-${dt.priceMax}"

                spark_button.setEventListener(object : SparkEventListener {
                    override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}

                    override fun onEvent(button: ImageView?, buttonState: Boolean) {
                        if (buttonState) {
                            createSnack(ctx = context, txt = "Added to favourites")

                            val favR = FavouriteRequest("custom_plate", dt.id)

                            vm.addFavourite(favR).observe(context, Observer {
                                when (it.status) {
                                    Status.ERROR -> {
                                        Timber.wtf(it.message)
                                    }
                                    Status.SUCCESS -> {
                                        Timber.d(it.data?.message)
                                    }
                                    Status.LOADING -> {
                                    }
                                }
                            })

                        } else {
                            remove(model!!)

                            val favR = FavouriteRequest("custom_plate", dt.id)

                            vm.removeFavourite(favR).observe(context, Observer {
                                when (it.status) {
                                    Status.ERROR -> {
                                        Timber.wtf(it.message)
                                    }
                                    Status.SUCCESS -> {
                                        Timber.d(it.data?.message)
                                    }
                                    Status.LOADING -> {
                                    }
                                }
                            })
                        }
                    }

                    override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {}
                })

            }

            if (model is FavPlate) {

                val dt = (model as FavPlate)

                // set whatever you want. for instance;
                if (!dt?.plateImages.isNullOrEmpty()) {
                    foodimage.loadUrl(dt?.plateImages?.get(0)?.url)
                }
                foodname.text = dt?.name
                times.text = "${dt!!.deliveryTime!!.minus(5)}-${dt?.deliveryTime} min"


                spark_button.setEventListener(object : SparkEventListener {
                    override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}

                    override fun onEvent(button: ImageView?, buttonState: Boolean) {
                        if (buttonState) {
                            createSnack(ctx = context, txt = "Added to favourites")

                            val favR = FavouriteRequest("plate", dt.id)

                            vm.addFavourite(favR).observe(context, Observer {
                                when (it.status) {
                                    Status.ERROR -> {
                                        Timber.wtf(it.message)
                                    }
                                    Status.SUCCESS -> {
                                        Timber.d(it.data?.message)
                                    }
                                    Status.LOADING -> {
                                    }
                                }
                            })

                        } else {
                            remove(model!!)

                            val favR = FavouriteRequest("plate", dt.id)

                            vm.removeFavourite(favR).observe(context, Observer {
                                when (it.status) {
                                    Status.ERROR -> {
                                        Timber.wtf(it.message)
                                    }
                                    Status.SUCCESS -> {
                                        Timber.d(it.data?.message)
                                    }
                                    Status.LOADING -> {
                                    }
                                }
                            })
                        }
                    }

                    override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {}
                })
            }


            spark_button.isChecked = true



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
