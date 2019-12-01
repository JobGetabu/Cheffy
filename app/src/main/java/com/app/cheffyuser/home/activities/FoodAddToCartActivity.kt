package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.FoodOtherSelectedAdapter
import com.app.cheffyuser.home.adapter.RecyclerCheckBoxClickListener
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.*
import com.app.cheffyuser.utils.Tools.nestedScrollTo
import com.app.cheffyuser.utils.Tools.toggleArrow
import com.app.cheffyuser.utils.ViewAnimations.AnimListener
import kotlinx.android.synthetic.main.activity_food_add_to_cart.*
import kotlinx.android.synthetic.main.item_loading.*


class FoodAddToCartActivity : BaseActivity() {

    private lateinit var relatedAdapter: FoodOtherSelectedAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private var platesResponse: PlatesResponse? = null

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, FoodAddToCartActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_add_to_cart)

        platesResponse = intent.getParcelableExtra(Constants.PLATES_RESPONSE_EXTRA)

        foodname.text = platesResponse?.name
        fooddescription.text = platesResponse?.description

        bottomlay.setOnClickListener {
            toast("Added to cart")
            finish()
        }

        imageButton2.setOnClickListener {
            toggleSectionInfo(imageButton2)
        }

        setRelatedFoodList()

    }

    private fun setRelatedFoodList() {
        recycler_view.setHasFixedSize(true)

        loader_layout.showView()
        recycler_view.hideView()

        val id = platesResponse?.id!!

        vm.fetchRelatedFood(id).observe(this, androidx.lifecycle.Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: No related foods")

                    //
                    loader_layout.hideView()
                    recycler_view.hideView()
                    textView12.hideView()


                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    loader_layout.hideView()

                    datas.let {
                        relatedAdapter = FoodOtherSelectedAdapter(this, datas,
                            object : RecyclerCheckBoxClickListener {
                                override fun modelClick(model: Any, isChecked: Boolean) {
                                    model as PlatesResponse

                                    createSnack(
                                        ctx = this@FoodAddToCartActivity,
                                        txt = "$isChecked ${model.name}"
                                    )
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
}
