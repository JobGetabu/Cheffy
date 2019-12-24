package com.app.cheffyuser.cart.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.cart.models.CreditCardRequest
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.whiteelephant.monthpicker.MonthPickerDialog
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.*

class AddCardActivity : BaseActivity() {


    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddCardActivity::class.java)
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
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

        expiry.setOnClickListener { dateSetter() }
        year.setOnClickListener { dateSetter() }

        save_btn.setOnClickListener {
            saveCard()
        }

        name.setText(tokenManager.user?.data?.name)

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

    private fun dateSetter() {
        val today = Calendar.getInstance()
        val picker = MonthPickerDialog.Builder(
            this,
            { selectedMonth: Int, selectedYear: Int ->
                expiry!!.setText("${selectedMonth + 1}")
                year!!.setText("$selectedYear")
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH)
        )


        var minY = if (expiry?.text?.toString()!!.isNotEmpty()) {
            expiry?.text.toString().toInt()
        } else today.get(Calendar.YEAR)

        var minM = if (year?.text?.toString()!!.isNotEmpty()) {
            year?.text.toString().toInt()
        } else today.get(Calendar.YEAR)

        minM = today.get(Calendar.MONTH)
        minY = today.get(Calendar.YEAR)

        picker
            .setMinYear(minY)
            .setActivatedYear(minY)
            .setMaxYear(minY + 10)
            .setActivatedMonth(minM)
            .setTitle("Select expiry")
            .setOnMonthChangedListener { expiry!!.setText("${it + 1}") }
            .setOnYearChangedListener { year!!.setText("$it") }
            .build()
            .show()
    }

    private fun saveCard() {
        val nameTxt: String = name?.text.toString()
        val expiryno: Int? = if (expiry?.text?.toString()!!.isNotEmpty()) {
            expiry?.text.toString().toInt()
        } else null

        val yearno: Int? = if (year?.text?.toString()!!.isNotEmpty()) {
            year?.text.toString().toInt()
        } else null

        val cvvno: Int? =  if (cvv?.text?.toString()!!.isNotEmpty()) {
            cvv?.text.toString().toInt()
        } else null
        val number = card_num_label.toString().trim()

        if (nameTxt.isEmpty() || number.isEmpty() || expiryno == null || yearno == null || cvvno == null ) {
            createSnack(ctx = this, txt = "All fields are required")
            return
        }

        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { saveCard() })
            return
        }

        val dialog = showDialogue("Saving card", "Please wait ...")

        val req = CreditCardRequest(cvvno!!, expiryno!!, yearno!!, number)

        vm.addCreditCard(req).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog!!)

                    val res = it.data

                    //save in prefs
                    tokenManager.stripeId = res?.id
                    tokenManager.creditCardData = req
                    finish()
                }
            }
        })
    }
}
