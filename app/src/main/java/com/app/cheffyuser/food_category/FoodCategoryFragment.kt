package com.app.cheffyuser.food_category


import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.custom_order.CustomOrderActivity
import com.app.cheffyuser.food_category.adapter.FoodCatAdapter
import com.app.cheffyuser.food_category.model.FoodCatModel
import com.app.cheffyuser.home.activities.BottomNavActivity
import com.app.cheffyuser.home.activities.FoodDetailsActivity
import com.app.cheffyuser.home.activities.FoodDetailsActivity.Companion.plateId
import com.app.cheffyuser.home.adapter.FoodPopularAdapter
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.home.model.*
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.activities.ChefProfileActivity
import com.app.cheffyuser.profile.activities.ChefProfileActivity.Companion.chefId
import com.app.cheffyuser.profile.activities.ShippingActivity
import com.app.cheffyuser.utils.Constants.PLATES_RESPONSE_EXTRA
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.fragment_food_category.*
import kotlinx.android.synthetic.main.no_searchfood.*
import kotlinx.android.synthetic.main.search_bar_layout.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class FoodCategoryFragment : BaseFragment() {

    private lateinit var foodCatAdapter: FoodCatAdapter

    //TODO: Replace with a search Adapter
    private lateinit var foodPopularAdapter: FoodPopularAdapter

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


        search_et.isFocusable = false
        search_et.addTextChangedListener(textWatcher())

        search_et.setOnClickListener {

            val mum: BottomNavActivity = getActivity()!! as BottomNavActivity

            mum.searchView.openSearch()

        }


        searchObserver()

        initList()

        btn_post.setOnClickListener {
            val intent = Intent(activity, CustomOrderActivity::class.java)
            startActivity(intent)
        }

        vm.shippingData.observe(this, Observer {
            if (it == null) return@Observer

            address_txt.text = tokenManager.shippingData!!.addressLine1
        })

        address_txt.setOnClickListener {
            val intent = Intent(
                activity,
                ShippingActivity::class.java
            )
            startActivity(intent)
        }

        address_img.setOnClickListener {
            val intent = Intent(
                activity,
                ShippingActivity::class.java
            )
            startActivity(intent)
        }

    }

    private fun initList() {

        catlist.setHasFixedSize(true)

        shimmer_view_container.startShimmer()
        shimmer_view_container.showView()
        catlist.hideView()
        no_searchfood_layout.hideView()

        //this one is Grid
        val gridManager = GridLayoutManager(activity, 2)
        catlist.layoutManager = gridManager

        //TODO: pass in location coordinates
        vm.fetchFoodCategory().observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {
                    //TODO: stop shimmer effect in view
                    //TODO: send manged logs to crashlytics in production
                    createSnack(ctx = activity!!, txt = "No foods categories")

                    shimmer_view_container.startShimmer()
                    shimmer_view_container.hideView()
                    catlist.hideView()
                    no_searchfood_layout.showView()

                }
                Status.SUCCESS -> {

                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.hideView()
                    no_searchfood_layout.hideView()
                    catlist.showView()

                    if (!datas.isNullOrEmpty()) {

                        foodCatAdapter = FoodCatAdapter(context!!, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as FoodCatModel

                                    val name = model.name

                                    listWithCategoryResult(name!!, model.id!!)
                                }
                            })
                        catlist.adapter = foodCatAdapter

                    } else {
                        no_searchfood_layout.showView()
                        catlist.hideView()
                    }


                }
                Status.LOADING -> {

                    //still loading data
                    shimmer_view_container.showView()
                    catlist.hideView()
                    no_searchfood_layout.hideView()
                }
            }

        })
    }

    private fun searchObserver() {
        vm.searchTerm.observe(this, Observer {
            if (it.isNullOrEmpty()) return@Observer

            knowIfSuggestion(vm.searchTerm.value!!)
            Timber.d("Search term ${vm.searchTerm.value} result: ${vm.searchResult}")


            when (vm.searchResult.type) {
                SEARCH_PLATE -> {
                    val intent = Intent(FoodDetailsActivity.newIntent(activity!!))
                    intent.putExtra(plateId, vm.searchResult.id)
                    startActivity(intent)
                    search_et!!.setText("")
                    initList()
                }
                SEARCH_CHEF -> {
                    val intent = Intent(ChefProfileActivity.newIntent(activity!!))
                    intent.putExtra(chefId, vm.searchResult.id)
                    startActivity(intent)
                    search_et!!.setText("")
                    initList()
                }
                SEARCH_CATEGORY -> {
                    listWithCategoryResult(it, vm.searchResult.id!!)
                }
                SEARCH_TEXT -> {
                    createSnack(ctx = activity!!, txt = "Search text API not ready")
                }
            }

        })
    }

    private fun listWithCategoryResult(searchTermId: String, categoryId: Int) {

        search_et.setText(searchTermId)

        catlist.setHasFixedSize(true)

        shimmer_view_container.startShimmer()
        shimmer_view_container.showView()
        catlist.hideView()
        no_searchfood_layout.hideView()

        //this one is Grid
        val lManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        catlist.layoutManager = lManager

        vm.getPlatesByCategory(categoryId).observe(this, Observer {
            val datas = it.data

            when (it.status) {
                Status.ERROR -> {

                    no_searchfood_layout.showView()
                    shimmer_view_container.hideView()
                    shimmer_view_container.stopShimmer()
                    catlist.hideView()

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No foods found")

                    //check for net
                    try {
                        val act: BottomNavActivity = getActivity()!! as BottomNavActivity
                        act.checkNetwork()
                    } catch (e: Exception) {
                        Timber.wtf(e)
                    }

                }
                Status.SUCCESS -> {

                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.hideView()
                    no_searchfood_layout.hideView()
                    catlist.showView()

                    if (!datas.isNullOrEmpty()) {

                        foodPopularAdapter = FoodPopularAdapter(activity!!, vm, datas,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as PlatesResponse

                                    goToFoodDetails(model)
                                }
                            })
                        catlist.adapter = foodPopularAdapter

                    } else {
                        no_searchfood_layout.showView()
                        catlist.hideView()
                    }

                }
                Status.LOADING -> {
                    //still loading data
                }
            }

        })
    }

    private fun goToFoodDetails(model: PlatesResponse) {

        val intent = FoodDetailsActivity.newIntent(context!!)
        intent.putExtra(PLATES_RESPONSE_EXTRA, model)
        startActivity(intent)
    }

    private fun knowIfSuggestion(suggestion: String): SearchResult? {
        //search
        vm.predictionsResponse.typeChef!!.forEach {
            if (it!!.chef!!.restaurantName.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_CHEF, it.userId)
                return vm.searchResult
            }
        }

        vm.predictionsResponse.typePlate!!.forEach {
            if (it!!.name.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_PLATE, it.id)
                return vm.searchResult
            }
        }

        vm.predictionsResponse.typeCategory!!.forEach {
            if (it!!.name.equals(suggestion)) {
                vm.searchResult = SearchResult(SEARCH_CATEGORY, it.id)
                return vm.searchResult
            }
        }

        //at this point is not predicted. so <text> search
        vm.searchResult = SearchResult(SEARCH_TEXT, null)
        return vm.searchResult
    }

    // text watcher
    private fun textWatcher() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //val cardno: String = email!!.text.toString()
            val searchTxt: String = search_et!!.text.toString()

            if (searchTxt.isNotEmpty()) {

                ImageViewCompat.setImageTintList(
                    img_filter,
                    ColorStateList.valueOf(getColor(R.color.grey_60))
                )
                img_filter.setImageDrawable(getDrawable(R.drawable.ic_close))


                img_filter.setOnClickListener {
                    search_et!!.setText("")
                    initList()
                }

            } else {
                ImageViewCompat.setImageTintList(
                    img_filter,
                    ColorStateList.valueOf(getColor(R.color.colorAccent))
                )
                img_filter.setImageDrawable(getDrawable(R.drawable.ic_filter_list_black_24dp))

                img_filter.setOnClickListener {
                    //TODO: Add filter logic
                }
            }
        }
    }

}

