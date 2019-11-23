package com.app.cheffyuser.food_category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.food_category.adapter.FoodCatAdapter
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.fragment_food_category.*


/**
 * A simple [Fragment] subclass.
 */
class FoodCategoryFragment : Fragment() {

    private lateinit var foodCatAdapter: FoodCatAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initList()
    }

    private fun initList() {

        catlist.setHasFixedSize(true)
        catlist.hideView()

        //TODO: pass in location coordinates
        vm.fetchFoodCategory().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {
                    //TODO: stop shimmer effect in view
                    //TODO: send manged logs to crashlytics in production
                    createSnack(ctx = activity!!, txt = "No foods categories")

                    shimmer_view_container.showView()
                    catlist.hideView()

                }
                Status.SUCCESS -> {

                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.hideView()
                    catlist.showView()

                    datas.let {
                        foodCatAdapter = FoodCatAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as FoodCatModel


                                }
                            })
                    }

                    catlist.adapter = foodCatAdapter
                }
                Status.LOADING -> {
                    //TODO: stop shimmer effect in view
                    //still loading data
                    shimmer_view_container.showView()
                    catlist.hideView()
                }
            }

        })
    }
}

