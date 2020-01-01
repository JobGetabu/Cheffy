package com.app.cheffyuser.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.home.model.FavouriteRequest
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener
import timber.log.Timber

class FoodPopularAdapter(
    private val context: FragmentActivity,
    private val vm: HomeViewModel,
    private val foodNearbyModels: MutableList<PlatesResponse>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_fooditem_vertical, parent, false)
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
        private val food_ratings = itemView.findViewById<TextView>(R.id.food_ratings)
        private val times = itemView.findViewById<TextView>(R.id.times)
        private val deliverytext = itemView.findViewById<TextView>(R.id.deliverytext)

        private val sparkButton = itemView.findViewById<SparkButton>(R.id.spark_button)

        init {

            foodimage.setOnClickListener(this)
            foodname.setOnClickListener(this)
            food_ratings.setOnClickListener(this)
            deliverytext.setOnClickListener(this)
            times.setOnClickListener(this)

            sparkButton.setEventListener(object : SparkEventListener {
                override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}

                override fun onEvent(button: ImageView?, buttonState: Boolean) {
                    if (buttonState) {
                        createSnack(ctx = context, txt = "Added to favourites")

                        val favR = FavouriteRequest("plate", model!!.id)

                        if (!CheffyApp.instance!!.tokenManager.isLoggedIn) {
                            createSnack(
                                ctx = context,
                                txt = "Login to add favourites",
                                txtAction = "Login",
                                isDefinate = true,
                                action = View.OnClickListener { vm.goToLoginTrigger.value = true })

                            sparkButton.isChecked = false
                            return
                        }

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
                        createSnack(ctx = context, txt = "Removed to favourites")

                        val favR = FavouriteRequest("plate", model!!.id)

                        if (!CheffyApp.instance!!.tokenManager.isLoggedIn) {
                            createSnack(
                                ctx = context,
                                txt = "Login to add favourites",
                                txtAction = "Login",
                                isDefinate = true,
                                action = View.OnClickListener { vm.goToLoginTrigger.value = true })

                            sparkButton.isChecked = false
                            return
                        }

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

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = foodNearbyModels!![position]

            var ratingSum = 0
            var rating = 0

            model?.reviews?.forEach {
                //ratingSum += it.rating!!
            }

            model?.reviews?.let {
                if (it.count() != 0)
                    rating = ratingSum / it.count()
            }


            // set whatever you want. for instance;
            if (!model?.plateImages.isNullOrEmpty()) {

                foodimage.loadUrl(model?.plateImages?.get(0)?.url)
            }
            foodname.text = model?.name
            food_ratings.text = "$rating(${ratingSum})"

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

    fun clearList() {
        val size = foodNearbyModels!!.size
        foodNearbyModels.clear()
        notifyItemRangeRemoved(0, size)
    }
}
