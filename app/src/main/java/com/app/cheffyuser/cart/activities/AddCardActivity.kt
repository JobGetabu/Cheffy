package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : BaseActivity() {


    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddCardActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        uiStuff()
    }

    private fun uiStuff() {

        name.addTextChangedListener(textWatcher(name))
        expiry.addTextChangedListener(textWatcher(expiry))
        year.addTextChangedListener(textWatcher(year))

        et1.addTextChangedListener(myCardTextWatcher(et1))
        et2.addTextChangedListener(myCardTextWatcher(et2))
        et3.addTextChangedListener(myCardTextWatcher(et3))
        et4.addTextChangedListener(myCardTextWatcher(et4))

    }

    private fun clearTexts() {
        et1.setText("")
        et2.setText("")
        et3.setText("")
        et4.setText("")

        et1.isFocusable = true
        et1.requestFocus()
    }


    //one text watcher
    private fun textWatcher(edit: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //val cardno: String = email!!.text.toString()
            val nameTxt: String = name!!.text.toString()
            val expiryno: String = expiry!!.text.toString()
            val yearno: String = year!!.text.toString()
            val cvvno: String = cvv!!.text.toString()

            when (edit) {
                name -> {

                    card_name_label.text = nameTxt
                }

                expiry, year -> {
                    card_expiry_label.text = "$expiryno" + "/" + "$yearno"
                }
            }
        }
    }

    private fun myCardTextWatcher(view: EditText) = object : TextWatcher {

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                R.id.et1 -> if (text.length == 4)
                    et2.requestFocus()
                R.id.et2 -> if (text.length == 4)
                    et3.requestFocus()
                else if (text.length == 0)
                    et1.requestFocus()
                R.id.et3 -> if (text.length == 4)
                    et4.requestFocus()
                else if (text.length == 0)
                    et2.requestFocus()
                R.id.et4 -> {
                    if (text.length == 0)
                        et3.requestFocus()
                }
            }

            card_num_label.text = "${et1.text}   ${et2.text}   ${et3.text}   ${et4.text}"
        }


        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

    }

}
