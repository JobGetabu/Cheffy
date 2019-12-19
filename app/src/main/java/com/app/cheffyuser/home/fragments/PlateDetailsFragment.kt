package com.app.cheffyuser.home.fragments


import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.FoodAddToCartActivity
import com.app.cheffyuser.home.activities.ReceiptDetailsActivity
import com.app.cheffyuser.home.adapter.FoodRelatedAdapter
import com.app.cheffyuser.home.adapter.IngredientsAdapter
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.Constants
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.fragment_plate_details.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.item_multiplier.*
import kotlinx.android.synthetic.main.single_ingredient_layout.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class PlateDetailsFragment : BaseFragment() {

    private lateinit var relatedAdapter: FoodRelatedAdapter

    private var numberWannaBuy = 1
    private var foodPrice: Double = 0.00

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plate_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStuff()

        viewReceipt.setOnClickListener {
            goToReceiptActivity()
        }

        buy_btn.setOnClickListener {
            goToFoodAddToCart()
        }

        plateResObserver()

        counter.text = "$numberWannaBuy"
        counterForMultiplier()

    }

    private fun uiStuff() {
        //TODO: check if plateId is not null


    }

    private fun counterForMultiplier() {

        minus_img.setOnClickListener {
            if (numberWannaBuy < 2) return@setOnClickListener
            numberWannaBuy--
            counter.text = "$numberWannaBuy"

            priceCounter()

        }

        plus_img.setOnClickListener {
            if (numberWannaBuy > 19) return@setOnClickListener
            numberWannaBuy++
            counter.text = "$numberWannaBuy"

            priceCounter()
        }
    }

    private fun priceCounter() {
        //update price

        buy_btn.text = "$"+"Buy ${foodPrice * numberWannaBuy}"
    }

    private fun plateResObserver() {
        vm.platesResponse.observe(this, Observer {
            if (it != null) {
                fooddescription.text = vm.platesResponse.value?.description

                foodPrice = vm.platesResponse.value?.price!!
                buy_btn.text = "$"+"Buy $foodPrice"

                setIngredientsList()

                setRelatedFoodList()
            }
        })
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
        val pair1: android.util.Pair<View, String> =
            android.util.Pair.create(ingredient_layout, "slider")

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            activity!!, pair1
        )

        val intent = ReceiptDetailsActivity.newIntent(context!!)
        intent.putExtra(Constants.PLATES_RESPONSE_EXTRA, vm.platesResponse.value)

        startActivity(intent, activityOptions.toBundle())
    }

    private fun setIngredientsList() {
        ingredient_list.setHasFixedSize(true)

        val ingredients = vm.platesResponse.value?.ingredients

        if (!vm.platesResponse.value?.ingredients.isNullOrEmpty()) {

            val adapter = IngredientsAdapter(true, context!!, ingredients?.toMutableList())

            ingredient_list.adapter = adapter
        }

        //no ingredients
        if (ingredients.isNullOrEmpty()) {
            viewReceipt.hideView()
            ingredient_layout.hideView()

        }
    }

    private fun setRelatedFoodList() {
        recyclerview_plate.setHasFixedSize(true)

        loader_layout.showView()
        recyclerview_plate.hideView()

        val id = (vm.platesResponse.value as PlatesResponse).id!!

        vm.fetchRelatedFood(id).observe(this, androidx.lifecycle.Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No related foods")

                    //
                    loader_layout.hideView()
                    recyclerview_plate.hideView()
                    other_plate_lbl.hideView()


                }
                Status.SUCCESS -> {
                    recyclerview_plate.showView()
                    loader_layout.hideView()

                    datas.let {
                        relatedAdapter = FoodRelatedAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as PlatesResponse

                                    createSnack(ctx = activity!!, txt = "Go to categoty foods")

                                }
                            })
                    }

                    recyclerview_plate.adapter = relatedAdapter
                }
                Status.LOADING -> {
                    //still loading data
                    loader_layout.showView()
                }
            }
        })
    }

    private fun getBasket() {

        vm.getBasket().observe(this, Observer {
            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    Timber.d("$it")

                }
                Status.SUCCESS -> {

                    if (!data!!.items.isNullOrEmpty()) {

                        //tv_total.text = "$" + "${data.total}"
                        //tv_count.text = "${data.items?.count()}"

                    } else {

                    }
                }
                Status.LOADING -> {
                }
            }
        })
    }

}
