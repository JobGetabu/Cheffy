package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.adapter.CardAdapter
import com.app.cheffyuser.create_account.model.ShippingData
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.loadAnim
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.activity_payment_options.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber

class PaymentOptionsActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, PaymentOptionsActivity::class.java)
    }

    private lateinit var adapter: CardAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_options)

        uiStuff()
    }

    private fun uiStuff() {
        no_item_text.text = getString(R.string.your_cards_appear)

        fab.setOnClickListener {
            startActivity(AddCardActivity.newIntent(this))
        }

        setupList()
    }

    private fun setupList() {

        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()

        vm.fetchShipping().observe(this, Observer {

            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    adapter = CardAdapter(
                        this,
                        data!!.shippingResponseData?.toMutableList(),
                        object : RecyclerItemClickListener {
                            override fun modelClick(model: Any) {
                                model as ShippingData

                                setCard(model)
                            }
                        })

                    recycler_view.adapter = adapter
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun setCard(model: ShippingData) {

    }
}
