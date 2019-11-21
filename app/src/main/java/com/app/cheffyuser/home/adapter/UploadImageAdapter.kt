package com.app.cheffyuser.home.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import timber.log.Timber
import java.io.File

class UploadImageAdapter(
    private val context: Context,
    private val urls: MutableList<String>?,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var myHolder: BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_upload_holder, parent, false)
        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        myHolder = holder
        holder.onBind(position)
    }


    override fun getItemCount(): Int {
        return urls?.size ?: 0
    }

    fun add(url: String) {
        urls!!.add(url)
        notifyItemInserted(urls.size - 1)
    }

    fun remove(url: String) {
        val position = urls!!.indexOf(url)
        if (position > -1) {
            urls.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): String? {
        return if (position != RecyclerView.NO_POSITION)
            urls!![position]
        else
            null
    }

    internal inner class ViewHolder(
        itemView: View,
        private val clickListener: RecyclerItemClickListener
    ) :
        BaseViewHolder(itemView), View.OnClickListener {
        private var model: String? = null

        //init views here
        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val remove = itemView.findViewById<ImageButton>(R.id.btn_remove)

        init {

            remove.setOnClickListener(this)
        }

        override fun onBind(position: Int) {
            super.onBind(position)
            this.model = urls!![position]

            Timber.d("$model")
            val file = File("$model")
            if (file.exists()) {
                val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
                image.setImageBitmap(myBitmap)
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
        val size = urls!!.size
        urls.clear()
        notifyItemRangeRemoved(0, size)
    }
}
