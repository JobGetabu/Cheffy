package com.app.cheffyuser.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.utils.loadUrl
import de.hdodenhof.circleimageview.CircleImageView
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class ReviewsAdapter(
    private val context: Context,
    private val reviews: MutableList<PlatesResponse.Reviews>?
) : RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_review_lay, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return reviews?.size ?: 0
    }

    fun add(review: PlatesResponse.Reviews) {
        reviews!!.add(review)
        notifyItemInserted(reviews.size - 1)
    }

    private fun remove(review: PlatesResponse.Reviews) {
        val position = reviews!!.indexOf(review)
        if (position > -1) {
            reviews.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): PlatesResponse.Reviews? {
        return if (position != RecyclerView.NO_POSITION)
            reviews!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View
    ) :
        BaseViewHolder(itemView) {
        private var model: PlatesResponse.Reviews? = null

        //init views here
        private val reviewface = itemView.findViewById<CircleImageView>(R.id.reviewface)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val rate = itemView.findViewById<MaterialRatingBar>(R.id.rate)
        private val comment = itemView.findViewById<TextView>(R.id.comment)

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = reviews!![position]

            // set whatever you want. for instance;
            reviewface.loadUrl(model?.user?.name)
            name.text = model?.user?.name
            date.text = "April 4, 2019"
            rate.rating = model?.rating!!.toFloat()
            comment.text = model?.comment

        }

        override fun clear() {}
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = reviews!!.size
        reviews.clear()
        notifyItemRangeRemoved(0, size)
    }
}
