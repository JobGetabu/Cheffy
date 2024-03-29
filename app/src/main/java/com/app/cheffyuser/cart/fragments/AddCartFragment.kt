package com.app.cheffyuser.cart.fragments


import android.content.Intent
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
import com.app.cheffyuser.cart.activities.ItemCartActivity
import com.app.cheffyuser.cart.adapter.CartItemsAdapter
import com.app.cheffyuser.cart.adapter.UpdateCartClickListener
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.hideView
import com.app.cheffyuser.utils.loadAnim
import com.app.cheffyuser.utils.showView
import kotlinx.android.synthetic.main.float_viewcart.*
import kotlinx.android.synthetic.main.fragment_add_cart.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.no_item_layout.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class AddCartFragment : BaseFragment() {

    private lateinit var cartItemsAdapter: CartItemsAdapter

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        no_item_text.text = "Foods in cart appear here"
        layout_item_cart.hideView()

        setupCartList()

        swipeToRefresh.setOnRefreshListener {
            setupCartList()
        }

    }

    private fun setupCartList() {
        val lm = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = lm
        recycler_view.setHasFixedSize(true)
        recycler_view.animate()

        recycler_view.hideView()
        noitem_layout.hideView()
        loader_layout.showView()


        layout_item_cart_body.setOnClickListener {
            activity?.startActivity(Intent(activity, ItemCartActivity::class.java))
        }

        vm.getBasket().observe(this, Observer {
            swipeToRefresh?.isRefreshing = false
            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    recycler_view.hideView()
                    noitem_layout.showView()
                    loader_layout.hideView()

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "Debug only: No cart foods")

                    Timber.d("$it")

                }
                Status.SUCCESS -> {
                    recycler_view.showView()
                    recycler_view.loadAnim()

                    noitem_layout.hideView()
                    loader_layout.hideView()

                    if (!data!!.items.isNullOrEmpty()) {

                        //we only take ordinary plates
                        val plateBaskets = data.items?.filter { b ->
                            b!!.basketType.equals("plate")
                        }?.toMutableList()

                        cartItemsAdapter = CartItemsAdapter(
                            activity!!,
                            vm,
                            plateBaskets,
                            object : RecyclerItemClickListener {
                                override fun modelClick(model: Any) {
                                    //loading
                                    recycler_view.hideView()
                                    noitem_layout.hideView()
                                    loader_layout.showView()
                                }
                            }, object : UpdateCartClickListener {
                                override fun modelClick(model: Any, isUpdated: Boolean) {
                                    //just got updated
                                    recycler_view.showView()
                                    noitem_layout.hideView()
                                    loader_layout.hideView()

                                    //check new data
                                    if (!data.items.isNullOrEmpty()) {
                                        layout_item_cart.showView()
                                        tv_total.text = "$" + "${data.total}"
                                        tv_count.text = "${data.items.count()}"
                                    } else {
                                        layout_item_cart.hideView()
                                    }
                                }
                            })
                        recycler_view.adapter = cartItemsAdapter

                        layout_item_cart.showView()
                        tv_total.text = "$" + "${data.total}"
                        tv_count.text = "${data.items?.count()}"

                    } else {
                        layout_item_cart.hideView()

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

}
