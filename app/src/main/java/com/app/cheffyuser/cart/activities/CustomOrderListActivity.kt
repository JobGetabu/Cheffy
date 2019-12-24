package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.CustomOrderChefAdapter
import com.app.cheffyuser.cart.models.CustomPlateAuctionBids
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.*
import kotlinx.android.synthetic.main.activity_custom_order_list.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber

class CustomOrderListActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, CustomOrderListActivity::class.java)

        const val CUSTOM_PLATE_ID = "CUSTOM_PLATE_ID"

    }

    private lateinit var customOrderAdapter: CustomOrderChefAdapter
    private var customPlateId: Int? = null

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order_list)

        customPlateId = intent.getIntExtra(CUSTOM_PLATE_ID, 0)

        if (customPlateId == null) {
            toast("Provide plate id")
            finish()
        }

        uiStuff()
    }

    private fun uiStuff() {

        no_item_text.text = "Received bids appear here"

        setupCustomOrderList()

        swipeToRefresh.setOnRefreshListener {
            setupCustomOrderList()
        }

    }

    private fun setupCustomOrderList() {
        val lm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = lm
        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()

        vm.getCustomPlate(customPlateId!!).observe(this, Observer {
            swipeToRefresh?.isRefreshing = false
            val data = it.data?.customPlateAuction

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug only: No bids")

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    if (!data!!.customPlateAuctionBids.isNullOrEmpty()) {
                        customOrderAdapter = CustomOrderChefAdapter(
                            this,
                            data.customPlateAuctionBids,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    model as CustomPlateAuctionBids

                                    acceptBid(model.id!!)

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

    private fun acceptBid(bidId: Int) {

        val dialog = showDialogue("Adding item in cart", "Please wait ...")

        vm.acceptBid(bidId).observe(this, Observer {

            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)

                }
            }
        })
    }
}