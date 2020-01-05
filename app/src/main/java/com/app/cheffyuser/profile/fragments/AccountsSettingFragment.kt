package com.app.cheffyuser.profile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.activities.PaymentOptionsActivity
import com.app.cheffyuser.create_account.activities.CreateAccountActivity
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.profile.activities.EditProfileActivity
import com.app.cheffyuser.profile.activities.ListShippingActivity
import com.app.cheffyuser.utils.Tools.directLinkToBrowser
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.fragment_accounts_setting.*

/**
 * A simple [Fragment] subclass.
 */
class AccountsSettingFragment : BaseFragment() {


    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accounts_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val BASE_URL = "https://mycheffy.herokuapp.com/"

        layout_payment.setOnClickListener {
            val intent = Intent(activity, PaymentOptionsActivity::class.java)
            startActivity(intent)
        }

        layout_shipping.setOnClickListener {
            val intent = Intent(activity, ListShippingActivity::class.java)
            startActivity(intent)
        }

        layout_logout.setOnClickListener {
            logout()
        }
        layout_profile_edit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        layout_support.setOnClickListener {
            createSnack(ctx = activity!!, txt = "TODO: Add support link")
            directLinkToBrowser(activity!!, BASE_URL)
        }
        layout_legal.setOnClickListener {
            createSnack(ctx = activity!!, txt = "TODO: Add legal link")
            directLinkToBrowser(activity!!, BASE_URL)
        }

    }

    private fun logout() {

        val dialog = showDialogue("Logging out", "Please wait ...")

        vm.logout().observe(this, Observer {

            when (it.status) {
                Status.ERROR -> {

                    if (BuildConfig.DEBUG)
                        createSnack(ctx = activity!!, txt = "${it.data?.message}")

                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    dialog?.dismiss()
                    tokenManager.DELETEALLPREFS()

                    val intent = Intent(activity, CreateAccountActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity?.finish()
                }
                Status.LOADING -> {
                }
            }

        })
    }
}