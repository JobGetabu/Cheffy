package com.app.cheffyuser.cart.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.activities.CustomOrderListActivity
import com.app.cheffyuser.cart.adapter.CustomOrderAdapter
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.model.CustomPlateResponseData
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.loadAnim
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.float_viewcart.*
import kotlinx.android.synthetic.main.fragment_custom_order.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class CustomOrderFragment : Fragment() {

    private lateinit var customOrderAdapter: CustomOrderAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        no_item_text.text = "Custom orders appear here"
        layout_item_cart.hideView()

        setupCustomOrderList()

        getBasket()

        swipeToRefresh.setOnRefreshListener {
            setupCustomOrderList()
        }
    }

    private fun setupCustomOrderList() {
        val lm = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = lm
        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()

        vm.getCustomPlates().observe(this, Observer {
            swipeToRefresh?.isRefreshing = false
            val data = it.data?.customPlateResponseData

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No custom orders")

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    if (!data!!.isNullOrEmpty()) {
                        customOrderAdapter = CustomOrderAdapter(
                            activity!!,
                            data.toMutableList(),
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as CustomPlateResponseData

                                    val bids = model.customPlateAuction!!.bidCount!!

                                    if (bids > 0) {
                                        val intent = CustomOrderListActivity.newIntent(activity!!)
                                        intent.putExtra(
                                            CustomOrderListActivity.CUSTOM_PLATE_ID,
                                            model.customPlateAuction.id!!
                                        )
                                        activity?.startActivity(intent)

                                    } else {
                                        createSnack(
                                            ctx = activity!!,
                                            txt = "Your order has not received bids yet, we'll notify you",
                                            txtAction = "Refresh",
                                            action = View.OnClickListener {
                                                setupCustomOrderList()
                                            }
                                        )
                                    }
                                }
                            })
                        recycler_view.adapter = customOrderAdapter

                    } else {
                        recycler_view.hideView()
                        noitem_layout.showView()
                        loader_layout.hideView()
                    }
                }
                Status.LOADING -> {
                }
            }
        })
    }


    private fun getBasket() {
        vm.getBasket().observe(this, Observer {
            val data = it.data

            when (it.status) {
                Status.SUCCESS -> {
                    if (!data!!.items.isNullOrEmpty()) {
                        layout_item_cart.showView()
                        tv_total.text = "$" + "${data.total}"
                        tv_count.text = "${data.items?.count()}"

                    } else {
                        layout_item_cart.hideView()
                    }
                }
            }
        })


    }

}
