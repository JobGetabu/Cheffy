package com.app.cheffyuser.create_account.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.VerifyActivity
import com.app.cheffyuser.create_account.model.AccessToken
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : BaseFragment() {

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(AuthViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        btn_next.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val email = etEmail.editText?.text.toString()

        if (email.isEmpty()) {
            etEmail.isErrorEnabled = true
            etEmail.error = "valid email required"
            return
        }

        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { signup() })
            }
            return
        }

        val dialog = showDialogue("Processing email", "Please wait ...")

        val sReq = SignupRequest(email)

        vm.createAccount(sReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "Please try again or use different email", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)

                    val res = it.data

                    //save in prefs
                    tokenManager.saveToken(AccessToken(res!!.token!!))
                    tokenManager.email = email

                    val intent = Intent(activity, VerifyActivity::class.java)
                    startActivity(intent)
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })
    }

}