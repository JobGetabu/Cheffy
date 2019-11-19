package com.app.cheffyuser.home.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.ReceiptDetailsActivity
import com.app.cheffyuser.home.adapter.Food_plate_adapter
import com.app.cheffyuser.home.adapter.IngredienceAdapter
import com.app.cheffyuser.home.model.Food_Plate_Model
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_plate_details.*
import kotlinx.android.synthetic.main.single_ingredient_layout.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class PlateDetailsFragment : BaseFragment() {

    private var recyclerView: RecyclerView? = null
    private var adapter: Food_plate_adapter? = null
    private var layoutManager1: LinearLayoutManager? = null
    private var foodList: MutableList<Food_Plate_Model>? = null

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plate_details, container, false)


        recyclerView = view.findViewById(R.id.recyclerview_plate)
        recyclerView!!.setHasFixedSize(true)
        layoutManager1 = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager1
        foodList = ArrayList()
        adapter = Food_plate_adapter(foodList)
        foodList!!.add(
            Food_Plate_Model(
                "Grilled salmon",
                "Season salmon fillets with lemon pepper, garlic powder, and salt. In a small bowl, ",
                "$120.00",
                "10-20 min",
                "Delivery",
                "Free",
                R.drawable.upload_thumbnail
            )
        )
        foodList!!.add(
            Food_Plate_Model(
                "Grilled salmon",
                "Season salmon fillets with lemon pepper, garlic powder, and salt. In a small bowl, ",
                "$140.00",
                "10-20 min",
                "Delivery",
                "Free",
                R.drawable.upload_thumbnail
            )
        )
        recyclerView!!.adapter = adapter

        val button = view.findViewById<Button>(R.id.viewReceipt)
        button.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    ReceiptDetailsActivity::class.java
                )
            )
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        fooddescription.text = vm.platesResponse.value?.description

        setIngredienceList()

    }

    private fun setIngredienceList() {
        ingredient_list.setHasFixedSize(true)

        val ingredients = vm.platesResponse.value?.ingredients

        val adapter = IngredienceAdapter(true, context!!, ingredients?.toMutableList())

        ingredient_list.adapter = adapter
    }

}
