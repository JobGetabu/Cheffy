package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.VerifyRequest
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.GenericTextWatcher
import com.app.cheffyuser.utils.GenericTextWatcherListener
import com.app.cheffyuser.utils.createSnack
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : BaseActivity(), GenericTextWatcherListener {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, VerifyActivity::class.java)
    }

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    private var finalWatcher: GenericTextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        uiStuff()
    }

    private fun uiStuff() {
        finalWatcher = GenericTextWatcher(et4, et1, et2, et3, et4)
        finalWatcher!!.setGenericTextWatcherListener(this)

        et1.addTextChangedListener(GenericTextWatcher(et1, et1, et2, et3, et4))
        et2.addTextChangedListener(GenericTextWatcher(et2, et1, et2, et3, et4))
        et3.addTextChangedListener(GenericTextWatcher(et3, et1, et2, et3, et4))
        et4.addTextChangedListener(finalWatcher)

        btn_continue.setOnClickListener {
            //TODO: Get code
            verifyPhone("0000")
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onFinalClick(code: String) {
        verifyPhone(code)
    }

    private fun verifyPhone(code: String) {


        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { verifyPhone(code) })

            return
        }

        val dialog = showDialogue("Verifying Email", "Please wait ...")

        val sReq = VerifyRequest(tokenManager.email, code)

        vm.verifyAccount(sReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it?.data?.message}", dialog!!)
                    clearTexts()
                }
                Status.SUCCESS -> {

                    val res = it.data!!
                    successDialogue(alertDialog = dialog, descriptions = res.message!!)

                    val intent = Intent(this@VerifyActivity, UserActivity::class.java)
                    startActivity(intent)
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })


    }

    private fun clearTexts() {
        et1.setText("")
        et2.setText("")
        et3.setText("")
        et4.setText("")

        et1.isFocusable = true
        et1.requestFocus()
    }


}
