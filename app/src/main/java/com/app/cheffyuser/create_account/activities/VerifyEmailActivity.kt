package com.app.cheffyuser.create_account.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.utils.GenericTextWatcher
import com.app.cheffyuser.utils.GenericTextWatcherListener
import kotlinx.android.synthetic.main.activity_verify_email.*

class VerifyEmailActivity : BaseActivity(), GenericTextWatcherListener {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, VerifyEmailActivity::class.java)
    }

    private var finalWatcher: GenericTextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)

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
        //TODO: Do a verification API call to server
        val intent = Intent(this, SetPasswordActivity::class.java)
        startActivity(intent)
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
