package com.app.cheffyuser.home.fragments


import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.FoodAddToCartActivity
import com.app.cheffyuser.home.activities.ReceiptDetailsActivity
import com.app.cheffyuser.home.adapter.Food_plate_adapter
import com.app.cheffyuser.home.adapter.IngredienceAdapter
import com.app.cheffyuser.home.model.Food_Plate_Model
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.Constants
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        fooddescription.text = vm.platesResponse.value?.description

        setIngredienceList()

        viewReceipt.setOnClickListener {
            goToReceiptActivity()
        }

        buy_btn.setOnClickListener {
            goToFoodAddToCart()
        }

    }

    private fun goToFoodAddToCart() {
        val pair1: android.util.Pair<View, String> = android.util.Pair.create(buy_btn, "slider1")

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            activity!!, pair1
        )

        val intent = FoodAddToCartActivity.newIntent(context!!)
        intent.putExtra(Constants.PLATES_RESPONSE_EXTRA, vm.platesResponse.value)

        startActivity(intent)
    }

    private fun goToReceiptActivity() {
        val pair1: android.util.Pair<View, String> = android.util.Pair.create(ingredient_layout, "slider")

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            activity!!, pair1
        )

        val intent = ReceiptDetailsActivity.newIntent(context!!)
        intent.putExtra(Constants.PLATES_RESPONSE_EXTRA, vm.platesResponse.value)

        startActivity(intent, activityOptions.toBundle())
    }

    private fun setIngredienceList() {
        ingredient_list.setHasFixedSize(true)

        val ingredients = vm.platesResponse.value?.ingredients

        val adapter = IngredienceAdapter(true, context!!, ingredients?.toMutableList())

        ingredient_list.adapter = adapter
    }

}
