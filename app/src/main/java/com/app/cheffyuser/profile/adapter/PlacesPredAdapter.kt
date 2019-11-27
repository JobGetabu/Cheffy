package com.app.cheffyuser.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.profile.model.DropdownItem
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class PlacesPredAdapter(
    private val context: Context,
    private val placesClient: PlacesClient,
    private val clickListener: RecyclerPlaceClickListener
) : RecyclerView.Adapter<PlacesPredAdapter.ViewHolder>(), Filterable {

    private var dropdownItems: MutableList<DropdownItem>? = null

    private val listFilter: SearchLocationFilter

    init {
        this.dropdownItems = ArrayList()

        listFilter = SearchLocationFilter(this, placesClient)
    }

    fun setDropdownItems(dropdownItems: MutableList<DropdownItem>) {
        this.dropdownItems = dropdownItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.drop_item, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpUi(dropdownItems!![position])
        holder.setModel(dropdownItems!![position])
    }


    private fun getItem(position: Int): DropdownItem? {
        return if (position != RecyclerView.NO_POSITION)
            dropdownItems!![position]
        else
            null
    }

    override fun getItemCount(): Int {
        //return customerList.size();
        return dropdownItems!!.size
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    inner class ViewHolder(itemView: View, private val clickListener: RecyclerPlaceClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        var priTxt: TextView
        var secoTxt: TextView
        var main: LinearLayout

        private var model: DropdownItem? = null

        init {


            priTxt = itemView.findViewById(R.id.drop_prim_txt)
            secoTxt = itemView.findViewById(R.id.drop_seco_txt)
            main = itemView.findViewById(R.id.main)

            main.setOnClickListener(this)
            //material effect
            itemView.findViewById<View>(R.id.confirm_action).setOnClickListener(this)
        }

        fun setModel(model: DropdownItem) {
            this.model = model
        }

        fun setUpUi(model: DropdownItem) {
            priTxt.text = model.primaryText
            secoTxt.text = model.secondaryText
        }

        override fun onClick(view: View) {
            this.clickListener.onPlaceClick(model!!)
        }
    }

    fun refreshList() {
        notifyDataSetChanged()
    }

    fun clearList() {
        val size = dropdownItems!!.size
        dropdownItems!!.clear()
        notifyItemRangeRemoved(0, size)
    }

    companion object {

        val TAG = "PlaceAdapter"
    }

}
