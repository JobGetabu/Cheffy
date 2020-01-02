package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.FoodOtherSelectedAdapter
import com.app.cheffyuser.cart.models.AddToBasketRequest
import com.app.cheffyuser.cart.models.BasketRequest
import com.app.cheffyuser.cart.models.PeopleAddedResponse
import com.app.cheffyuser.create_account.activities.CreateAccountActivity
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerCheckBoxClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.*
import com.app.cheffyuser.utils.Tools.nestedScrollTo
import com.app.cheffyuser.utils.Tools.toggleArrow
import com.app.cheffyuser.utils.ViewAnimations.AnimListener
import kotlinx.android.synthetic.main.activity_food_add_to_cart.*
import kotlinx.android.synthetic.main.float_addtocart.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.item_multiplier.*
import timber.log.Timber


class FoodAddToCartActivity : BaseActivity() {

    private lateinit var relatedAdapter: FoodOtherSelectedAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private var numberWannaBuy = 1
    private var foodPrice: Double = 0.00

    val calList: MutableList<PeopleAddedResponse>? = mutableListOf()
    private var platesResponse: PlatesResponse? = null

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodAddToCartActivity::class.java)

        const val NUM_BUY_EXTRA = "NUM_BUY_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_add_to_cart)

        platesResponse = intent.getParcelableExtra(Constants.PLATES_RESPONSE_EXTRA)!!

        if (platesResponse == null) {
            toast("Plate must be selected")
            finish()
        }

        foodPrice = platesResponse!!.price!!
        numberWannaBuy = intent.getIntExtra(NUM_BUY_EXTRA, 1)

        foodname.text = platesResponse?.name
        fooddescription.text = platesResponse?.description

        float_addtocart_body.setOnClickListener {
            addToCart()
        }

        imageButton2.setOnClickListener {
            toggleSectionInfo(imageButton2)
        }

        setRelatedFoodList()

        priceCounter()
        counterForMultiplier()
        ppleAddedObserver()

    }

    private fun counterForMultiplier() {

        minus_img.setOnClickListener {
            if (numberWannaBuy < 2 && numberWannaBuy > 0) return@setOnClickListener
            numberWannaBuy--
            priceCounter()

        }

        plus_img.setOnClickListener {
            if (numberWannaBuy > 19) return@setOnClickListener
            numberWannaBuy++
            priceCounter()
        }
    }

    private fun priceCounter() {
        //update price
        counter.text = "$numberWannaBuy"
        addtC_tv_price.text = "$" + "${foodPrice * numberWannaBuy}"

        ppleAddedObserver()
    }

    private fun ppleAddedObserver() {
        var pTotal = 0.0

        calList!!.forEach { p ->
            run {
                pTotal += p.price!!
            }
        }

        addtC_tv_price.text = "$" + "${(foodPrice * numberWannaBuy) + pTotal}"

    }

    private fun setRelatedFoodList() {
        recycler_view.setHasFixedSize(true)

        loader_layout.showView()
        recycler_view.hideView()

        val id = platesResponse?.id!!

        vm.getPeopleAlsoAdded(id).observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: No related foods")

                    loader_layout.hideView()
                    recycler_view.hideView()
                    textView12.hideView()
                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    loader_layout.hideView()

                    datas.let {

                        relatedAdapter =
                            FoodOtherSelectedAdapter(this,
                                datas?.toMutableList(),
                                object : RecyclerCheckBoxClickListener {
                                    override fun modelClick(model: Any, isChecked: Boolean) {
                                        model as PeopleAddedResponse

                                        Timber.d("click check at =>$isChecked model=>${model.name}")

                                        if (isChecked) calList!!.add(model)
                                        else calList!!.remove(model)

                                        calList.forEach { Timber.d("id =>${it.name} model=>${it.name}") }

                                        ppleAddedObserver()
                                    }
                                })
                    }

                    recycler_view.adapter = relatedAdapter
                }
                Status.LOADING -> {
                    //still loading data
                    loader_layout.showView()
                }
            }
        })
    }

    private fun toggleSectionInfo(view: View) {
        val show = toggleArrow(view)
        if (show) {
            ViewAnimations.expand(instructions, object : AnimListener {
                override fun onFinish() {
                    nestedScrollTo(nestedscroll, instructions)
                }
            })
        } else {
            ViewAnimations.collapse(instructions)
        }
    }


    private fun addToCart() {
        if (!CheffyApp.instance!!.tokenManager.isLoggedIn) {
            createSnack(
                ctx = this,
                txt = "Login to create order",
                txtAction = "Login",
                isDefinate = true,
                action = View.OnClickListener {

                    val intent = Intent(
                        this,
                        CreateAccountActivity::class.java
                    )
                    startActivity(intent)
                })

            return
        }

        val dialog = showDialogue("Adding to cart", "Please wait ...")

        val basketRequests: MutableList<BasketRequest> = mutableListOf()

        val req1 = BasketRequest(platesResponse!!.id!!, numberWannaBuy)
        basketRequests.add(req1)

        calList?.forEach {
            val req = BasketRequest(it.id, 1)
            basketRequests.add(req)
        }

        val req = AddToBasketRequest(plates = basketRequests)

        vm.addToBasket(req).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)
                    dialog?.dismiss()
                    toast("Added to cart")

                    //TODO: goToChef now

                    finish()
                }
            }
        })
    }
}
